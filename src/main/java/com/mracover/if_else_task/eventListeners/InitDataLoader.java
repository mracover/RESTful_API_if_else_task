package com.mracover.if_else_task.eventListeners;

import com.mracover.if_else_task.models.userModels.Account;
import com.mracover.if_else_task.models.userModels.Role;
import com.mracover.if_else_task.services.AccountService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final AccountService accountService;

    public InitDataLoader(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Account accountAdmin = Account.builder().id(1).firstName("adminFirstName").lastName("adminLastName")
                .email("admin@simbirsoft.com").password("qwerty123").role(new Role(1, "ADMIN")).build();
        Account accountChipper = Account.builder().id(2).firstName("chipperFirstName").lastName("chipperLastName")
                .email("chipper@simbirsoft.com").password("qwerty123").role(new Role(2, "CHIPPER")).build();
        Account accountUser = Account.builder().id(3).firstName("userFirstName").lastName("userLastName")
                .email("user@simbirsoft.com").password("qwerty123").role(new Role(3, "USER")).build();

        accountService.addNewAccount(accountAdmin);
        accountService.addNewAccount(accountChipper);
        accountService.addNewAccount(accountUser);
    }
}
