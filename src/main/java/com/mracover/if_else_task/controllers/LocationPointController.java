package com.mracover.if_else_task.controllers;

import com.mracover.if_else_task.DTO.request.RequestLocationPointDTO;
import com.mracover.if_else_task.DTO.response.ResponseLocationPointDTO;
import com.mracover.if_else_task.mappers.request.RequestLocationPointMapper;
import com.mracover.if_else_task.mappers.response.ResponseLocationPointMapper;
import com.mracover.if_else_task.models.LocationPoint;
import com.mracover.if_else_task.services.LocationPointService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/locations")
public class LocationPointController {

    private final LocationPointService locationPointService;
    private final RequestLocationPointMapper requestLocationPointMapper;
    private final ResponseLocationPointMapper responseLocationPointMapper;

    public LocationPointController(LocationPointService locationPointService,
                                   RequestLocationPointMapper requestLocationPointMapper,
                                   ResponseLocationPointMapper responseLocationPointMapper) {
        this.locationPointService = locationPointService;
        this.requestLocationPointMapper = requestLocationPointMapper;
        this.responseLocationPointMapper = responseLocationPointMapper;
    }

    @GetMapping("/{pointId}")
    public ResponseEntity<ResponseLocationPointDTO> getLocationPointById(@PathVariable("pointId") @NotNull @Positive Long id) {
        LocationPoint locationPoint = locationPointService.findLocationPointById(id);
        return new ResponseEntity<>(responseLocationPointMapper.accountToDTO(locationPoint), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ResponseLocationPointDTO> addNewLocationPoint(@Valid @RequestBody RequestLocationPointDTO requestLocationPointDTO) {
        LocationPoint creatLocation = locationPointService
                .addNewLocationPoint(requestLocationPointMapper.dtoToLocationPoint(requestLocationPointDTO));
        return new ResponseEntity<>(responseLocationPointMapper.accountToDTO(creatLocation), HttpStatus.CREATED);
    }

    @PutMapping("/{pointId}")
    public ResponseEntity<ResponseLocationPointDTO> updateLocationPoint(@PathVariable("pointId") @NotNull @Positive Long id,
                                                             @Valid @RequestBody RequestLocationPointDTO requestLocationPointDTO) {
        requestLocationPointDTO.setId(id);
        LocationPoint updLocationPoint = locationPointService.updateLocationPoint
                (requestLocationPointMapper.dtoToLocationPoint(requestLocationPointDTO));
        return new ResponseEntity<>(responseLocationPointMapper.accountToDTO(updLocationPoint), HttpStatus.OK);
    }

    @DeleteMapping("/{pointId}")
    public ResponseEntity<Void> deleteLocationPoint(@PathVariable("pointId") @NotNull @Positive Long id) {
        locationPointService.deleteLocationPointById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
