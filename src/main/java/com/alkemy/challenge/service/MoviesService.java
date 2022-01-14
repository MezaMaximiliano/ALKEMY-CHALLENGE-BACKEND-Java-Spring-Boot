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
    private MoviesRepository moviesRepository;

    @Transactional
    public Movies save(Movies movie){
        return moviesRepository.save(movie);
    }

    @Transactional
    public Boolean delete(Integer id)throws Exception{
        try{
            moviesRepository.deleteById(id);
            return true;
        }catch (Exception err){
            return false;
        }
    }

    @Transactional
    public Iterable<Object[]> findAll(){
        return moviesRepository.allMovies();
    }

    @Transactional
    public Iterable<Object[]> findByGerder_id(Integer genderId){
        return moviesRepository.findByGerder_id(genderId);
    }

    @Transactional
    public List<Movies> findAllMovies(){
        return moviesRepository.findAll();
    }

    @Transactional
    public Iterable<Object[]> getAllByOrder(String order){
        if(order.equals("DESC")){
            return  moviesRepository.getAllByOrdDesc();
        }else{
            return moviesRepository.getAllByOrdAsc();
        }
    }

    @Transactional
    public Optional<Movies> findById(Integer id){
        return moviesRepository.findById(id);
    }

    @Transactional
    public Iterable<Object[]> findByTitle(String name){
        return moviesRepository.findByTitle(name);
    }


}
