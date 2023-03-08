package com.mracover.if_else_task.DTO.request.animal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPutAnimalTypeDTO {

    @NotNull
    @Positive
    private Long oldTypeId;

    @NotNull
    @Positive
    private Long newTypeId;
}
