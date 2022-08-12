package fr.jacgrana.springsecurityjpa.service;

import fr.jacgrana.springsecurityjpa.entity.Animal;
import fr.jacgrana.springsecurityjpa.entity.AnimalType;
import fr.jacgrana.springsecurityjpa.repository.AnimalTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AnimalTypeService {

    @Autowired
    private AnimalTypeRepository animalTypeRepository;



    public List<AnimalType> findAll() {
        return this.animalTypeRepository.findAll();
    }
}
