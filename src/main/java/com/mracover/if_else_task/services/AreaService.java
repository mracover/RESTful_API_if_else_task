package com.mracover.if_else_task.services;

import com.mracover.if_else_task.models.areaModels.Area;

public interface AreaService {

    Area getAreaById (Long id);
    Area addNewArea (Area area);
    Area updateArea (Area area);
    void deleteArea (Long id);
}
