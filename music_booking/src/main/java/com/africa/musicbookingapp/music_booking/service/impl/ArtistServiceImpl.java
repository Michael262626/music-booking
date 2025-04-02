package com.africa.musicbookingapp.music_booking.service.impl;

import com.africa.musicbookingapp.music_booking.configuration.securityConfig.WebClientConfig;
import com.africa.musicbookingapp.music_booking.dto.request.ArtistDto;
import com.africa.musicbookingapp.music_booking.dto.request.LoginRequest;
import com.africa.musicbookingapp.music_booking.dto.response.ArtistResponseDto;
import com.africa.musicbookingapp.music_booking.dto.response.LoginResponse;
import com.africa.musicbookingapp.music_booking.exception.ExternalApiException;
import com.africa.musicbookingapp.music_booking.exception.InvalidUserCredentialsException;
import com.africa.musicbookingapp.music_booking.exception.ResourceNotFoundException;
import com.africa.musicbookingapp.music_booking.entities.RepositoryManager;
import com.africa.musicbookingapp.music_booking.entities.model.Artist;
import com.africa.musicbookingapp.music_booking.mapper.MusicBookingMapper;
import com.africa.musicbookingapp.music_booking.entities.model.NewUserRecord;
import com.africa.musicbookingapp.music_booking.service.IArtistService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.stripe.Stripe.clientId;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Slf4j
@Service
public class ArtistServiceImpl implements IArtistService {
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.client-secret}")
    private String clientSecret;
    @Value("${token-server-url}")
    private String tokenServerUrl;
    @Autowired
    private WebClient webClient;

    private final RepositoryManager repositoryManager;
    private final MusicBookingMapper mapper;
    private final Keycloak keycloak;
    private final PasswordEncoder passwordEncoder;

    public ArtistServiceImpl(@Lazy RepositoryManager repositoryManager, MusicBookingMapper mapper, Keycloak keycloak, RestTemplate restTemplate, PasswordEncoder passwordEncoder) {
        this.repositoryManager = repositoryManager;
        this.mapper = mapper;
        this.keycloak = keycloak;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<ArtistResponseDto> getAllArtists() {
        List<Artist> artists = repositoryManager.getArtistRepository().findAll();
        return artists.stream()
                .map(mapper::toArtistResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public LoginResponse login(String username, String password) {
        return webClient.post()
                .uri(tokenServerUrl)
                .contentType(APPLICATION_FORM_URLENCODED)
                .bodyValue("client_id=" + "michael" +
                        "&client_secret=" + "AlecQQEDQgDp7RFfjVoSWQewTBUG870X" +
                        "&grant_type=password" +
                        "&username=" + username +
                        "&password=" + password)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new InvalidUserCredentialsException("Invalid user credentials"))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ExternalApiException("Keycloak is unable to authenticate user due to server error"))
                )
                .bodyToMono(LoginResponse.class).block();
    }

    @Override
    public ArtistResponseDto createArtist(NewUserRecord newUserRecord) {
        UserRepresentation userRepresentation = getUserRepresentation(newUserRecord);
        UsersResource usersResource = getUsersResource();
        Response response = usersResource.create(userRepresentation);
        log.info("Status Code: {}, Response: {}", response.getStatus(), response.readEntity(String.class));
        if (!Objects.equals(201, response.getStatus())) {
            throw new ResourceNotFoundException("Status code: " + response.getStatus());
        }
        log.info("New user created");
        Artist artist = mapper.toArtist(newUserRecord, userRepresentation);
        artist.setPassword(passwordEncoder.encode(newUserRecord.password()));
        Artist savedArtist = repositoryManager.getArtistRepository().save(artist);
        return mapper.toArtistResponseDto(savedArtist);
    }


    private void assignRoleToUser(String userId) {
        UserResource userResource = getUsersResource().get(userId);
        RolesResource rolesResource = keycloak.realm(realm).roles();

        RoleRepresentation role = rolesResource.get("USERS").toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(role));
        log.info("Assigned role '{}' to user with ID: {}", "USERS", userId);
    }

    private static UserRepresentation getUserRepresentation(NewUserRecord newUserRecord) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(newUserRecord.firstName());
        userRepresentation.setLastName(newUserRecord.lastName());
        userRepresentation.setUsername(newUserRecord.email());
        userRepresentation.setEmail(newUserRecord.email());
        userRepresentation.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(newUserRecord.password());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setTemporary(false);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        return userRepresentation;
    }

    @Override
    public void forgetPassword(LoginRequest loginRequest) {
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(loginRequest.getPassword(), true);
        UserRepresentation user = userRepresentations.get(0);

        UserResource userResource = usersResource.get(user.getId());
        userResource.executeActionsEmail(List.of("UPDATE PASSWORD"));
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    @Override
    public void updateArtist(Long id, ArtistDto updatedUser) {
        var existingArtist = repositoryManager.getArtistRepository()
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist with ID " + id + " not found."));

        existingArtist.setUsername(updatedUser.getUsername());
        existingArtist.setGenre(updatedUser.getGenre());
        repositoryManager.getArtistRepository().save(existingArtist);
    }

    @Override
    public void verificationEmail(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    @Override
    public void deleteArtist(Long id) {
       UsersResource usersResource = getUsersResource();
       usersResource.delete(String.valueOf(id));
    }

    @Override
    public UserResource getUser(Long userId) {
        UsersResource resource = getUsersResource();
        return resource.get(String.valueOf(userId));
    }

    @Override
    public ArtistResponseDto getArtistById(Long id) {
        Artist artist = repositoryManager.getArtistRepository()
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist with ID " + id + " not found."));

        return mapper.toArtistResponseDto(artist);    }
}
