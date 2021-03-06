package com.alkemy.challenge.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Characters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String image;
    private String name;
    private Integer age;
    private Integer weight;
    private String history;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinTable(name = "characters_movies",
            joinColumns = {
                    @JoinColumn(name = "character_id", nullable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "movie_id", nullable = false)})
    private List<Movies> moviesList;

}
