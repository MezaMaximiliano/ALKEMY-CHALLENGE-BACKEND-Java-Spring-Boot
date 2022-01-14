package com.alkemy.challenge.controller;

import com.alkemy.challenge.entity.Gender;
import com.alkemy.challenge.entity.Movies;
import com.alkemy.challenge.service.GenderService;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GenderControllerTest {

    @InjectMocks
    private GenderController genderController;

    @Mock
    private GenderService genderService;

    private Gender gender;
    private MultipartFile image;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        List<Movies> list = new ArrayList<>();

        Movies mov = new Movies();
        mov.setImage("");
        mov.setGender(null);
        mov.setId(1);
        mov.setCharactersList(new ArrayList<>());
        mov.setDate(new Date());
        mov.setQualification(5);
        mov.setTitle("Aladdin");

        list.add(mov);

        gender = new Gender();
        gender.setId(1);
        gender.setName("Aventura");
        gender.setMoviesList(list);
        gender.setImage("Aventura.jpg");
        gender.setMoviesList(list);

        Path path = Paths.get("static/img/imagesAventura.jpg");
        String name = "imagesAventura.jpg";
        String originalFileName = "Aventura.jpg";
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
    void findById() {
        when(genderService.findById(1)).thenReturn(Optional.ofNullable(gender));
        assertNotNull(genderController.findById(1));
        assertEquals(genderController.findById(1),Optional.ofNullable(gender));
    }

    @Test
    void findeAll() {
        when(genderService.findAll()).thenReturn(Arrays.asList(gender));
        assertNotNull(genderController.findeAll());
    }

    @Test
    void delete() throws Exception {

        when(genderService.delete(1)).thenReturn(Boolean.TRUE);
        assertEquals(genderController.delete(1),"El genero con id 1 se ha eliminado de manera exitosa.");

        when(genderService.delete(2)).thenReturn(Boolean.FALSE);
        assertEquals(genderController.delete(2),"No se ha podido eliminar el genero con ID 2");
    }

    @Test
    void save() throws IOException{
        when(genderService.save(gender)).thenReturn(gender);
        assertEquals(genderController.save(image,gender),gender);
    }
}