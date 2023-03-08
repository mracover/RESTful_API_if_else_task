package com.mracover.if_else_task.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAnimalVisitedLocationDTO {

    private Long id;
    private OffsetDateTime dateTimeOfVisitLocationPoint;
    private Long locationPointId;
}
