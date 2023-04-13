package com.mracover.if_else_task.services;

import com.mracover.if_else_task.exception_handler.exception.NoSuchDataException;
import com.mracover.if_else_task.repositories.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final AccountRepository accountRepository;

    public CustomUserDetailService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)  {
        return accountRepository.findAccountByEmail(email).orElseThrow(() ->
            new NoSuchDataException("Пользователь не найден"));
    }
}
