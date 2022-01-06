package com.alkemy.challenge.repository;

import com.alkemy.challenge.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenderRepository extends JpaRepository<Gender,Integer> {

    Optional<Gender> findById(Integer id);

    List<Gender> findAll();
}
