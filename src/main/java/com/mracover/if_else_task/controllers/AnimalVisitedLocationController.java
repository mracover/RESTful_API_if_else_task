package com.mracover.if_else_task.controllers;

import com.mracover.if_else_task.DTO.request.RequestPutAnimalVisitedLocationDTO;
import com.mracover.if_else_task.DTO.response.ResponseAnimalVisitedLocationDTO;
import com.mracover.if_else_task.mappers.request.RequestPutAnimalVisitedLocationMapper;
import com.mracover.if_else_task.mappers.response.ResponseAnimalVisitedLocationMapper;
import com.mracover.if_else_task.models.animalModels.locationModels.AnimalVisitedLocation;
import com.mracover.if_else_task.services.AnimalVisitedLocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.OffsetDateTime;
import java.util.List;

@Validated
@RestController
@RequestMapping("/animals")
public class AnimalVisitedLocationController {

    private final AnimalVisitedLocationService animalVisitedLocationService;
    private final ResponseAnimalVisitedLocationMapper responseAnimalVisitedLocationMapper;
    private final RequestPutAnimalVisitedLocationMapper requestPutAnimalVisitedLocationMapper;

    public AnimalVisitedLocationController(AnimalVisitedLocationService animalVisitedLocationService,
                                           ResponseAnimalVisitedLocationMapper responseAnimalVisitedLocationMapper,
                                           RequestPutAnimalVisitedLocationMapper requestPutAnimalVisitedLocationMapper) {
        this.animalVisitedLocationService = animalVisitedLocationService;
        this.responseAnimalVisitedLocationMapper = responseAnimalVisitedLocationMapper;
        this.requestPutAnimalVisitedLocationMapper = requestPutAnimalVisitedLocationMapper;
    }

    @GetMapping("/{animalId}/locations")
    public ResponseEntity<List<ResponseAnimalVisitedLocationDTO>>
    getAnimalVisitedLocationById(@RequestParam(value = "startDateTime", required = false) String startDateTime,
                                 @RequestParam(value = "endDateTime", required = false) String endDateTime,
                                 @RequestParam(value = "from", defaultValue = "0", required = false) @NotNull @PositiveOrZero Integer from,
                                 @RequestParam(value = "size", defaultValue = "10", required = false) @NotNull @Positive Integer size,
                                 @PathVariable("animalId") @NotNull @Positive Long id) {
        OffsetDateTime offsetStartDateTime = null;
        OffsetDateTime offsetEndDateTime = null;
        if (startDateTime != null) {
            offsetStartDateTime = OffsetDateTime.parse(startDateTime);
        }
        if (endDateTime != null) {
            offsetEndDateTime = OffsetDateTime.parse(endDateTime);
        }
        List<AnimalVisitedLocation> animalVisitedLocations = animalVisitedLocationService
                .findAnimalVisitedLocationByParameters(id, offsetStartDateTime, offsetEndDateTime, from, size);
        return new ResponseEntity<>(responseAnimalVisitedLocationMapper.animalVisitedLocationListToDTO(animalVisitedLocations),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHIPPER')")
    @PostMapping("/{animalId}/locations/{pointId}")
    public ResponseEntity<ResponseAnimalVisitedLocationDTO> addNewAnimalVisitedLocation(@PathVariable("animalId") @NotNull @Positive Long animalId,
                                                                                        @PathVariable("pointId") @NotNull @Positive Long pointId) {
        return new ResponseEntity<>(responseAnimalVisitedLocationMapper.animalVisitedLocationToDTO(
                animalVisitedLocationService.addNewAnimalVisitedLocation(animalId, pointId)), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHIPPER')")
    @PutMapping("/{animalId}/locations")
    public ResponseEntity<ResponseAnimalVisitedLocationDTO> updateAnimalVisitedLocation(@PathVariable("animalId") @NotNull @Positive Long animalId,
                                                                                        @Valid @RequestBody RequestPutAnimalVisitedLocationDTO requestPutAnimalVisitedLocationDTO) {
        AnimalVisitedLocation animalVisitedLocation = animalVisitedLocationService.updateAnimalVisitedLocation(
                requestPutAnimalVisitedLocationMapper.dtoToAnimalVisitedLocation(requestPutAnimalVisitedLocationDTO), animalId);
        return new ResponseEntity<>(responseAnimalVisitedLocationMapper.animalVisitedLocationToDTO(animalVisitedLocation), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{animalId}/locations/{visitedPointId}")
    public ResponseEntity<Void> deleteAnimalVisitedLocation(@PathVariable("animalId") @NotNull @Positive Long animalId,
                                                                                        @PathVariable("visitedPointId") @NotNull @Positive Long visitedPointId) {
        animalVisitedLocationService.deleteAnimalVisitedLocationById(animalId, visitedPointId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
