package fr.jacgrana.springsecurityjpa.controller;

import fr.jacgrana.springsecurityjpa.dto.UserAnimalLinkDTO;
import fr.jacgrana.springsecurityjpa.entity.Animal;
import fr.jacgrana.springsecurityjpa.entity.User;
import fr.jacgrana.springsecurityjpa.exceptions.BadRequestException;
import fr.jacgrana.springsecurityjpa.service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/animal/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody Animal animal) throws BadRequestException{
        this.animalService.create(animal);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(path = "/animal/update/{id}")
    public void update(@RequestBody Animal animal,  @PathVariable("id") Integer id) throws BadRequestException{
        this.animalService.update(animal, id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/animal/delete/{id}")
    public void delete(@PathVariable("id") Integer id) throws BadRequestException {
        this.animalService.delete(id);
    }

    /**
     *
     * @throws BadRequestException
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/animal/orphans")
    public List<Animal>  getOrphansAnimals() throws BadRequestException {
        return this.animalService.getOrphans();
    }
}
