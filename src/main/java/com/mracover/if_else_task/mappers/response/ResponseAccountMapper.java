package com.mracover.if_else_task.mappers.response;

import com.mracover.if_else_task.DTO.response.ResponseAccountDTO;
import com.mracover.if_else_task.models.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ResponseAccountMapper {
    ResponseAccountDTO accountToDTO(Account account);

    List<ResponseAccountDTO> toListAccountDTO(List<Account> accounts);
}
