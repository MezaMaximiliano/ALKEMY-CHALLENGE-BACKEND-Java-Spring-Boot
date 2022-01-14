package com.alkemy.challenge.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movies {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String image;
    private String title;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date date;
    private Integer qualification;

    @JsonBackReference
    @ManyToMany(mappedBy = "moviesList",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Characters> charactersList;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;
}
