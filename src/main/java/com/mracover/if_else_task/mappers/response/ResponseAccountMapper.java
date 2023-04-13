package com.mracover.if_else_task.mappers.response;

import com.mracover.if_else_task.DTO.response.ResponseAccountDTO;
import com.mracover.if_else_task.models.userModels.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResponseAccountMapper {

    @Mapping(source = "role.role", target = "role")
    ResponseAccountDTO accountToDTO(Account account);

    List<ResponseAccountDTO> toListAccountDTO(List<Account> accounts);
}
