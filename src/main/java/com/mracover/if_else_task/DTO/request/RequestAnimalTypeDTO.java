package com.mracover.if_else_task.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestAnimalTypeDTO {

    private Long id;

    @NotBlank
    private String type;
}
