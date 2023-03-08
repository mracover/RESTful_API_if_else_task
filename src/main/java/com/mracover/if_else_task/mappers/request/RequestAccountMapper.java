package com.mracover.if_else_task.mappers.request;

import com.mracover.if_else_task.DTO.request.RequestAccountDTO;
import com.mracover.if_else_task.models.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestAccountMapper {
    Account dtoToAccount(RequestAccountDTO requestAccountDTO);
}
