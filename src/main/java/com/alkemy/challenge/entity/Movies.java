package com.alkemy.challenge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date date;
    private Integer qualification;
}
