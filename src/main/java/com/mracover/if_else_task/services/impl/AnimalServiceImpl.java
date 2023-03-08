package com.mracover.if_else_task.services.impl;

import com.mracover.if_else_task.DTO.request.animal.RequestPutAnimalTypeDTO;
import com.mracover.if_else_task.components.LifeStatusAnimal;
import com.mracover.if_else_task.exception_handler.exception.ConflictException;
import com.mracover.if_else_task.exception_handler.exception.NoSuchDataException;
import com.mracover.if_else_task.models.*;
import com.mracover.if_else_task.repositories.*;
import com.mracover.if_else_task.services.AccountService;
import com.mracover.if_else_task.services.AnimalService;
import com.mracover.if_else_task.services.AnimalTypeService;
import com.mracover.if_else_task.services.LocationPointService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.validation.ValidationException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final AccountService accountService;
    private final AnimalTypeService animalTypeService;
    private final LocationPointService locationPointService;

    public AnimalServiceImpl(AnimalRepository animalRepository,
                             AccountService accountService,
                             AnimalTypeService animalTypeService,
                             LocationPointService locationPointService) {
        this.animalRepository = animalRepository;
        this.accountService = accountService;
        this.animalTypeService = animalTypeService;
        this.locationPointService = locationPointService;
    }

    @Override
    @Transactional
    public List<Animal> findAnimalsByParameters(Animal animal, OffsetDateTime startDateTime,
                                                OffsetDateTime endDateTime, int from, int size) {
        if (animal.getChipperId() != null) {
            animal.setChipperId(accountService.findAccountById(animal.getChipperId().getId()));
        }
        if (animal.getChippingLocationId() != null) {
            animal.setChippingLocationId(locationPointService.findLocationPointById(animal.getChippingLocationId().getId()));
        }

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnorePaths("id", "animalTypeList",
                "weight", "length", "height", "chippingDateTimeNano", "animalVisitedLocationList", "deathDateTime", "deathDateTimeNano");
        Example<Animal> animalExample = Example.of(animal, matcher);

        return animalRepository.findAll(getSpecFromDatesAndExample(startDateTime, endDateTime, animalExample), Sort.by("id").ascending())
                .stream().skip(from).limit(size).toList();
    }

    public Specification<Animal> getSpecFromDatesAndExample(
            OffsetDateTime from, OffsetDateTime to, Example<Animal> example) {
        return (Specification<Animal>) (root, query, builder) -> {
            final List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
            if (from != null) {
                predicates.add(builder.greaterThan(root.get("chippingDateTime"), from));
            }
            if (to != null) {
                predicates.add(builder.lessThan(root.get("chippingDateTime"), to));
            }
            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    @Override
    @Transactional
    public Animal findAnimalById(Long id) {
        return animalRepository.findById(id).orElseThrow(() ->
                new NoSuchDataException("Животное с animalId не найдено"));
    }

    @Override
    @Transactional
    public Animal addNewAnimal(Animal animal) {
        return animalRepository.save(animalAddRelation(animal));
    }

    @Override
    @Transactional
    public Animal addAnimalTypeToAnimal(Long animalId, Long typeId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(() ->
                new NoSuchDataException("Животное с animalId не найдено"));

        AnimalType animalType = animalTypeService.findAnimalTypeById(typeId);
        animal.getAnimalTypeList().add(animalType);
        return animalRepository.save(animal);
    }

    @Override
    @Transactional
    public Animal updateAnimal(Animal animal) {
        Animal animal1 = animalRepository.findById(animal.getId()).orElseThrow(() ->
                new NoSuchDataException("Животное с animalId не найдено"));

        if (!animal1.getAnimalVisitedLocationList().isEmpty() &&
                animal1.getAnimalVisitedLocationList().get(0).getLocationPointId().getId().equals(animal.getChippingLocationId().getId())) {
            throw new ValidationException();
        }

        return animalRepository.save(animalPutRelation(animal, animal1));
    }

    @Override
    @Transactional
    public Animal updateAnimalType(Long id, RequestPutAnimalTypeDTO requestPutAnimalTypeDTO) {
        Animal animal = animalRepository.findById(id).orElseThrow(() ->
                new NoSuchDataException("Животное с animalId не найдено"));

        List<AnimalType> animalTypes = animal.getAnimalTypeList();
        AnimalType animalType = animalTypeService.findAnimalTypeById(requestPutAnimalTypeDTO.getOldTypeId());

        if (!animalTypes.contains(animalType)) {
            throw new NoSuchDataException("Изменения типа животного, несуществующий у животного OldTypeId");
        }

        for (AnimalType s : animalTypes) {
            if (s.getId().equals(requestPutAnimalTypeDTO.getNewTypeId())) {
                throw new ConflictException("Разновидность животных с newTypeId уже есть у животного с animalId");
            }
        }

        animalTypes.remove(animalType);
        animalTypes.add(animalTypeService.findAnimalTypeById(requestPutAnimalTypeDTO.getNewTypeId()));
        animal.setAnimalTypeList(animalTypes);
        return animalRepository.save(animal);
    }

    @Override
    @Transactional
    public void deleteAnimalById(Long id) {
        Animal animal = animalRepository.findById(id).orElseThrow(() ->
                new NoSuchDataException("Животное с animalId не найдено"));

        if (!animal.getAnimalVisitedLocationList().isEmpty()) {
            throw new ValidationException();
        }

        animalRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Animal deleteAnimalTypeFromAnimal(Long animalId, Long typeId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(() ->
                new NoSuchDataException("Животное с animalId не найдено"));

        AnimalType animalType = animalTypeService.findAnimalTypeById(typeId);
        List<AnimalType> animalTypes = animal.getAnimalTypeList();

        if (!animalTypes.contains(animalType)) {
            throw new NoSuchDataException("Удаления типа животного, разновидность животного с typeId не найдена у животного");
        }

        if (animalTypes.size() == 1) {
            throw new ValidationException();
        }

        animalTypes.remove(animalType);
        animal.setAnimalTypeList(animalTypes);
        return animalRepository.save(animal);
    }

    //Сложный маппинг из DTO в Entity
    private Animal animalAddRelation (Animal animal) {
        LocationPoint chippingLocationId = locationPointService.findLocationPointById(animal.getChippingLocationId().getId());
        List<AnimalType> animalTypesPost = new ArrayList<>();
        List<AnimalVisitedLocation> animalVisitedLocationsPost = new ArrayList<>();

        for(AnimalType s : animal.getAnimalTypeList()) {
            AnimalType animalType = animalTypeService.findAnimalTypeById(s.getId());
            animalTypesPost.add(animalType);
        }

        animal.setAnimalTypeList(animalTypesPost);
        animal.setAnimalVisitedLocationList(animalVisitedLocationsPost);
        animal.setLifeStatus(LifeStatusAnimal.ALIVE.toString());
        animal.setChippingDateTime(OffsetDateTime.now());
        animal.setChippingDateTimeNano(OffsetDateTime.now().getNano());
        animal.setChipperId(accountService.findAccountById(animal.getChipperId().getId()));
        animal.setChippingLocationId(chippingLocationId);
        return animal;
    }

    private Animal animalPutRelation(Animal animal, Animal animal1) {
        LocationPoint chippingLocationId = locationPointService.findLocationPointById(animal.getChippingLocationId().getId());
        animal1.setWeight(animal.getWeight());
        animal1.setLength(animal.getLength());
        animal1.setHeight(animal.getHeight());
        animal1.setGender(animal.getGender());
        animal1.setLifeStatus(animal.getLifeStatus());

        if (animal.getLifeStatus().equals(LifeStatusAnimal.DEAD.toString())) {
            animal1.setDeathDateTime(OffsetDateTime.now());
            animal1.setDeathDateTimeNano(OffsetDateTime.now().getNano());
        }

        animal1.setChipperId(accountService.findAccountById(animal.getChipperId().getId()));
        animal1.setChippingLocationId(chippingLocationId);
        return animal1;
    }
}
