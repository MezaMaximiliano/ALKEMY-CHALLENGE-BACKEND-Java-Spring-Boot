package com.alkemy.challenge.service;

import com.alkemy.challenge.entity.Movies;
import com.alkemy.challenge.repository.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MoviesService {

    @Autowired
    private MoviesRepository repository;

    @Transactional
    public Movies save(Movies movie){
        return repository.save(movie);
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
        return repository.allMovies();
    }

    @Transactional
    public Iterable<Object[]> findByGerder_id(Integer genderId){
        return repository.findByGerder_id(genderId);
    }

    @Transactional
    public List<Movies> findAllMovies(){
        return repository.findAll();
    }

    @Transactional
    public Iterable<Object[]> getAllByOrder(String order){
        if(order.equals("DESC")){
            return  repository.getAllByOrdDesc();
        }else{
            return repository.getAllByOrdAsc();
        }
    }

    @Transactional
    public Optional<Movies> findById(Integer id){
        Movies mov = repository.findById(id).get();

        return repository.findById(id);
    }

    @Transactional
    public Iterable<Object[]> findByTitle(String name){
        return repository.findByTitle(name);
    }


}
