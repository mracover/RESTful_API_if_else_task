package com.mracover.if_else_task.models.areaModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AreaPoint {

    @Min(-180)
    @Max(180)
    @NotNull
    @Column
    private double longitude;

    @Min(-90)
    @Max(90)
    @NotNull
    @Column
    private double latitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaPoint areaPoint = (AreaPoint) o;
        return Double.compare(areaPoint.longitude, longitude) == 0 && Double.compare(areaPoint.latitude, latitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }
}
