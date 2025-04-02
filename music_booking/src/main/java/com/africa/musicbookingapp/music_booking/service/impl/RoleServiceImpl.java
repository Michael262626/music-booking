package com.africa.musicbookingapp.music_booking.service.impl;

import com.africa.musicbookingapp.music_booking.service.IArtistService;
import com.africa.musicbookingapp.music_booking.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;


@RequiredArgsConstructor
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Value("${keycloak.realm}")
    private String realm;
    private final Keycloak keycloak;
    private final IArtistService artistService;

    @Override
    public void assignRole(Long userId, String roleName) {

        UserResource usersResource = artistService.getUser(userId);
        RolesResource rolesResource = getRolesResource();
        RoleRepresentation roleRepresentation = rolesResource.get(roleName)
                        .toRepresentation();
        usersResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));
    }
    private RolesResource getRolesResource() {
        return keycloak.realm(realm).roles();
    }
}
