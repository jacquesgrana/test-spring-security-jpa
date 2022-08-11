package fr.jacgrana.springsecurityjpa.repository;

import fr.jacgrana.springsecurityjpa.entity.Animal;
import fr.jacgrana.springsecurityjpa.entity.User;
import fr.jacgrana.springsecurityjpa.service.AnimalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface AnimalRepository  extends JpaRepository <Animal, Integer> {

}
