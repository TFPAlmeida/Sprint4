package com.sprint4_activity.crm.controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/weather")
public class WeatherController {

    @GetMapping("/tempo/{latitude}/{longitude}")
    public List<Object> getweather(@PathVariable String latitude, @PathVariable String longitude){
        String apiKey = "9617l9zTTbSQQEeivtBvKPqh8Ovl4U5d";
        String baseUrl = "https://api.tomorrow.io/v4/weather/forecast";
        String location = latitude + "," + longitude;
        String fullUrl = baseUrl + "?location=" + location + "&apikey=" + apiKey;

        RestTemplate template = new RestTemplate();
        Object[] objects = new Object[]{template.getForObject(fullUrl, Object.class)};
        return Arrays.asList(objects);
    }
}
