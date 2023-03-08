package com.mracover.if_else_task.DTO.request;

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
public class RequestPutAnimalVisitedLocationDTO {

    @NotNull
    @Positive
    private Long visitedLocationPointId;

    @NotNull
    @Positive
    private Long locationPointId;
}
