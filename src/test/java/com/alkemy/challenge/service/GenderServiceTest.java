package com.alkemy.challenge.service;

import com.alkemy.challenge.entity.Gender;
import com.alkemy.challenge.repository.GenderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GenderServiceTest {

    @Mock
    private GenderRepository genderRepository;

    @InjectMocks
    private GenderService genderService;

    private Gender gender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        gender = new Gender();
        gender.setImage("");
        gender.setMoviesList(new ArrayList<>());
        gender.setName("Aventura");
        gender.setId(1);
    }

    @Test
    void findAll() {
        when(genderRepository.findAll()).thenReturn(Arrays.asList(gender));
        assertNotNull(genderService.findAll());
    }
}