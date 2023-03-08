package com.mracover.if_else_task.services.impl;

import com.mracover.if_else_task.components.LifeStatusAnimal;
import com.mracover.if_else_task.exception_handler.exception.NoSuchDataException;
import com.mracover.if_else_task.models.Animal;
import com.mracover.if_else_task.models.AnimalVisitedLocation;
import com.mracover.if_else_task.models.LocationPoint;
import com.mracover.if_else_task.repositories.AnimalRepository;
import com.mracover.if_else_task.repositories.AnimalVisitedLocationRepository;
import com.mracover.if_else_task.services.AnimalService;
import com.mracover.if_else_task.services.AnimalVisitedLocationService;
import com.mracover.if_else_task.services.LocationPointService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.validation.ValidationException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalVisitedLocationImpl implements AnimalVisitedLocationService {

    private final AnimalVisitedLocationRepository animalVisitedLocationRepository;
    private final AnimalService animalService;
    private final AnimalRepository animalRepository;
    private final LocationPointService locationPointService;

    public AnimalVisitedLocationImpl(AnimalVisitedLocationRepository animalVisitedLocationRepository,
                                     AnimalService animalService, AnimalRepository animalRepository,
                                     LocationPointService locationPointService) {
        this.animalVisitedLocationRepository = animalVisitedLocationRepository;
        this.animalService = animalService;
        this.animalRepository = animalRepository;
        this.locationPointService = locationPointService;
    }

    @Override
    @Transactional
    public List<AnimalVisitedLocation> findAnimalVisitedLocationByParameters(Long animalId, OffsetDateTime startDateTime,
                                                                             OffsetDateTime endDateTime, int from, int size) {
        return animalVisitedLocationRepository.findAll(getSpecFromDatesAndExample(startDateTime, endDateTime, animalId),
                        Sort.by("dateTimeOfVisitLocationPoint").ascending()).stream().skip(from).limit(size).toList();
    }

    public Specification<AnimalVisitedLocation> getSpecFromDatesAndExample(
            OffsetDateTime from, OffsetDateTime to, Long animalId) {
        return (Specification<AnimalVisitedLocation>) (root, query, builder) -> {
            final List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
            if (from != null) {
                predicates.add(builder.greaterThan(root.get("dateTimeOfVisitLocationPoint"), from));
            }
            if (to != null) {
                predicates.add(builder.lessThan(root.get("dateTimeOfVisitLocationPoint"), to));
            }
            if (animalId != null) {
                predicates.add(builder.equal(root.get("animal"),animalId));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    @Override
    @Transactional
    public AnimalVisitedLocation addNewAnimalVisitedLocation(Long animalId, Long pointId) {
        Animal animal = animalService.findAnimalById(animalId);
        LocationPoint locationPoint = locationPointService.findLocationPointById(pointId);

        if (animal.getLifeStatus().equals(LifeStatusAnimal.DEAD.toString())) {
            throw new ValidationException();
        }

        if (!animal.getAnimalVisitedLocationList().isEmpty() &&
                animal.getAnimalVisitedLocationList().get(animal.getAnimalVisitedLocationList().size() - 1).getLocationPointId().getId().equals(pointId)) {
            throw new ValidationException();
        }

        if (animal.getChippingLocationId().getId().equals(pointId) && animal.getAnimalVisitedLocationList().isEmpty()) {
            throw new ValidationException();
        }

        return animalVisitedLocationRepository.save(new AnimalVisitedLocation(null, OffsetDateTime.now(), OffsetDateTime.now().getNano(), locationPoint, animal));
    }

    @Override
    @Transactional
    public AnimalVisitedLocation updateAnimalVisitedLocation(AnimalVisitedLocation animalVisitedLocation, Long animalId) {
        AnimalVisitedLocation animalVisitedLocation1 = animalVisitedLocationRepository.findById(animalVisitedLocation.getId())
                .orElseThrow(() -> new NoSuchDataException("Объект с информацией о посещенной точке локации\n" +
                        "с visitedLocationPointId не найден."));

        LocationPoint locationPoint = locationPointService.findLocationPointById(animalVisitedLocation.getLocationPointId().getId());
        Animal animal = animalService.findAnimalById(animalId);

        if (!animal.getAnimalVisitedLocationList().isEmpty()) {
            validateUpdateAnimalVisitedLocation(animal, animalVisitedLocation1, locationPoint);
        }

        animalVisitedLocation1.setLocationPointId(locationPoint);
        return animalVisitedLocationRepository.save(animalVisitedLocation1);
    }

    @Override
    @Transactional
    public void deleteAnimalVisitedLocationById(Long animalId, Long visitedPointId) {
        Animal animal = animalService.findAnimalById(animalId);

        AnimalVisitedLocation animalVisitedLocation = animalVisitedLocationRepository.findById(visitedPointId)
                .orElseThrow(() -> new NoSuchDataException("Объект с информацией о посещенной точке локации\n" +
                        "с visitedLocationPointId не найден."));

        if (!(animal.getAnimalVisitedLocationList().contains(animalVisitedLocation))) {
            throw new NoSuchDataException("У животного нет объекта с информацией о посещенной точке локации с visitedPointId");
        }

        int a = animal.getAnimalVisitedLocationList().indexOf(animalVisitedLocation);

        if (a == 0 && animal.getAnimalVisitedLocationList().size() >= 2 &&
                animal.getAnimalVisitedLocationList().get(1).getLocationPointId().getId().equals(animal.getChippingLocationId().getId())) {
            animal.getAnimalVisitedLocationList().remove(1);
            animal.getAnimalVisitedLocationList().remove(animalVisitedLocation);
            animalRepository.save(animal);
        } else {
            animal.getAnimalVisitedLocationList().remove(a);
            animalRepository.save(animal);
        }
    }

    private void validateUpdateAnimalVisitedLocation(Animal animal, AnimalVisitedLocation animalVisitedLocation,
                                                     LocationPoint locationPoint) {

        int a = animal.getAnimalVisitedLocationList().indexOf(animalVisitedLocation);
        int b = animal.getAnimalVisitedLocationList().size() - 1;
        int c = b + 1;

        if (animal.getAnimalVisitedLocationList().get(0).getId().equals(animalVisitedLocation.getId()) &&
                animal.getChippingLocationId().getId().equals(locationPoint.getId())) {
            throw new ValidationException();
        }

        if (animal.getAnimalVisitedLocationList().get(b).getLocationPointId().getId().equals(locationPoint.getId())) {
            throw new ValidationException();
        }

        if ((0 <= a) && (a <= (b - 1)) &&
                ((animal.getAnimalVisitedLocationList().get(a - 1).getLocationPointId().getId().equals(locationPoint.getId())) ||
                        animal.getAnimalVisitedLocationList().get(a + 1).getLocationPointId().getId().equals(locationPoint.getId()))) {
            throw new ValidationException();
        }

        for (AnimalVisitedLocation s : animal.getAnimalVisitedLocationList()) {
            if (s.getId().equals(animalVisitedLocation.getId())) {
                c = c - 1;
            }
        }

        if (c == b + 1) {
            throw new NoSuchDataException(" У животного нет объекта с информацией о посещенной точке локации с visitedLocationPointId");
        }
    }

}
