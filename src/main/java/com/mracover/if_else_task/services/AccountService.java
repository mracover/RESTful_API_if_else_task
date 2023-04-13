package com.mracover.if_else_task.services;

import com.mracover.if_else_task.models.userModels.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAccountByParameters(Account account, int from, int size);
    Account findAccountById (Integer id);
    Account addNewAccount (Account account);
    Account updateAccount (Account account);
    void deleteAccountById (Integer id);
    Account findAccountByEmail (String email);
}
