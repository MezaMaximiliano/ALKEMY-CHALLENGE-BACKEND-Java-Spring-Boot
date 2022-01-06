package com.alkemy.challenge.repository;


import com.alkemy.challenge.entity.Characters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharactersRepository extends JpaRepository<Characters,Integer> {


    Optional<Characters> findById(Integer id);

    @Query("SELECT name, image FROM Characters")
    Iterable<Object[]> allCharacters();

    Iterable<Object[]> findByName(String name);

    Iterable<Object[]> findByAge(Integer age);

}
