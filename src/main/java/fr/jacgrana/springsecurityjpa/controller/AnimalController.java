package fr.jacgrana.springsecurityjpa.controller;

import fr.jacgrana.springsecurityjpa.entity.Animal;
import fr.jacgrana.springsecurityjpa.entity.User;
import fr.jacgrana.springsecurityjpa.exceptions.BadRequestException;
import fr.jacgrana.springsecurityjpa.service.AnimalService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5500/*")
public class AnimalController {

    AnimalService animalService;


    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/animal/all")
    //@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Animal> getAll() {
        return this.animalService.findAll();
    }

    @GetMapping(path = "/animal/{id}")
    public Animal read(@PathVariable("id") Integer id) throws BadRequestException {
        return this.animalService.getById(id);
    }
}
