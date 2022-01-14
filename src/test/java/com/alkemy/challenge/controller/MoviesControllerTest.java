package com.alkemy.challenge.controller;

import com.alkemy.challenge.entity.Characters;
import com.alkemy.challenge.entity.Gender;
import com.alkemy.challenge.entity.Movies;
import com.alkemy.challenge.model.MovieModel;
import com.alkemy.challenge.service.MoviesService;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MoviesControllerTest {

    @InjectMocks
    private MoviesController moviesController;

    @Mock
    private MoviesService moviesService;

    private Movies movie;
    private Gender gender;
    private Characters character;
    private MultipartFile image;
    private MovieModel movieModel;
    private Iterable<Object[]> iterableList=new ArrayList<>();
    private List<Movies> moviesList= new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<Characters> charactersList = new ArrayList<>();

        gender = new Gender();
        gender.setId(1);
        gender.setName("Aventura");
        gender.setMoviesList(new ArrayList<>());
        gender.setImage("Aventura.jpg");
        gender.setMoviesList(new ArrayList<>());

        character = new Characters();
        character.setImage("");
        character.setAge(17);
        character.setHistory("Alguna historia");
        character.setId(1);
        character.setMoviesList(new ArrayList<>());
        character.setWeight(48);
        character.setName("Aladdin");

        charactersList.add(character);

        movieModel= new MovieModel();
        movieModel.setId(1);
        movieModel.setImage("");
        movieModel.setTitle("Aladdin");
        movieModel.setQualification(5);
        movieModel.setDate(new Date());
        movieModel.setGender(gender.getName());
        movieModel.setCharactersList(charactersList);

        movie= new Movies();
        movie.setId(1);
        movie.setImage("");
        movie.setTitle("Aladdin");
        movie.setQualification(5);
        movie.setDate(new Date());
        movie.setGender(gender);
        movie.setCharactersList(charactersList);

        Path path = Paths.get("static/img/imagesAladdin.jpg");
        String name = "imagesAladdin.jpg";
        String originalFileName = "Aladdin.jpg";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch ( IOException e) {
        }
        image = new MockMultipartFile(name,
                originalFileName, contentType, content);


    }

    @Test
    void findAll() {
        when(moviesService.findAll()).thenReturn(iterableList);
        assertEquals(moviesController.findAll(),iterableList);
    }

    @Test
    void todos() {
        when(moviesService.findAllMovies()).thenReturn(moviesList);
        assertEquals(moviesController.todos(),moviesList);
    }

    @Test
    void save() {
        when(moviesService.save(movie)).thenReturn(movie);
        assertEquals(moviesController.save(image,movie),movie);
    }

    @Test
    void delete() throws Exception {
        when(moviesService.delete(1)).thenReturn(Boolean.TRUE);
        assertEquals(moviesController.delete(1),"Se ha eliminado la pelicula de manera exitosa");

        when(moviesService.delete(2)).thenReturn(Boolean.FALSE);
        assertEquals(moviesController.delete(2),"La pelicula con ID 2 no ha podido ser eliminada");

        when(moviesService.delete(2)).thenReturn(Boolean.FALSE);
        assertNotEquals(moviesController.delete(2),"Se ha eliminado la pelicula de manera exitosa");
    }

    @Test
    void findById() {
        when(moviesService.findById(1)).thenReturn(Optional.ofNullable(movie));
        assertEquals(moviesController.findById(1),movieModel);
    }

    @Test
    void getAllByOrder() {
        assertEquals(moviesController.getAllByOrder("ASC"),iterableList);
    }

    @Test
    void findByTitle() {;
        assertEquals(moviesController.findByTitle("Aladdin"),iterableList);
    }

    @Test
    void findByGerder_id() {
        assertEquals(moviesController.findByGerder_id(1),iterableList);
    }
}