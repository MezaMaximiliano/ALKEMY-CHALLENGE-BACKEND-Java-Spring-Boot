package com.alkemy.challenge.controller;

import com.alkemy.challenge.entity.Movies;
import com.alkemy.challenge.model.MovieModel;
import com.alkemy.challenge.service.MoviesService;
import com.alkemy.challenge.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MoviesController {

    private final MoviesService service;

    @GetMapping
    public Iterable<Object[]> findAll(){

        return service.findAll();
    }

    @GetMapping("/all")
    public Iterable<Movies> todos(){
        return service.findAllMovies();
    }

    @PostMapping("/save")
    public Movies save(@RequestParam(value = "file",required = false) MultipartFile image, @ModelAttribute Movies movie){

        if(image==null){
            movie.setImage("");
        }else if(!image.isEmpty() ){
            Path imagesPath = Paths.get("src//main//resources//static//img//peliculas//images");
            String absolutPath = imagesPath.toFile().getAbsolutePath();
            try {
                byte[] bytes = image.getBytes();
                Path route = Paths.get(absolutPath + image.getOriginalFilename());
                Files.write(route, bytes);
                movie.setImage(image.getOriginalFilename());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return service.save(movie);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        try {
           if( service.delete(id)){
               return "Se ha eliminado la pelicula de manera exitosa";
           }else{
               return "La pelicula con ID "+id+" no ha podido ser eliminada";
           }

        } catch (Exception err) {
            return "La pelicula con ID "+id+" no existe o ya ha sido eliminada";
        }
    }

    @GetMapping("/{id}")
    public MovieModel findById(@PathVariable("id") Integer id){
        return Util.convertMovies(service.findById(id).get());
    }

    @GetMapping(params = "order")
    public Iterable<Object[]> getAllByOrder(@RequestParam("order") String order){
        return service.getAllByOrder(order);
    }

    @GetMapping(params = "name")
    public Iterable<Object[]> findByTitle(@RequestParam("name") String name){

        return service.findByTitle(name);
    }

    @GetMapping(params = "genre")
    public Iterable<Object[]> findByGerder_id(@RequestParam("genre") Integer genderId){
        return service.findByGerder_id(genderId);
    }

}

