package com.mracover.if_else_task.mappers.request;

import com.mracover.if_else_task.DTO.request.animal.RequestGetAnimalDTO;
import com.mracover.if_else_task.DTO.request.animal.RequestPutAnimalDTO;
import com.mracover.if_else_task.models.userModels.Account;
import com.mracover.if_else_task.models.animalModels.Animal;
import com.mracover.if_else_task.models.animalModels.AnimalType;
import com.mracover.if_else_task.models.animalModels.locationModels.LocationPoint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestGetAndPutAnimalMapper {

    @Mapping(source = "animalTypes", target = "animalTypeList")
    Animal dtoToAnimal(RequestGetAnimalDTO requestGetAnimalDTO);

    Animal dtoPutToAnimal(RequestPutAnimalDTO requestPutAnimalDTO);

    default List<AnimalType> idTypeToAnimalType(List<Long> animalTypes) {
        if (animalTypes == null) {
            return null;
        }
        List<AnimalType> animalTypeList = new ArrayList<>();
        for (Long s : animalTypes) {
            animalTypeList.add(new AnimalType(s,null, null));
        }
        return animalTypeList;
    }

    default Account idToAccount(Integer chipperId) {
        if (chipperId == null) {
            return null;
        }
        Account account = new Account();
        account.setId(chipperId);
        return account;
    }

    default LocationPoint idToLocation(Long chippingLocationId) {
        if (chippingLocationId == null) {
            return null;
        }
        LocationPoint locationPoint = new LocationPoint();
        locationPoint.setId(chippingLocationId);
        return locationPoint;
    }

}
