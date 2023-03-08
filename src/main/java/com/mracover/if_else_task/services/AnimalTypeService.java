package com.mracover.if_else_task.services;

import com.mracover.if_else_task.models.AnimalType;

public interface AnimalTypeService {
    AnimalType findAnimalTypeById (Long id);
    AnimalType addNewAnimalType (AnimalType animalType);
    AnimalType updateAnimalType (AnimalType animalType);
    void deleteAnimalTypeById (Long id);
}
