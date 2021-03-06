package com.alkemy.challenge.repository;


import com.alkemy.challenge.entity.Characters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharactersRepository extends JpaRepository<Characters,Integer> {


    Optional<Characters> findById(Integer id);

    @Query("SELECT image, name FROM Characters")
    Iterable<Object[]> allCharacters();

    Iterable<Object[]> findByName(String name);

    Iterable<Object[]> findByAge(Integer age);

    @Query(value = "SELECT c.* FROM characters c " +
            "INNER JOIN characters_movies cm ON (c.id=cm.character_id) " +
            "INNER JOIN movies m ON (m.id=cm.movie_id) " +
            "WHERE m.id=?1", nativeQuery = true)
    Iterable<Object[]> findByMoviesId(Integer movieId);

}
