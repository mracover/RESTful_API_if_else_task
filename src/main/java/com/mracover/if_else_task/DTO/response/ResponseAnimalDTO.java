package com.mracover.if_else_task.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAnimalDTO {

    private long id;
    private List<Long> animalTypes;
    private float weight;
    private float length;
    private float height;
    private String gender;
    private String lifeStatus;
    private OffsetDateTime chippingDateTime;
    private int chipperId;
    private long chippingLocationId;
    private List<Long> visitedLocations;
    private OffsetDateTime deathDateTime;
}
