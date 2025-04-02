package com.africa.musicbookingapp.music_booking.service.impl;

import com.africa.musicbookingapp.music_booking.dto.request.ArtistDto;
import com.africa.musicbookingapp.music_booking.exception.ResourceNotFoundException;
import com.africa.musicbookingapp.music_booking.entities.RepositoryManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
public class ArtistServiceImplTest {

    @Autowired
    private RepositoryManager repositoryManager;

    @Autowired
    private ServiceManager serviceManager;


    @Test
    void testToCreateArtist() {
        var artists = new ArtistDto();
        artists.setUsername("Artist");
        artists.setGenre("Genre");
        artists.setContactInfo("Contact");

        var response = serviceManager.getArtistService().createArtist(artists);
        assertNotNull(response);
        assertThat(response.getUsername().equals("Artist")).isTrue();
    }

    @Test
    void testToGetAllArtists() {
        var response = serviceManager.getArtistService().getAllArtists();
        assertNotNull(response);
    }
    @Test
    void testToUpdateArtist() {
        var artist = new ArtistDto();
        artist.setUsername("Artist1");
        artist.setGenre("Genre2");
        artist.setContactInfo("Contact");
        var response = serviceManager.getArtistService().createArtist(artist);

        var artist2 = new ArtistDto();
        artist2.setUsername("Mike");
        artist2.setGenre("Genre2");
        artist2.setContactInfo("Contact");
        serviceManager.getArtistService().updateArtist(response.getId(), artist2);
        var updatedArtist = serviceManager.getArtistService().getArtistById(response.getId());
        assertThat(updatedArtist.getName()).isEqualTo("Mike");
    }
    @Test
    void testToDeleteArtist() {
        var artist = new ArtistDto();
        artist.setUsername("Kume");
        artist.setGenre("Genre2");
        artist.setContactInfo("Contact");
        var response = serviceManager.getArtistService().createArtist(artist);
        var findArtist = serviceManager.getArtistService().getArtistById(response.getId());
        assertNotNull(findArtist);
        serviceManager.getArtistService().deleteArtist(response.getId());
        assertThrows(ResourceNotFoundException.class,()-> serviceManager.getArtistService().getArtistById(response.getId()));
    }
}