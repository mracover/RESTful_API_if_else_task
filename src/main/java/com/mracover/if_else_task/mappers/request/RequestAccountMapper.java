package com.mracover.if_else_task.mappers.request;

import com.mracover.if_else_task.DTO.request.RequestAccountDTO;
import com.mracover.if_else_task.models.userModels.Account;
import com.mracover.if_else_task.models.userModels.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestAccountMapper {
    Account dtoToAccount(RequestAccountDTO requestAccountDTO);

    default Role StringRoleToRole(String role) {
        Role role1 = new Role();
        role1.setRole(role);
        return role1;
    }
}
