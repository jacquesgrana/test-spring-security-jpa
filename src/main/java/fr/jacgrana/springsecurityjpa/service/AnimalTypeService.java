package fr.jacgrana.springsecurityjpa.service;

import fr.jacgrana.springsecurityjpa.entity.Animal;
import fr.jacgrana.springsecurityjpa.entity.AnimalType;
import fr.jacgrana.springsecurityjpa.enums.ErrorCodeEnum;
import fr.jacgrana.springsecurityjpa.exceptions.BadRequestException;
import fr.jacgrana.springsecurityjpa.repository.AnimalTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalTypeService {

    @Autowired
    private AnimalTypeRepository animalTypeRepository;

    /**
     * TODO controler si label pas deja utilisé dans create et update
     */

    public List<AnimalType> findAll() {
        return this.animalTypeRepository.findAll();
    }

    public AnimalType getById(Integer id) throws BadRequestException {
        Optional<AnimalType> animalType = this.animalTypeRepository.findById(id);
        return animalType.orElseThrow(() -> new BadRequestException(ErrorCodeEnum.ANIMAL_TYPE_NOT_FOUND, "Pas de type d'animal avec cet id : " + id));
    }
    public void create(AnimalType animalType) {

        this.animalTypeRepository.save(animalType);
    }

    public void update(AnimalType animalType, Integer id) throws BadRequestException {
        AnimalType animalTypeInDb = this.getById(id);
        if (animalTypeInDb != null) {
            animalTypeInDb.setLabel(animalType.getLabel());

            this.animalTypeRepository.save(animalTypeInDb);
        }
    }


    public void delete(Integer id) throws BadRequestException {
        AnimalType animalTypeInDb = this.getById(id);
        if (animalTypeInDb != null) {
            this.animalTypeRepository.delete(animalTypeInDb);
        }
    }
}
