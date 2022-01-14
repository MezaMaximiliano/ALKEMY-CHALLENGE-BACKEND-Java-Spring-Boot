package com.alkemy.challenge.controller;

import com.alkemy.challenge.entity.Characters;
import com.alkemy.challenge.entity.Movies;
import com.alkemy.challenge.service.CharactersService;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

class CharactersControllerTest {

    @InjectMocks
    private CharactersController charactersController;

    @Mock
    private CharactersService charactersService;

    private Characters character;
    private MultipartFile image;
    private Movies movie;
    private Iterable<Object[]> characterModels;
    private Iterable<Characters> charactersIterable;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        character = new Characters();

        character.setImage("");
        character.setAge(17);
        character.setHistory("Alguna historia");
        character.setId(1);
        character.setMoviesList(new ArrayList<>());
        character.setWeight(48);
        character.setName("Aladdin");

        movie = new Movies();
        movie.setId(1);

        character.getMoviesList().add(movie);

        Path path = Paths.get("static/img/imagesaladdin.jpg");
        String name = "imagesaladdin.jpg";
        String originalFileName = "aladdin.jpg";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch ( IOException e) {
        }
        image = new MockMultipartFile(name, originalFileName, contentType, content);

    }

    @Test
    void findAll() {
        when(charactersService.findAllCharacters()).thenReturn(charactersIterable);
        assertEquals(charactersController.findAllCharacters(),charactersIterable);
    }

    @Test
    void findAllCharacters() {
        when(charactersService.findAll()).thenReturn(characterModels);
        assertEquals(charactersController.findAll(),characterModels);

    }

    @Test
    void save() {
        when(charactersService.save(character)).thenReturn(character);
        assertEquals(charactersController.save(image,character),character);
    }

    @Test
    void delete() {
        when(charactersService.delete(1)).thenReturn(Boolean.TRUE);
        assertEquals(charactersController.delete(1),"El personaje con id 1 se ha eliminado de manera exitosa.");

        when(charactersService.delete(2)).thenReturn(Boolean.FALSE);
        assertEquals(charactersController.delete(2),"No se ha podido eliminar el personaje con ID 2");

        when(charactersService.delete(2)).thenReturn(Boolean.FALSE);
        assertNotEquals(charactersController.delete(2),"El personaje con id 2 se ha eliminado de manera exitosa.");
    }

    @Test
    void findById() {
        when(charactersService.findById(1)).thenReturn(Optional.ofNullable(character));
        assertEquals(charactersController.findById(1),Optional.ofNullable(character));
;
    }

    @Test
    void findByName() {
        when(charactersService.findByName("Aladdin")).thenReturn(characterModels);
        assertEquals(charactersController.findByName("Aladdin"),characterModels);

        characterModels= Collections.EMPTY_LIST;

        assertEquals(charactersController.findByName("El rey leon"),characterModels);
    }

    @Test
    void findByAge() {
        when(charactersService.findByAge(17)).thenReturn(characterModels);
        assertEquals(charactersController.findByAge(17),characterModels);

        characterModels= Collections.EMPTY_LIST;

        assertEquals(charactersController.findByAge(22),characterModels);
    }

    @Test
    void findByMoviesList() {
        when(charactersService.findByMoviesId(1)).thenReturn(characterModels);
        assertEquals(charactersController.findByMoviesList(1),characterModels);

        characterModels= Collections.EMPTY_LIST;

        assertEquals(charactersController.findByMoviesList(2),characterModels);
    }

}
@Data
class CharacterModel{
    private String image;
    private String name;
}