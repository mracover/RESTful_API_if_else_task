package com.mracover.if_else_task.models.areaModels.analyticsModels;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalsAnalytics {

    private String animalType;
    private long animalTypeId;
    private long quantityAnimals;
    private long animalsArrived;
    private long animalsGone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalsAnalytics that = (AnimalsAnalytics) o;
        return animalTypeId == that.animalTypeId && Objects.equals(animalType, that.animalType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalType, animalTypeId);
    }
}
