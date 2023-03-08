package com.mracover.if_else_task.services;

import com.mracover.if_else_task.models.LocationPoint;

public interface LocationPointService {
    LocationPoint findLocationPointById (Long id);
    LocationPoint addNewLocationPoint (LocationPoint locationPoint);
    LocationPoint updateLocationPoint (LocationPoint locationPoint);
    void deleteLocationPointById (Long id);
}
