package com.mracover.if_else_task.services.analytics;

import com.mracover.if_else_task.models.animalModels.Animal;
import com.mracover.if_else_task.models.animalModels.AnimalType;
import com.mracover.if_else_task.models.animalModels.locationModels.AnimalVisitedLocation;
import com.mracover.if_else_task.models.areaModels.AreaPoint;
import com.mracover.if_else_task.models.areaModels.analyticsModels.AnimalsAnalytics;
import com.mracover.if_else_task.models.areaModels.analyticsModels.AreaAnalytics;
import com.mracover.if_else_task.repositories.AnimalRepository;
import com.mracover.if_else_task.repositories.AnimalVisitedLocationRepository;
import com.mracover.if_else_task.services.AreaService;
import com.mracover.if_else_task.services.checkPolygon.CheckPolygon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.List;

@Service
public class AnalyticsService {

    private final AreaService areaService;
    private final CheckPolygon checkPolygon;
    private final AnimalVisitedLocationRepository animalVisitedLocationRepository;
    private final AnimalRepository animalRepository;


    public AnalyticsService(AreaService areaService, CheckPolygon checkPolygon,
                            AnimalVisitedLocationRepository animalVisitedLocationRepository, AnimalRepository animalRepository) {
        this.areaService = areaService;
        this.checkPolygon = checkPolygon;
        this.animalVisitedLocationRepository = animalVisitedLocationRepository;
        this.animalRepository = animalRepository;
    }

    @Transactional
    public AreaAnalytics analyticsAreas(Long id, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate) || startDate == endDate) {
            throw new ValidationException("endDate раньше или равен startDate");
        }
        List<AnimalsAnalytics> animalsAnalytics = new ArrayList<>();
        AreaAnalytics areaAnalytics = new AreaAnalytics();

        List<AreaPoint> areaPoints = new ArrayList<>(areaService.getAreaById(id).getAreaPoints());
        double[] lon = areaPoints.stream().mapToDouble(AreaPoint::getLongitude).toArray();
        double[] lat = areaPoints.stream().mapToDouble(AreaPoint::getLatitude).toArray();
        List<AnimalVisitedLocation> animalVisitedLocations1 = new ArrayList<>(animalVisitedLocationRepository.findAll());

        List<AnimalVisitedLocation> animalVisitedLocations = new ArrayList<>(animalVisitedLocations1.stream()
                .filter(a -> OffsetDateTime.parse(a.getDateTimeOfVisitLocationPoint()).toLocalDate().isBefore(endDate) &&
                        OffsetDateTime.parse(a.getDateTimeOfVisitLocationPoint()).toLocalDate().isAfter(startDate) &&
                        isInMaxArea(a.getLocationPointId().getLongitude(), a.getLocationPointId().getLatitude(), lon, lat, areaPoints)).toList());

        List<Animal> animals = new ArrayList<>(animalVisitedLocations.stream().map(AnimalVisitedLocation::getAnimal).distinct().toList());

        List<Animal> animalList1 = new ArrayList<>(animalRepository.findAll());
        List<Animal> animalList = new ArrayList<>(animalList1.stream().filter(a -> a.getChippingDateTime()
                .withNano(a.getChippingDateTimeNano()).toLocalDate().isBefore(endDate) && a.getChippingDateTime()
                .withNano(a.getChippingDateTimeNano()).toLocalDate().isAfter(startDate) &&
                isInMaxArea(a.getChippingLocationId().getLongitude(), a.getChippingLocationId().getLatitude(), lon, lat, areaPoints)).toList());

        for (Animal s : animalList) {
            if (!animals.contains(s)) {
                animals.add(s);
            }
        }

        List<Long> idType = new ArrayList<>();
        for (Animal s : animals) {
            for (AnimalType a : s.getAnimalTypeList()) {
                AnimalsAnalytics animalsAnalyticsForOneType = new AnimalsAnalytics();
                animalsAnalyticsForOneType.setAnimalType(a.getType());
                animalsAnalyticsForOneType.setAnimalTypeId(a.getId());
                if (!idType.contains(a.getId())) {
                    animalsAnalytics.add(animalsAnalyticsForOneType);
                    idType.add(a.getId());
                }

            }
        }
        areaAnalytics.setTotalQuantityAnimals(animals.size());


        long totalAnimalsArrived = 0;
        long totalAnimalsGone = 0;

        for (Animal a : animals) {
            long animalsArrived = 0;
            long animalsGone = 0;
            List<AnimalVisitedLocation> visitedLocationsForOneAnimal = new ArrayList<>(a.getAnimalVisitedLocationList());
            visitedLocationsForOneAnimal.sort(Comparator.comparing(o -> OffsetDateTime.parse(o.getDateTimeOfVisitLocationPoint())));

            for (AnimalVisitedLocation s : visitedLocationsForOneAnimal) {
                LocalDate date = OffsetDateTime.parse(s.getDateTimeOfVisitLocationPoint()).toLocalDate();
                if (date.isBefore(endDate) && date.isAfter(startDate)) {

                    if (!(isInMaxArea(a.getChippingLocationId().getLongitude(), a.getChippingLocationId().getLatitude(), lon, lat, areaPoints))
                            && isInMaxArea(s.getLocationPointId().getLongitude(), s.getLocationPointId().getLatitude(), lon, lat, areaPoints) && animalsArrived < 1
                            && animalsGone < 1) {
                        totalAnimalsArrived++;
                        animalsArrived ++;
                    }
                    if ((!(isInMaxArea(s.getLocationPointId().getLongitude(), s.getLocationPointId().getLatitude(), lon, lat, areaPoints)) && animalsArrived == 1)
                    || (isInMaxArea(a.getChippingLocationId().getLongitude(), a.getChippingLocationId().getLatitude(), lon, lat, areaPoints) &&
                            !(isInMaxArea(s.getLocationPointId().getLongitude(), s.getLocationPointId().getLatitude(), lon, lat, areaPoints)) && animalsGone < 1)) {
                        totalAnimalsGone++;
                        animalsGone++;
                    }
                }

            }

            for (AnimalType s : a.getAnimalTypeList()) {
                AnimalsAnalytics tempAnalytics = new AnimalsAnalytics();
                tempAnalytics.setAnimalType(s.getType());
                tempAnalytics.setAnimalTypeId(s.getId());
                AnimalsAnalytics analytics = new AnimalsAnalytics();

                for (AnimalsAnalytics n : animalsAnalytics) {
                    if (n.getAnimalTypeId() == (s.getId())) {
                        analytics = n;
                        break;
                    }
                }

                analytics.setQuantityAnimals(analytics.getQuantityAnimals() + 1 - animalsGone);
                analytics.setAnimalsArrived(analytics.getAnimalsArrived() + animalsArrived);
                analytics.setAnimalsGone(analytics.getAnimalsGone() + animalsGone);
            }

        }
        areaAnalytics.setTotalAnimalsArrived(totalAnimalsArrived);
        areaAnalytics.setTotalAnimalsGone(totalAnimalsGone);
        areaAnalytics.setAnimalsAnalytics(animalsAnalytics);
        areaAnalytics.setTotalQuantityAnimals(areaAnalytics.getTotalQuantityAnimals()-totalAnimalsGone);
        return areaAnalytics;
    }



    public boolean isInMaxArea(double pointLon, double pointLat, double[] lon, double[] lat, List<AreaPoint> areaPoints) {

        // Получаем максимальное и минимальное значения горизонтальных и вертикальных координат области
        double temp = 0.0;
        for (int i = 0; i < lon.length; i++) {
            for (int j = 0; j < lon.length - i - 1; j++) {
                if (lon[j] > lon[j + 1]) {
                    temp = lon[j];
                    lon[j] = lon[j + 1];
                    lon[j + 1] = temp;
                }
            }
        }

        for (int i = 0; i < lat.length; i++) {
            for (int j = 0; j < lat.length - i - 1; j++) {
                if (lat[j] > lat[j + 1]) {
                    temp = lat[j];
                    lat[j] = lat[j + 1];
                    lat[j + 1] = temp;
                }
            }
        }

        if (pointLon < lon[0] || pointLon > lon[lon.length - 1] || pointLat < lat[0]
                || pointLat > lat[lat.length - 1]) {
            return false;
        } else {
            return true;
        }
    }
}
