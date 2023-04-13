package com.mracover.if_else_task.services.impl;

import com.mracover.if_else_task.models.userModels.Role;
import com.mracover.if_else_task.repositories.RoleRepository;
import com.mracover.if_else_task.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByRole(String role) {
        return roleRepository.findRoleByRole(role);
    }
}
