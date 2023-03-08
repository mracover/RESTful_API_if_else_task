package com.mracover.if_else_task.mappers.response;

import com.mracover.if_else_task.DTO.response.ResponseAnimalTypeDTO;
import com.mracover.if_else_task.models.AnimalType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponseAnimalTypeMapper {

    ResponseAnimalTypeDTO AnimalTypeToDTO(AnimalType animalType);

}
