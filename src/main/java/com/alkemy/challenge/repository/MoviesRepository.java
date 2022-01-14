package com.alkemy.challenge.repository;


import com.alkemy.challenge.entity.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoviesRepository extends JpaRepository<Movies,Integer> {

    Optional<Movies> findById(Integer id);

    @Override
    List<Movies> findAll();

    Iterable<Object[]> findByTitle(String name);

    @Query(value = "SELECT image,title,date FROM Movies", nativeQuery = true)
    Iterable<Object[]> allMovies();

    @Query(value = "SELECT image,title,date FROM movies ORDER BY date",nativeQuery = true)
    Iterable<Object[]> getAllByOrdAsc();

    @Query(value = "SELECT image,title,date FROM movies ORDER BY date DESC",nativeQuery = true)
    Iterable<Object[]> getAllByOrdDesc();

    @Query(value = "SELECT * FROM Movies WHERE gender_id = ?1",nativeQuery = true)
    Iterable<Object[]> findByGerder_id(Integer genderId);
}
