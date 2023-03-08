package com.mracover.if_else_task.mappers.response;

import com.mracover.if_else_task.DTO.response.ResponseAnimalDTO;
import com.mracover.if_else_task.models.Animal;
import com.mracover.if_else_task.models.AnimalType;
import com.mracover.if_else_task.models.AnimalVisitedLocation;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ResponseAnimalMapper {

    @Mappings({@Mapping(source = "animalTypeList", target = "animalTypes"),
            @Mapping(source = "chipperId.id", target = "chipperId"),
            @Mapping(source = "chippingLocationId.id", target = "chippingLocationId"),
            @Mapping(source = "animalVisitedLocationList", target = "visitedLocations"),}
    )
    ResponseAnimalDTO animalToDTO(Animal animal);

    List<ResponseAnimalDTO> animalListToListDTO(List<Animal> animals);

    default List<Long> animalTypeListToListId(List<AnimalType> animalTypeList) {
        if (animalTypeList == null) {
            return null;
        }
        List<Long> idList = new ArrayList<>();
        for (AnimalType s : animalTypeList) {
            idList.add(s.getId());
        }
        return idList;
    }

    default List<Long> animalVisitedLocationToListId(List<AnimalVisitedLocation> visitedLocations) {
        if (visitedLocations == null) {
            return null;
        }
        List<Long> idList = new ArrayList<>();
        for (AnimalVisitedLocation s : visitedLocations) {
            idList.add(s.getId());
        }
        return idList;
    }

    @AfterMapping
    default void getNano(@MappingTarget ResponseAnimalDTO responseAnimalDTO, Animal animal) {
        responseAnimalDTO.setChippingDateTime(responseAnimalDTO.getChippingDateTime().withNano(animal.getChippingDateTimeNano()));
        if (animal.getDeathDateTime() != null) {
            responseAnimalDTO.setDeathDateTime(responseAnimalDTO.getDeathDateTime().withNano(animal.getChippingDateTimeNano()));
        }
    }
}
