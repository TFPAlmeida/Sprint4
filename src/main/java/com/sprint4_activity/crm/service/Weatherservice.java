package com.sprint4_activity.crm.service;

import com.sprint4_activity.crm.weather.WeatherData;
import com.sprint4_activity.crm.weather.WeatherDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@Service
public class Weatherservice {

    public List<Object> getweather(@PathVariable double latitude, @PathVariable double longitude) {
        String apiKey = "9617l9zTTbSQQEeivtBvKPqh8Ovl4U5d";
        String baseUrl = "https://api.tomorrow.io/v4/weather/forecast";
        String location = latitude + "," + longitude;
        String fullUrl = baseUrl + "?location=" + location + "&apikey=" + apiKey;

        RestTemplate template = new RestTemplate();
        Object[] objects = new Object[]{template.getForObject(fullUrl, Object.class)};
        return Arrays.asList(objects);
    }

    public WeatherData getweatherData(@PathVariable int globalIdLocal) {
        String baseUrl = "https://api.ipma.pt/open-data/forecast/meteorology/cities/daily/";
        String fullUrl = baseUrl + globalIdLocal + ".json";

        RestTemplate template = new RestTemplate();
        WeatherData data = template.getForObject(fullUrl, WeatherData.class);
        return data;
    }

    public List<WeatherDetails> getWeatherDetails(@PathVariable int globalIdLocal) {
        WeatherData data = getweatherData(globalIdLocal);

        if (data != null && !data.getData().isEmpty()) {
            List<WeatherDetails> details = data.getData();
            return details;
        }
        return null;
    }
}
