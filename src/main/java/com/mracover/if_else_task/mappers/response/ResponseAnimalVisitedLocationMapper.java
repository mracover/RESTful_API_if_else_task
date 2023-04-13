package com.mracover.if_else_task.mappers.response;

import com.mracover.if_else_task.DTO.response.ResponseAnimalVisitedLocationDTO;
import com.mracover.if_else_task.models.animalModels.locationModels.AnimalVisitedLocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ResponseAnimalVisitedLocationMapper {

    @Mapping(source = "locationPointId.id", target = "locationPointId")
    ResponseAnimalVisitedLocationDTO animalVisitedLocationToDTO(AnimalVisitedLocation animalVisitedLocation);
    List<ResponseAnimalVisitedLocationDTO> animalVisitedLocationListToDTO (List<AnimalVisitedLocation> animalVisitedLocation);

    default OffsetDateTime stringToDateTime(String dateTimeOfVisitLocationPoint) {
        return OffsetDateTime.parse(dateTimeOfVisitLocationPoint);
    }
}
