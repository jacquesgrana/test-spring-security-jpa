package fr.jacgrana.springsecurityjpa.controller;

import fr.jacgrana.springsecurityjpa.entity.Animal;
import fr.jacgrana.springsecurityjpa.entity.AnimalType;
import fr.jacgrana.springsecurityjpa.exceptions.BadRequestException;
import fr.jacgrana.springsecurityjpa.service.AnimalTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/animaltype/{id}")
    public AnimalType read(@PathVariable("id") Integer id) throws BadRequestException {
        return this.animalTypeService.getById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/animaltype/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody AnimalType animalType) throws BadRequestException{
        this.animalTypeService.create(animalType);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(path = "/animaltype/update/{id}")
    public void update(@RequestBody AnimalType animalType,  @PathVariable("id") Integer id) throws BadRequestException{
        this.animalTypeService.update(animalType, id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path = "/animaltype/delete/{id}")
    public void delete(@PathVariable("id") Integer id) throws BadRequestException {
        this.animalTypeService.delete(id);
    }
}
