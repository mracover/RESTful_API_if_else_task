package com.mracover.if_else_task.services;

import com.mracover.if_else_task.models.animalModels.locationModels.LocationPoint;

public interface LocationPointService {
    LocationPoint findLocationByPoints(double latitude, double longitude);
    LocationPoint findLocationPointById (Long id);
    LocationPoint addNewLocationPoint (LocationPoint locationPoint);
    LocationPoint updateLocationPoint (LocationPoint locationPoint);
    void deleteLocationPointById (Long id);
}
