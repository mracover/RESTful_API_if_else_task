package com.mracover.if_else_task.repositories;

import com.mracover.if_else_task.models.userModels.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRole(String role);
}
