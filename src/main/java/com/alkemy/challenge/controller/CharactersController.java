package com.alkemy.challenge.controller;


import com.alkemy.challenge.entity.Characters;
import com.alkemy.challenge.service.CharactersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/characters")
public class CharactersController {

    @Autowired
    private CharactersService service;

    @GetMapping
    public Iterable<Object[]> findAll(){
        return service.findAll();
    }

    @GetMapping("/all")
    public Iterable<Characters> findAllCharacters(){
        return service.findAllCharacters();
    }

    @PostMapping("/save")
    public Characters save(@RequestParam(value = "file") MultipartFile image, @ModelAttribute Characters character){

        if(!image.isEmpty() ){
            Path imagesPath = Paths.get("src//main//resources//static//images");
            String absolutPath = imagesPath.toFile().getAbsolutePath();
            try {
                byte[] bytes = image.getBytes();
                Path route = Paths.get(absolutPath + image.getOriginalFilename());
                Files.write(route, bytes);
                character.setImage(image.getOriginalFilename());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return service.save(character);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        try {
            if(service.delete(id)){
                return "El personaje con id "+id+" se ha eliminado de manera exitosa.";
            }else{
                return ("No se ha podido eliminar el personaje con ID "+id);
            }
        } catch (Exception err) {
            return err.getMessage();
        }
    }

    @GetMapping("/{id}")
    public Optional<Characters> findById(@PathVariable("id") Integer id){
        return service.findById(id);
    }

    @GetMapping(params = "name")
    public Iterable<Object[]> findByName(@RequestParam("name") String name){
        return service.findByName(name);
    }

    @GetMapping(params = "age")
    public Iterable<Object[]> findByAge(@RequestParam("age") Integer age){
        return service.findByAge(age);
    }

    @GetMapping(params = "movies")
    public Iterable<Object[]> findByMoviesList(@RequestParam("movies") Integer movieId){
        return service.findByMoviesId(movieId);
    }


}
