package com.mracover.if_else_task.repositories;

import com.mracover.if_else_task.models.userModels.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findAccountByEmail (String email);
}
