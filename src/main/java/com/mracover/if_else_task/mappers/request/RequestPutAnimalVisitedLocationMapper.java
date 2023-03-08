package com.mracover.if_else_task.mappers.request;

import com.mracover.if_else_task.DTO.request.RequestPutAnimalVisitedLocationDTO;
import com.mracover.if_else_task.models.AnimalVisitedLocation;
import com.mracover.if_else_task.models.LocationPoint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RequestPutAnimalVisitedLocationMapper {

    @Mapping(source = "visitedLocationPointId", target = "id")
    AnimalVisitedLocation dtoToAnimalVisitedLocation (RequestPutAnimalVisitedLocationDTO requestPutAnimalVisitedLocationDTO);

    default LocationPoint idToLocationPoint(Long locationPointId) {
        if (locationPointId == null) {
            return null;
        }
        LocationPoint locationPoint = new LocationPoint();
        locationPoint.setId(locationPointId);
        return locationPoint;
    }
}
