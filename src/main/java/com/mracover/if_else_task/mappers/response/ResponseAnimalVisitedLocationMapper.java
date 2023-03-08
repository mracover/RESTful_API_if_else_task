package com.mracover.if_else_task.mappers.response;

import com.mracover.if_else_task.DTO.response.ResponseAnimalVisitedLocationDTO;
import com.mracover.if_else_task.models.AnimalVisitedLocation;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResponseAnimalVisitedLocationMapper {

    @Mapping(source = "locationPointId.id", target = "locationPointId")
    ResponseAnimalVisitedLocationDTO animalVisitedLocationToDTO(AnimalVisitedLocation animalVisitedLocation);
    List<ResponseAnimalVisitedLocationDTO> animalVisitedLocationListToDTO (List<AnimalVisitedLocation> animalVisitedLocation);

    @AfterMapping
    default void getNano(@MappingTarget ResponseAnimalVisitedLocationDTO responseAnimalVisitedLocationDTO, AnimalVisitedLocation animalVisitedLocation) {
        responseAnimalVisitedLocationDTO.setDateTimeOfVisitLocationPoint(
                responseAnimalVisitedLocationDTO.getDateTimeOfVisitLocationPoint().withNano(animalVisitedLocation.getDateTimeOfVisitLocationPointNano()));
    }
}
