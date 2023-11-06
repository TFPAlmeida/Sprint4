package com.sprint4_activity.crm.restcontroller;

import com.sprint4_activity.crm.location.LocationData;
import com.sprint4_activity.crm.location.LocationDetails;
import com.sprint4_activity.crm.auxiliary.ApiResponse;
import com.sprint4_activity.crm.auxiliary.ApiBoundingBox;
import com.sprint4_activity.crm.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/coordenates")
public class LocationRestController {

    private LocationService service;

    @GetMapping("/getCoordenates/{city}/{country}")
    public ResponseEntity<Object> getCoordenates(@PathVariable String city, @PathVariable String country) throws UnsupportedEncodingException {
        return ResponseEntity.ok(service.getCoordinatesData(city, country));
    }

    @GetMapping("/getCoordinatesData/{city}/{country}")
    public ResponseEntity<ApiResponse> getCoordinatesData(@PathVariable String city, @PathVariable String country) throws UnsupportedEncodingException, UnsupportedEncodingException {
        return ResponseEntity.ok(service.getCoordinatesData(city, country));
    }

    @GetMapping("/getCoordenateDetails/{city}/{country}")
    public ResponseEntity<ApiBoundingBox> getCoordenateDetails(@PathVariable String city, @PathVariable String country) throws UnsupportedEncodingException {
        return ResponseEntity.ok(service.getCoordinatesDetails(city, country));
    }

    @GetMapping("/getLocationData")
    public ResponseEntity<LocationData> getLocationData() {
        return ResponseEntity.ok(service.getLocationData());
    }

    @GetMapping("/getLocationDetails")
    public ResponseEntity<List<LocationDetails>> getLocationDetails() {
        return ResponseEntity.ok(service.getLocationDetails());
    }
}
