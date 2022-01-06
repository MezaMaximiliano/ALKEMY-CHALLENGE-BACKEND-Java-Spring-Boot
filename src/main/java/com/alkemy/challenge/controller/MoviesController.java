package com.alkemy.challenge.controller;

import com.alkemy.challenge.entity.Characters;
import com.alkemy.challenge.entity.Movies;
import com.alkemy.challenge.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    private MoviesService service;

    @GetMapping
    public Iterable<Object[]> findAll(){
        return service.findAll();
    }

    @GetMapping("/all")
    public Iterable<Movies> todos(){
        return service.findAllMovies();
    }

    @PostMapping("/save")
    public Movies save(/*@RequestParam(value = "file", required = false) MultipartFile image, */@RequestBody Movies movie){
        /*if(!image.isEmpty() ){
            Path imagesPath = Paths.get("src//main//resources//static//images");
            String absolutPath = imagesPath.toFile().getAbsolutePath();
            try {
                byte[] bytes = image.getBytes();
                Path route = Paths.get(absolutPath + image.getOriginalFilename());
                Files.write(route, bytes);
                movie.setImage(image.getOriginalFilename());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        movie.setImage("");
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
    public Optional<Movies> findById(@PathVariable("id") Integer id){
        return service.findById(id);
    }

    @GetMapping(params = "order")
    public Iterable<Object[]> getAllByOrder(@RequestParam("order") String order){
        return service.getAllByOrder(order);
    }

    @GetMapping(params = "title")
    public Iterable<Object[]> findByTitle(@RequestParam("title") String title){
        return service.findByTitle(title);
    }



}
