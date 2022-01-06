package com.alkemy.challenge.service;

import com.alkemy.challenge.entity.Gender;
import com.alkemy.challenge.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GenderService {

    @Autowired
    private GenderRepository repository;

    @Transactional
    public Gender save(Gender gender){
        return repository.save(gender);
    }

    @Transactional
    public List<Gender> findAll(){
        return repository.findAll();
    }

    @Transactional
    public Optional<Gender> findById(Integer id){
        return repository.findById(id);
    }

    @Transactional
    public Boolean delete(Integer id) throws Exception{
        try{
            repository.deleteById(id);
            return true;
        }catch (Exception err){
            return false;
        }
    }
}
