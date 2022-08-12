package fr.jacgrana.springsecurityjpa.controller;

import fr.jacgrana.springsecurityjpa.entity.Animal;
import fr.jacgrana.springsecurityjpa.entity.AnimalType;
import fr.jacgrana.springsecurityjpa.service.AnimalTypeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5500/*")
public class AnimalTypeController {

    AnimalTypeService animalTypeService;

    public AnimalTypeController(AnimalTypeService animalTypeService) {
        this.animalTypeService = animalTypeService;
    }

    @GetMapping("/animaltype/all")
    //@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AnimalType> getAll() {
        return this.animalTypeService.findAll();
    }
}
