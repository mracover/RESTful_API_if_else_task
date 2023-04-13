package com.mracover.if_else_task.services;

import com.mracover.if_else_task.models.animalModels.locationModels.AnimalVisitedLocation;

import java.time.OffsetDateTime;
import java.util.List;

public interface AnimalVisitedLocationService {
    List<AnimalVisitedLocation> findAnimalVisitedLocationByParameters(Long animalId, OffsetDateTime startDateTime, OffsetDateTime endDateTime,
                                                                      int from, int size);
    AnimalVisitedLocation addNewAnimalVisitedLocation (Long animalId, Long pointId);
    AnimalVisitedLocation updateAnimalVisitedLocation (AnimalVisitedLocation animalVisitedLocation, Long animalId);
    void deleteAnimalVisitedLocationById (Long animalId, Long visitedPointId);
}
