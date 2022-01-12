package com.alkemy.challenge.util;

import com.alkemy.challenge.entity.Movies;
import com.alkemy.challenge.model.MovieModel;
import com.alkemy.challenge.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Util{


    private MoviesService service;

    public static String format(String chain, String parameter){
        chain = String.format(chain,parameter);
        return chain;
    }

    public void getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException{

    }

    public static MovieModel convertMovies(Movies movie){
        MovieModel movModel = new MovieModel();

        movModel.setId(movie.getId());
        movModel.setTitle(movie.getTitle());
        movModel.setImage(movie.getImage());
        movModel.setDate(movie.getDate());
        movModel.setQualification(movie.getQualification());
        movModel.setCharactersList(movie.getCharactersList());
        movModel.setGender_id(movie.getGender_id());

        return movModel;
    }

}
