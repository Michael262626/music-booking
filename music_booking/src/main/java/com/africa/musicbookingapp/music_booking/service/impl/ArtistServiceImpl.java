package com.africa.musicbookingapp.music_booking.service.impl;

import com.africa.musicbookingapp.music_booking.domain.model.Artist;
import com.africa.musicbookingapp.music_booking.dto.request.ArtistDto;
import com.africa.musicbookingapp.music_booking.dto.response.ArtistResponseDto;
import com.africa.musicbookingapp.music_booking.exception.ResourceNotFoundException;
import com.africa.musicbookingapp.music_booking.mapper.MusicBookingMapper;
import com.africa.musicbookingapp.music_booking.service.IArtistService;
import com.africa.musicbookingapp.music_booking.service.RepositoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements IArtistService {
    private final RepositoryManager repositoryManager;
    private final MusicBookingMapper mapper;


    @Autowired
    public ArtistServiceImpl(@Lazy RepositoryManager repositoryManager, MusicBookingMapper mapper) {
        this.repositoryManager = repositoryManager;
        this.mapper = mapper;
    }

    @Override
    public List<ArtistResponseDto> getAllArtists() {
        List<Artist> artists = repositoryManager.getArtistRepository().findAll();
        return artists.stream()
                .map(mapper::toArtistResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ArtistResponseDto createArtist(ArtistDto dto) {
        repositoryManager.getArtistRepository()
                .findByName(dto.getName())
                .ifPresent(artist -> {
                    throw new ResourceNotFoundException("Artist with name " + dto.getName() + " already exists.");
                });

        Artist artist = mapper.toArtist(dto);
        Artist savedArtist = repositoryManager.getArtistRepository().save(artist);
        return mapper.toArtistResponseDto(savedArtist);
    }
    @Override
    public ArtistResponseDto updateArtist(Long id, ArtistDto dto) {
        var existingArtist = repositoryManager.getArtistRepository()
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist with ID " + id + " not found."));

        existingArtist.setName(dto.getName());
        existingArtist.setGenre(dto.getGenre());

        var updatedArtist = repositoryManager.getArtistRepository().save(existingArtist);
        return mapper.toArtistResponseDto(updatedArtist);
    }
    @Override
    public void deleteArtist(Long id) {
        var existingArtist = repositoryManager.getArtistRepository()
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist with ID " + id + " not found."));
        repositoryManager.getArtistRepository().delete(existingArtist);
    }
    @Override
    public ArtistResponseDto getArtistById(Long id) {
        Artist artist = repositoryManager.getArtistRepository()
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist with ID " + id + " not found."));

        return mapper.toArtistResponseDto(artist);
    }

}
