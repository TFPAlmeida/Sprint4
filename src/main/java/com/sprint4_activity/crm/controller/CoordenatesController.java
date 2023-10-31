package com.sprint4_activity.crm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/coordenates")
public class CoordenatesController {

    @GetMapping("/{city}/{country}")
    public List<Object> getweather(@PathVariable String city, @PathVariable String country) throws UnsupportedEncodingException {
        String apiKey = "fd0a59ce5ba6472d8c161249be5cb876";
        String baseUrl = "https://api.geoapify.com/v1/geocode/search";
        String format = "json";
        String encodedText = URLEncoder.encode(city + ", " + country, "UTF-8");
        String fullUrl = baseUrl + "?text=" + encodedText + "&format=" + format + "&apiKey=" + apiKey;

        RestTemplate template = new RestTemplate();
        Object[] objects = new Object[]{template.getForObject(fullUrl, Object.class)};
        return Arrays.asList(objects);
    }
}
