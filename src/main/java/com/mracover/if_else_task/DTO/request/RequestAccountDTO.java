package com.mracover.if_else_task.DTO.request;

import com.mracover.if_else_task.components.RoleAccount;
import com.mracover.if_else_task.validators.ValidateString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestAccountDTO {

    private Integer id;

    @NotBlank(message = "Неккоректное имя: null or empty name")
    private String firstName;

    @NotBlank(message = "Неккоректная фамилия: null or empty lastName")
    private String lastName;

    @NotBlank(message = "Неккоректный email: null or empty email")
    @Email
    private String email;

    @NotBlank(message = "Неккоректный пароль: null or empty password")
    private String password;

    @ValidateString(enumClazz = RoleAccount.class)
    private String role;
}
