package com.mracover.if_else_task.repositories;

import com.mracover.if_else_task.models.animalModels.locationModels.AnimalVisitedLocation;
import com.mracover.if_else_task.models.animalModels.locationModels.LocationPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalVisitedLocationRepository extends JpaRepository<AnimalVisitedLocation, Long>, JpaSpecificationExecutor<AnimalVisitedLocation> {

    List<AnimalVisitedLocation> findAnimalVisitedLocationByLocationPointId (LocationPoint locationPoint);
}
