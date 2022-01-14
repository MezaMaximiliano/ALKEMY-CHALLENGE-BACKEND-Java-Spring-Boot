package com.alkemy.challenge.util;

import com.alkemy.challenge.entity.Movies;
import com.alkemy.challenge.model.MovieModel;

public class Util{

    public static String format(String chain, String name,String userName, String password){
        chain = String.format(chain,name,userName,password);
        return chain;
    }

    public static MovieModel convertMovies(Movies movie){
        MovieModel model = new MovieModel();

        model.setId(movie.getId());
        model.setTitle(movie.getTitle());
        model.setImage(movie.getImage());
        model.setDate(movie.getDate());
        model.setQualification(movie.getQualification());
        model.setCharactersList(movie.getCharactersList());
        model.setGender(movie.getGender().getName());

        return model;
    }

}
