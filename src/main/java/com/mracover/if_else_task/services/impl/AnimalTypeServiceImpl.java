package com.mracover.if_else_task.services.impl;

import com.mracover.if_else_task.exception_handler.exception.ConflictException;
import com.mracover.if_else_task.exception_handler.exception.NoSuchDataException;
import com.mracover.if_else_task.models.animalModels.AnimalType;
import com.mracover.if_else_task.repositories.AnimalTypeRepository;
import com.mracover.if_else_task.services.AnimalTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

@Service
public class AnimalTypeServiceImpl implements AnimalTypeService {

    private final AnimalTypeRepository animalTypeRepository;

    public AnimalTypeServiceImpl(AnimalTypeRepository animalTypeRepository) {
        this.animalTypeRepository = animalTypeRepository;
    }

    @Override
    @Transactional
    public AnimalType findAnimalTypeById(Long id) {
        return animalTypeRepository.findById(id).orElseThrow(() ->
                new NoSuchDataException("Тип животного с таким id не найдена"));
    }

    @Override
    @Transactional
    public AnimalType addNewAnimalType(AnimalType animalType) {
        if (animalTypeRepository.findByType(animalType.getType()).isPresent()) {
            throw new ConflictException("Тип животного с таким type уже существует");
        }

        return animalTypeRepository.save(animalType);
    }

    @Override
    @Transactional
    public AnimalType updateAnimalType(AnimalType animalType) {
        animalTypeRepository.findById(animalType.getId()).orElseThrow(() ->
                new NoSuchDataException("Тип животного с таким id не найдена"));

        if (animalTypeRepository.findByType(animalType.getType()).isPresent()) {
            throw new ConflictException("Тип животного с таким type уже существует");
        }

        return animalTypeRepository.save(animalType);
    }

    @Override
    @Transactional
    public void deleteAnimalTypeById(Long id) {
        AnimalType animalType = animalTypeRepository.findById(id).orElseThrow(() ->
                new NoSuchDataException("Тип животного с таким id не найдена"));

        if (!animalType.getAnimals().isEmpty()) {
            throw new ValidationException();
        }

        animalTypeRepository.deleteById(id);
    }
}
