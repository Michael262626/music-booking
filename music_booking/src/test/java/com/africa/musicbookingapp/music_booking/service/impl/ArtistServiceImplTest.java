package com.africa.musicbookingapp.music_booking.service.impl;

import com.africa.musicbookingapp.music_booking.dto.request.ArtistDto;
import com.africa.musicbookingapp.music_booking.exception.ResourceNotFoundException;
import com.africa.musicbookingapp.music_booking.service.RepositoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class ArtistServiceImplTest {

    @Autowired
    private RepositoryManager repositoryManager;

    @Autowired
    private ServiceManager serviceManager;

    @BeforeEach
    void setUp() {
        repositoryManager.getArtistRepository().deleteAll();
    }

    @Test
    void testToCreateArtist() {
        var artists = new ArtistDto();
        artists.setName("Artist");
        artists.setGenre("Genre");
        artists.setContactInfo("Contact");
        var response = serviceManager.getArtistService().createArtist(artists);
        assertNotNull(response);
        assertThat(response.getName().equals("Artist")).isTrue();
    }

    @Test
    void testToGetAllArtists() {
        var response = serviceManager.getArtistService().getAllArtists();
        assertNotNull(response);
    }
    @Test
    void testToUpdateArtist() {
        var artist = new ArtistDto();
        artist.setName("Artist1");
        artist.setGenre("Genre2");
        artist.setContactInfo("Contact");
        var response = serviceManager.getArtistService().createArtist(artist);

        var artist2 = new ArtistDto();
        artist2.setName("Mike");
        artist2.setGenre("Genre2");
        artist2.setContactInfo("Contact");
        var updatedArtist = serviceManager.getArtistService().updateArtist(response.getId(), artist2);
        assertNotNull(updatedArtist);
        assertThat(updatedArtist.getName().equals("Mike")).isTrue();
    }
    @Test
    void testToDeleteArtist() {
        var artist = new ArtistDto();
        artist.setName("Kume");
        artist.setGenre("Genre2");
        artist.setContactInfo("Contact");
        var response = serviceManager.getArtistService().createArtist(artist);
        var findArtist = serviceManager.getArtistService().getArtistById(response.getId());
        assertNotNull(findArtist);
        serviceManager.getArtistService().deleteArtist(response.getId());
        assertThrows(ResourceNotFoundException.class,()-> serviceManager.getArtistService().getArtistById(response.getId()));
    }
}