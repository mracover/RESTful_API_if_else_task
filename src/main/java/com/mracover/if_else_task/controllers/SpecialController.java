package com.mracover.if_else_task.controllers;

import ch.hsr.geohash.GeoHash;
import com.mracover.if_else_task.services.LocationPointService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Base64;


@Validated
@RestController
@RequestMapping("/locations")
public class SpecialController {

    private final LocationPointService locationPointService;

    public SpecialController(LocationPointService locationPointService) {
        this.locationPointService = locationPointService;
    }

    @GetMapping
    public ResponseEntity<Long> getLocationSpecialOne(@RequestParam(value = "latitude") @NotNull Double latitude,
                                                     @RequestParam(value = "longitude") @NotNull Double longitude) {
        return new ResponseEntity<>(locationPointService.findLocationByPoints(latitude, longitude).getId(),HttpStatus.OK);
    }

    @GetMapping("/geohash")
    public ResponseEntity<String> getLocationSpecialTwo(@RequestParam(value = "latitude") @NotNull Double latitude,
                                                     @RequestParam(value = "longitude") @NotNull Double longitude) {
        String geoHash = GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, 12);
        return new ResponseEntity<>(geoHash,HttpStatus.OK);
    }

    @GetMapping("/geohashv2")
    public ResponseEntity<String> getLocationSpecialThree(@RequestParam(value = "latitude") @NotNull Double latitude,
                                                      @RequestParam(value = "longitude") @NotNull Double longitude) {
        String geoHash = GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, 12);
        String s = Base64.getEncoder().encodeToString(geoHash.getBytes());
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @GetMapping("/geohashv3")
    public ResponseEntity<String> getLocationSpecialFour(@RequestParam(value = "latitude") @NotNull Double latitude,
                                                        @RequestParam(value = "longitude") @NotNull Double longitude) {
        String geoHash = GeoHash.geoHashStringWithCharacterPrecision(latitude, longitude, 12);
        return new ResponseEntity<>(geoHash,HttpStatus.OK);
    }
}
