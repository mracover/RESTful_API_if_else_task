package com.mracover.if_else_task.repositories;

import com.mracover.if_else_task.models.animalModels.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>, JpaSpecificationExecutor<Animal> {

//    List<Animal> findAnimalsByAnimalVisi;
//    List<Animal> findAnimalsOnExitArea();
}
