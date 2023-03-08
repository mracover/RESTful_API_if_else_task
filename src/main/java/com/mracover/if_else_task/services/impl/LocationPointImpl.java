package com.mracover.if_else_task.services.impl;

import com.mracover.if_else_task.exception_handler.exception.ConflictException;
import com.mracover.if_else_task.exception_handler.exception.NoSuchDataException;
import com.mracover.if_else_task.models.AnimalVisitedLocation;
import com.mracover.if_else_task.models.LocationPoint;
import com.mracover.if_else_task.repositories.AnimalVisitedLocationRepository;
import com.mracover.if_else_task.repositories.LocationPointRepository;
import com.mracover.if_else_task.services.LocationPointService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class LocationPointImpl implements LocationPointService {

    private final AnimalVisitedLocationRepository animalVisitedLocationRepository;
    public final LocationPointRepository locationPointRepository;

    public LocationPointImpl(AnimalVisitedLocationRepository animalVisitedLocationRepository,
                             LocationPointRepository locationPointRepository) {
        this.animalVisitedLocationRepository = animalVisitedLocationRepository;
        this.locationPointRepository = locationPointRepository;
    }

    @Override
    @Transactional
    public LocationPoint findLocationPointById(Long id) {
        return locationPointRepository.findById(id).orElseThrow(() ->
                new NoSuchDataException("Локация с таким id не найдена"));
    }

    @Override
    @Transactional
    public LocationPoint addNewLocationPoint(LocationPoint locationPoint) {
        if ((locationPoint.getLatitude() == 0) && (locationPoint.getLongitude() == 0)) {
            return locationPointRepository.save(locationPoint);
        }

        if (locationPointRepository.findLocationPointByLatitudeAndLongitude
                (locationPoint.getLatitude(), locationPoint.getLongitude()).isPresent()) {
            throw new ConflictException("Точка локации с такими latitude и longitude уже существует");
        }

        return locationPointRepository.save(locationPoint);
    }

    @Override
    @Transactional
    public LocationPoint updateLocationPoint(LocationPoint locationPoint) {
        LocationPoint locationPoint1 = locationPointRepository.findById(locationPoint.getId()).orElseThrow(() ->
                new NoSuchDataException("Локация с таким id не найдена"));

        if (locationPointRepository.findLocationPointByLatitudeAndLongitude
                (locationPoint.getLatitude(), locationPoint.getLongitude()).isPresent()) {
            throw new ConflictException("Точка локации с такими latitude и longitude уже существует");
        }

        locationPoint1.setLatitude(locationPoint.getLatitude());
        locationPoint1.setLongitude(locationPoint.getLongitude());
        return locationPointRepository.save(locationPoint1);
    }

    @Override
    @Transactional
    public void deleteLocationPointById(Long id) {
        LocationPoint locationPoint = locationPointRepository.findById(id).orElseThrow(() ->
                new NoSuchDataException("Локация с таким id не найдена"));

        List<AnimalVisitedLocation> animalVisitedLocations = animalVisitedLocationRepository.findAnimalVisitedLocationByLocationPointId(locationPoint);
        if ((!locationPoint.getAnimals().isEmpty()) || (!animalVisitedLocations.isEmpty())) {
            throw new ValidationException();
        }

        locationPointRepository.deleteById(id);
    }
}
