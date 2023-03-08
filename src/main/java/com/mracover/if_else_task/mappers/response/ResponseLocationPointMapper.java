package com.mracover.if_else_task.mappers.response;

import com.mracover.if_else_task.DTO.response.ResponseLocationPointDTO;
import com.mracover.if_else_task.models.LocationPoint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponseLocationPointMapper {
    ResponseLocationPointDTO accountToDTO (LocationPoint locationPoint);
}
