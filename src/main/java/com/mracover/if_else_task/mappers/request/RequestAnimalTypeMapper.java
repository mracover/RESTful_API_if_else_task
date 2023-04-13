package com.mracover.if_else_task.mappers.request;

import com.mracover.if_else_task.DTO.request.RequestAnimalTypeDTO;
import com.mracover.if_else_task.models.animalModels.AnimalType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestAnimalTypeMapper {
    AnimalType dtoToAnimalType(RequestAnimalTypeDTO requestAnimalTypeDTO);
}
