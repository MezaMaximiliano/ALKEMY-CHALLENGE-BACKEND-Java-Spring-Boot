package com.alkemy.challenge.model;

import com.alkemy.challenge.entity.Characters;
import com.alkemy.challenge.entity.Gender;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MovieModel {
    private Integer id;
    private String image;
    private String title;
    private Date date;
    private Integer qualification;
    private List<Characters> charactersList;
    private Gender gender_id;
}
