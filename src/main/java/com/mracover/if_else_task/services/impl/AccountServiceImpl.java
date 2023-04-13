package com.mracover.if_else_task.services.impl;

import com.mracover.if_else_task.exception_handler.exception.ConflictException;
import com.mracover.if_else_task.exception_handler.exception.ForbiddenException;
import com.mracover.if_else_task.exception_handler.exception.NoSuchDataException;
import com.mracover.if_else_task.models.userModels.Account;
import com.mracover.if_else_task.repositories.AccountRepository;
import com.mracover.if_else_task.services.AccountService;
import com.mracover.if_else_task.services.RoleService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository,
                              RoleService roleService,
                              PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public List<Account> findAccountByParameters(Account account, int from, int size) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase(true)
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreNullValues().withIgnorePaths("id", "password", "animalList");
        Example<Account> accountExample = Example.of(account, matcher);

        return accountRepository.findAll(accountExample, Sort.by("id").ascending()).stream()
                .skip(from).limit(size).toList();
    }

    @Override
    @Transactional
    public Account findAccountById(Integer id) {
        return accountRepository.findById(id).orElseThrow(() ->
                new NoSuchDataException("Пользователь не найден с таким id не найден"));
    }

    @Override
    @Transactional
    public Account addNewAccount(Account account) {
        if (accountRepository.findAccountByEmail(account.getEmail()).isPresent()) {
            throw new ConflictException("Пользователь с таким логином уже существует");
        }

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if (account.getRole().getId() == null) {
            account.setRole(roleService.findRoleByRole(account.getRole().getRole()));
        }
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account updateAccount(Account account) {
        Account account1 = accountRepository.findById(account.getId()).orElseThrow(() ->
                new ForbiddenException("Пользователь не найден"));

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRole(roleService.findRoleByRole(account.getRole().getRole()));
        account.setAnimalList(account1.getAnimalList());
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteAccountById(Integer id) {
        Account account = accountRepository.findById(id).orElseThrow(() ->
                new NoSuchDataException("Пользователь не найден"));

        if (!account.getAnimalList().isEmpty()) {
            throw new ValidationException();
        }

        accountRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Account findAccountByEmail(String email) {
        return accountRepository.findAccountByEmail(email).orElseThrow(() ->
                new NoSuchDataException("Пользователь c таким email не найден"));
    }
}
