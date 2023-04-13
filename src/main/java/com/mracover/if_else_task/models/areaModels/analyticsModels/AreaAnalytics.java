package com.mracover.if_else_task.models.areaModels.analyticsModels;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AreaAnalytics {

    private long totalQuantityAnimals;
    private long totalAnimalsArrived;
    private long totalAnimalsGone;
    private List<AnimalsAnalytics> animalsAnalytics;
}
