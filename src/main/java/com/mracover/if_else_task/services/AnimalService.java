package com.mracover.if_else_task.services;

import com.mracover.if_else_task.DTO.request.animal.RequestPutAnimalTypeDTO;
import com.mracover.if_else_task.models.Animal;

import java.time.OffsetDateTime;
import java.util.List;

public interface AnimalService {

    List<Animal> findAnimalsByParameters(Animal animal, OffsetDateTime startDateTime, OffsetDateTime endDateTime, int from, int size);
    Animal findAnimalById (Long id);
    Animal addNewAnimal (Animal animal);
    Animal addAnimalTypeToAnimal(Long animalId, Long typeId);
    Animal updateAnimal (Animal animal);
    Animal updateAnimalType (Long id, RequestPutAnimalTypeDTO requestPutAnimalTypeDTO);
    void deleteAnimalById (Long id);
    Animal deleteAnimalTypeFromAnimal(Long animalId, Long typeId);
}
