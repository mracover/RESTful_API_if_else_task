package com.mracover.if_else_task.controllers;

import com.mracover.if_else_task.DTO.request.RequestAccountDTO;
import com.mracover.if_else_task.DTO.response.ResponseAccountDTO;
import com.mracover.if_else_task.mappers.request.RequestAccountMapper;
import com.mracover.if_else_task.mappers.response.ResponseAccountMapper;
import com.mracover.if_else_task.models.Account;
import com.mracover.if_else_task.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    private final AccountService accountService;
    private final ResponseAccountMapper responseAccountMapper;

    private final RequestAccountMapper requestAccountMapper;

    public RegistrationController(AccountService accountService,
                                  ResponseAccountMapper responseAccountMapper,
                                  RequestAccountMapper requestAccountMapper) {
        this.accountService = accountService;
        this.responseAccountMapper = responseAccountMapper;
        this.requestAccountMapper = requestAccountMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<ResponseAccountDTO> registrationAccount(@Valid @RequestBody RequestAccountDTO requestAccountDTO) {
        Account regAccount = accountService.addNewAccount(requestAccountMapper.dtoToAccount(requestAccountDTO));
        return new ResponseEntity<>(responseAccountMapper.accountToDTO(regAccount), HttpStatus.CREATED);
    }
}
