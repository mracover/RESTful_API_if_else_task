package com.mracover.if_else_task.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestLocationPointDTO {

    private Long id;

    @Min(-90)
    @Max(90)
    @NotNull
    private Double latitude;

    @Min(-180)
    @Max(180)
    @NotNull
    private Double longitude;
}
