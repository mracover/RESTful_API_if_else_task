package com.mracover.if_else_task.controllers;

import com.mracover.if_else_task.models.areaModels.Area;
import com.mracover.if_else_task.models.areaModels.analyticsModels.AreaAnalytics;
import com.mracover.if_else_task.services.AreaService;
import com.mracover.if_else_task.services.analytics.AnalyticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Validated
@RestController
@RequestMapping("/areas")
public class AreaController {

    private final AreaService areaService;
    private final AnalyticsService analyticsService;

    public AreaController(AreaService areaService, AnalyticsService analyticsService) {
        this.areaService = areaService;
        this.analyticsService = analyticsService;
    }

    @GetMapping("/{areaId}")
    public ResponseEntity<Area> getAreaById(@PathVariable("areaId") @NotNull @Positive Long id) {
        return new ResponseEntity<>(areaService.getAreaById(id), HttpStatus.OK);
    }

    @GetMapping("/{areaId}/analytics")
    public ResponseEntity<AreaAnalytics> getAnalystics(@PathVariable("areaId") @NotNull @Positive Long id,
                                                       @RequestParam(value = "startDate") @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}") String startDate,
                                                       @RequestParam(value = "endDate") @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}") String endDate) {
        return new ResponseEntity<>(analyticsService.analyticsAreas(id, LocalDate.parse(startDate), LocalDate.parse(endDate)), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Area> addNewArea(@Valid @RequestBody Area area) {
        return new ResponseEntity<>(areaService.addNewArea(area), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{areaId}")
    public ResponseEntity<Area> updateArea(@PathVariable("areaId") @NotNull @Positive Long id,
                                           @Valid @RequestBody Area area) {
        area.setId(id);
        return new ResponseEntity<>(areaService.updateArea(area), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{areaId}")
    public ResponseEntity<Void> deleteArea(@PathVariable("areaId") @NotNull @Positive Long id) {
        areaService.deleteArea(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
