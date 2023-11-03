package com.sprint4_activity.crm.controller;


import com.sprint4_activity.crm.weather.WeatherData;
import com.sprint4_activity.crm.weather.WeatherDetails;
import com.sprint4_activity.crm.service.Weatherservice;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/weather")
public class WeatherController {

    private Weatherservice service;

    @GetMapping("/tempo/{latitude}/{longitude}")
    public ResponseEntity<List<Object>> getweather(@PathVariable double latitude, @PathVariable double longitude) {
        return ResponseEntity.ok(service.getweather(latitude,longitude));
    }

    @GetMapping("/getWeatherData/{globalIdLocal}")
    public ResponseEntity<WeatherData> getweatherData(@PathVariable int globalIdLocal) {
        return ResponseEntity.ok(service.getweatherData(globalIdLocal));
    }

    @GetMapping("/getWeatherDetails/{globalIdLocal}")
    public ResponseEntity<List<WeatherDetails>> detweatherdetails(@PathVariable int globalIdLocal) {
        return ResponseEntity.ok(service.getWeatherDetails(globalIdLocal));
    }


}
