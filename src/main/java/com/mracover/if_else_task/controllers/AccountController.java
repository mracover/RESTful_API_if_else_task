package com.mracover.if_else_task.controllers;

import com.mracover.if_else_task.DTO.request.RequestAccountDTO;
import com.mracover.if_else_task.DTO.response.ResponseAccountDTO;
import com.mracover.if_else_task.exception_handler.exception.ForbiddenException;
import com.mracover.if_else_task.mappers.request.RequestAccountMapper;
import com.mracover.if_else_task.mappers.response.ResponseAccountMapper;
import com.mracover.if_else_task.models.Account;
import com.mracover.if_else_task.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.security.Principal;
import java.util.List;

@Validated
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final ResponseAccountMapper responseAccountMapper;
    private final RequestAccountMapper requestAccountMapper;

    public AccountController(AccountService accountService,
                             ResponseAccountMapper responseAccountMapper,
                             RequestAccountMapper requestAccountMapper) {
        this.accountService = accountService;
        this.responseAccountMapper = responseAccountMapper;
        this.requestAccountMapper = requestAccountMapper;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<ResponseAccountDTO> getAccountById(@PathVariable("accountId") @NotNull @Positive Integer id) {
        Account account = accountService.findAccountById(id);
        return new ResponseEntity<>(responseAccountMapper.accountToDTO(account), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResponseAccountDTO>> getAccountsByParameters(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "from", defaultValue = "0", required = false) @NotNull @PositiveOrZero Integer from,
            @RequestParam(value = "size", defaultValue = "10", required = false) @NotNull @Positive Integer size) {
        Account account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        List<Account> accounts = accountService.findAccountByParameters(account, from, size);
        return new ResponseEntity<>(responseAccountMapper.toListAccountDTO(accounts), HttpStatus.OK);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<ResponseAccountDTO> updateAccount(@PathVariable("accountId") @NotNull @Positive Integer id,
                                                            @Valid @RequestBody RequestAccountDTO requestAccountDTO,
                                                            Principal principal) {
        if (!id.equals(accountService.findAccountByEmail(principal.getName()).getId())) {
            throw new ForbiddenException("Обновление не своего аккаунта");
        }

        requestAccountDTO.setId(id);
        Account account = accountService.updateAccount(requestAccountMapper.dtoToAccount(requestAccountDTO));
        return new ResponseEntity<>(responseAccountMapper.accountToDTO(account), HttpStatus.OK);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("accountId") @NotNull @Positive Integer id,
                                              Principal principal) {
        if (!id.equals(accountService.findAccountByEmail(principal.getName()).getId())) {
            throw new ForbiddenException("Удаление не своего аккаунта");
        }

        accountService.deleteAccountById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
