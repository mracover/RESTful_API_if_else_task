package com.mracover.if_else_task.services.checkPolygon;

import com.mracover.if_else_task.exception_handler.exception.ConflictException;
import com.mracover.if_else_task.models.areaModels.AreaPoint;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.util.*;

@Component
public class CheckPolygon {

    public Area listAreaPointToArea (List<AreaPoint> areaPoints) {
        Path2D path2D = new Path2D.Double();
        path2D.moveTo(areaPoints.get(0).getLongitude(), areaPoints.get(0).getLatitude());
        for (int i = 1; i < areaPoints.size(); i++) {
            path2D.lineTo(areaPoints.get(i).getLongitude(), areaPoints.get(i).getLatitude());
        }
        path2D.lineTo(areaPoints.get(0).getLongitude(), areaPoints.get(0).getLatitude());
        return new Area(path2D);
    }

    public void checkPolygonPointToLine(List<AreaPoint> areaPoints) {
        Set<Double> latitude = new HashSet<>();
        Set<Double> longitude = new HashSet<>();
        for (AreaPoint a : areaPoints) {
            latitude.add(a.getLatitude());
            longitude.add(a.getLongitude());
        }
        if (latitude.size() == 1 || longitude.size() == 1) {
            throw new ValidationException("Точки расположенны на одной линии");
        }
    }

    public void checkDuplicatesPoints(List<AreaPoint> areaPoints) {
        Set<AreaPoint> areaPoints1 = new HashSet<>(areaPoints);
        if (areaPoints1.size() != areaPoints.size()) {
            throw new ValidationException("Дубликаты точек");
        }
    }

    public void checkPointsNewPolygon(List<List<AreaPoint>> allAreaPoints, List<AreaPoint> addAreaPoints) {
        List<AreaPoint> addAreaPointsCheck = new ArrayList<>(addAreaPoints);
        addAreaPointsCheck.remove(0);
        List<List<AreaPoint>> areaPointsCheck = new ArrayList<>(allAreaPoints);
        for (List<AreaPoint> s : areaPointsCheck) {
            if (!s.isEmpty()) {
                List<AreaPoint> areaPoints = new ArrayList<>(s);
                areaPoints.remove(0);
                if (new HashSet<>(areaPoints).containsAll(addAreaPointsCheck)) {
                    throw new ConflictException("Зона, состоящая из таких точек, уже существует");
                }
            }
        }
    }

    public void checkIntersectPolygon(List<List<AreaPoint>> allAreaPoints, List<AreaPoint> addAreaPoints) {
        List<java.awt.geom.Area> areas = new ArrayList<>();
        java.awt.geom.Area area = listAreaPointToArea(addAreaPoints);
        List<List<AreaPoint>> checkAllAreaPoints = new ArrayList<>(allAreaPoints);
        for (List<AreaPoint> a : checkAllAreaPoints) {
            List<AreaPoint> areaPoints = new ArrayList<>(a);
            if (!a.isEmpty()) {
                areas.add(listAreaPointToArea(areaPoints));
            }
        }

        for (java.awt.geom.Area a : areas) {
            Area area1 = new Area(area);
            area1.intersect(a);
            if (!area1.isEmpty()) {
                throw new ValidationException("Новая зона пересекает имеющиеся");
            }
        }

    }

}
