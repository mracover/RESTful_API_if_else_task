package com.mracover.if_else_task.repositories;

import com.mracover.if_else_task.models.animalModels.locationModels.LocationPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationPointRepository extends JpaRepository<LocationPoint, Long> {
    Optional<LocationPoint> findLocationPointByLatitudeAndLongitude(double latitude, double longitude);
}
