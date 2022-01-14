package com.alkemy.challenge.controller;

import com.alkemy.challenge.entity.Gender;
import com.alkemy.challenge.service.GenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genders")
public class GenderController {


    private final GenderService service;

    @GetMapping
    public List<Gender> findeAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Gender> findById(@PathVariable("id") Integer id){
        return service.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        try {
            if(service.delete(id)){
                return "El genero con id "+id+" se ha eliminado de manera exitosa.";
            }else{
                return ("No se ha podido eliminar el genero con ID "+id);
            }
        } catch (Exception err) {
            return err.getMessage();
        }
    }

    @PostMapping("/save")
    public Gender save(@RequestParam(value = "file",required = false) MultipartFile image, @ModelAttribute Gender gender){

        if (image==null){
            gender.setImage("");
        } else if(!image.isEmpty() ){
            Path imagesPath = Paths.get("src//main//resources//static//img//generos//images");
            String absolutPath = imagesPath.toFile().getAbsolutePath();
            try {
                byte[] bytes = image.getBytes();
                Path route = Paths.get(absolutPath + image.getOriginalFilename());
                Files.write(route, bytes);
                gender.setImage(image.getOriginalFilename());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return service.save(gender);
    }
}
