package com.mracover.if_else_task.DTO.request.animal;

import com.mracover.if_else_task.components.GenderAnimal;
import com.mracover.if_else_task.validators.ValidateString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPostAnimalDTO {

    @NotNull
    private List<Long> animalTypes;

    @NotNull
    @Positive
    private Float weight;

    @NotNull
    @Positive
    private Float length;

    @NotNull
    @Positive
    private Float height;

    @NotNull
    @ValidateString(enumClazz = GenderAnimal.class)
    private String gender;

    @NotNull
    @Positive
    private Integer chipperId;

    @NotNull
    @Positive
    private Long chippingLocationId;
}
