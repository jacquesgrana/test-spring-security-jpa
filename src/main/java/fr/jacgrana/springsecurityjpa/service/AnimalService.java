package fr.jacgrana.springsecurityjpa.service;

import fr.jacgrana.springsecurityjpa.entity.Animal;
import fr.jacgrana.springsecurityjpa.entity.User;
import fr.jacgrana.springsecurityjpa.enums.ErrorCodeEnum;
import fr.jacgrana.springsecurityjpa.exceptions.BadRequestException;
import fr.jacgrana.springsecurityjpa.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {


    @Autowired
    AnimalRepository animalRepository;

    public List<Animal> findAll() {
        return this.animalRepository.findAll();
    }

    public Animal getById(Integer id) throws BadRequestException {
        Optional<Animal> animal = this.animalRepository.findById(id);
        return animal.orElseThrow(() -> new BadRequestException(ErrorCodeEnum.ANIMAL_NOT_FOUND, "Pas d'animal avec cet id : " + id));
    }
    public void create(Animal animal) {
        this.animalRepository.save(animal);
    }

    public void update(Animal animal, Integer id) throws BadRequestException {
        Animal animalInDb = this.getById(id);
        if (animalInDb != null) {
            animalInDb.setAnimalType(animal.getAnimalType());
            animalInDb.setBirth(animal.getBirth());
            animalInDb.setComment(animal.getComment());
            animalInDb.setGenre(animal.getGenre());
            animalInDb.setName(animal.getName());
            this.animalRepository.save(animalInDb);
        }
    }


    public void delete(Integer id) throws BadRequestException {
        Animal animalInDb = this.getById(id);
        if (animalInDb != null) {
            this.animalRepository.delete(animalInDb);
        }
    }
}
