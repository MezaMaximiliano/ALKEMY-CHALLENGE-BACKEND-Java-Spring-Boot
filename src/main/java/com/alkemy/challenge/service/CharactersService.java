package com.alkemy.challenge.service;

import com.alkemy.challenge.entity.Characters;
import com.alkemy.challenge.repository.CharactersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CharactersService {

    @Autowired
    private CharactersRepository repository;

    @Transactional
    public Characters save(Characters character){
        return repository.save(character);
    }

    @Transactional
    public Boolean delete(Integer id)throws Exception{
        try{
            repository.deleteById(id);
            return true;
        }catch (Exception err){
            return false;
        }
    }

    @Transactional
    public Iterable<Object[]> findAll(){
        return repository.allCharacters();
    }

    @Transactional
    public Iterable<Object[]> findByName(String name){
        return repository.findByName(name);
    }

    @Transactional
    public Iterable<Object[]> findByAge(Integer age){
        return repository.findByAge(age);
    }

    @Transactional
    public Optional<Characters> findById(Integer id){
        return repository.findById(id);
    }

    @Transactional
    public Iterable<Characters> findAllCharacters(){
        return repository.findAll();
    }
}
