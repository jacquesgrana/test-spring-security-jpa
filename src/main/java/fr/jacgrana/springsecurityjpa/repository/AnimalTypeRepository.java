package fr.jacgrana.springsecurityjpa.repository;

import fr.jacgrana.springsecurityjpa.entity.AnimalType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalTypeRepository extends JpaRepository<AnimalType, Integer> {
}
