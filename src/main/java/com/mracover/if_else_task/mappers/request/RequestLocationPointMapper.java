package com.mracover.if_else_task.mappers.request;

import com.mracover.if_else_task.DTO.request.RequestLocationPointDTO;
import com.mracover.if_else_task.models.LocationPoint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestLocationPointMapper {
    LocationPoint dtoToLocationPoint (RequestLocationPointDTO requestLocationPointDTO);
}
