package com.mracover.if_else_task.services.impl;

import com.mracover.if_else_task.exception_handler.exception.ConflictException;
import com.mracover.if_else_task.exception_handler.exception.NoSuchDataException;
import com.mracover.if_else_task.models.areaModels.Area;
import com.mracover.if_else_task.models.areaModels.AreaPoint;
import com.mracover.if_else_task.repositories.AreaRepository;
import com.mracover.if_else_task.services.AreaService;
import com.mracover.if_else_task.services.checkPolygon.CheckPolygon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;
    private final CheckPolygon checkPolygon;

    public AreaServiceImpl(AreaRepository areaRepository, CheckPolygon checkPolygon) {
        this.areaRepository = areaRepository;
        this.checkPolygon = checkPolygon;

    }

    @Override
    @Transactional(readOnly = true)
    public Area getAreaById(Long id) {
        return areaRepository.findById(id).orElseThrow(()-> new NoSuchDataException("Зона с таким areaId не найдена"));
    }

    @Transactional(readOnly = true)
    public List<Area> getAllArea() {
        return areaRepository.findAll();
    }

    @Override
    @Transactional
    public Area addNewArea(Area area) {
        List<AreaPoint> addAreaPoints = area.getAreaPoints();
        if (addAreaPoints.size() < 3) {
            throw new ValidationException("Содержит меньше 3 точек");
        }
        java.awt.geom.Area addGeomArea = checkPolygon.listAreaPointToArea(addAreaPoints);

        if (addGeomArea.isPolygonal() & addGeomArea.isSingular()) {
            checkPolygon.checkPolygonPointToLine(addAreaPoints);
            checkPolygon.checkDuplicatesPoints(addAreaPoints);
            List<Area> allAreaList = new ArrayList<>(getAllArea());

            if (!allAreaList.isEmpty()) {
                List<List<AreaPoint>> allAreaPoints = new ArrayList<>();
                for (Area a : allAreaList) {
                    allAreaPoints.add(a.getAreaPoints());
                }
                checkNameArea(allAreaList, area);
                checkPolygon.checkIntersectPolygon(allAreaPoints,addAreaPoints);
                checkPolygon.checkPointsNewPolygon(allAreaPoints,addAreaPoints);
            }

        } else {
            throw new ValidationException("Не многоугольник");
        }

        return areaRepository.save(area);
    }

    @Override
    @Transactional
    public Area updateArea(Area area) {
        List<AreaPoint> addAreaPoints = area.getAreaPoints();
        Area updateArea = getAreaById(area.getId());
        java.awt.geom.Area updateGeomArea = checkPolygon.listAreaPointToArea(addAreaPoints);

        if (addAreaPoints.size() <= 2) {
            throw new ValidationException("Содержит меньше 3 точек");
        }

        if (updateGeomArea.isPolygonal() & updateGeomArea.isSingular()) {
            List<Area> allAreaList = new ArrayList<>(getAllArea());
            checkPolygon.checkPolygonPointToLine(addAreaPoints);
            checkPolygon.checkDuplicatesPoints(addAreaPoints);

            if (!allAreaList.isEmpty()){
                allAreaList.remove(updateArea);
                if (!allAreaList.isEmpty()) {
                    List<List<AreaPoint>> allAreaPoints = new ArrayList<>();
                    for (Area a : allAreaList) {
                        allAreaPoints.add(a.getAreaPoints());
                    }
                    checkNameArea(allAreaList, area);
                    checkPolygon.checkIntersectPolygon(allAreaPoints,addAreaPoints);
                    checkPolygon.checkPointsNewPolygon(allAreaPoints,addAreaPoints);
                }
            }
        } else {
            throw new ValidationException("Не многоугольник");
        }

        return areaRepository.save(area);
    }

    @Override
    @Transactional
    public void deleteArea(Long id) {
        getAreaById(id);
        areaRepository.deleteById(id);
    }

    private void checkNameArea (List<Area> allAreaList, Area area) {
        for (Area a : allAreaList) {
            if (a.getName().equals(area.getName())) {
                throw new ConflictException("Зона с таким именем уже существует");
            }
        }
    }

}
