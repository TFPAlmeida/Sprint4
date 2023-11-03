package com.sprint4_activity.crm.service;

import com.sprint4_activity.crm.auxiliary.ApiBoundingBox;
import com.sprint4_activity.crm.auxiliary.ApiResponse;
import com.sprint4_activity.crm.auxiliary.ApiResult;
import com.sprint4_activity.crm.location.LocationData;
import com.sprint4_activity.crm.location.LocationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Service
public class LocationService {

    public ApiResponse getCoordinatesData(String city, String country) throws UnsupportedEncodingException, UnsupportedEncodingException {
        String apiKey = "fd0a59ce5ba6472d8c161249be5cb876";
        String baseUrl = "https://api.geoapify.com/v1/geocode/search";
        String format = "json";
        String encodedText = URLEncoder.encode(city + ", " + country, "UTF-8");
        String fullUrl = baseUrl + "?text=" + encodedText + "&format=" + format + "&apiKey=" + apiKey;

        RestTemplate template = new RestTemplate();
        ApiResponse response = template.getForObject(fullUrl, ApiResponse.class);

        return response;
    }

    public ApiBoundingBox getCoordinatesDetails(@PathVariable String city, @PathVariable String country) throws UnsupportedEncodingException {
        ApiResponse response = getCoordinatesData(city, country);
        if (response != null && !response.getResults().isEmpty()) {
            ApiResult result = response.getResults().get(0); // Supondo que você deseje acessar o primeiro resultado
            ApiBoundingBox bbox = result.getBbox();
            if (bbox != null) {
                System.out.println("Longitude: " + bbox.getLon1());
                System.out.println("Latitude: " + bbox.getLat1());
                return bbox;
            } else {
                System.out.println("Coordenadas de localização não estão disponíveis para esta resposta.");
            }
        } else {
            System.out.println("Nenhum resultado encontrado para a cidade e país fornecidos.");
        }

        return null;
    }

    public LocationData getLocationData() {
        String fullUrl = "https://api.ipma.pt/open-data/distrits-islands.json";

        RestTemplate template = new RestTemplate();
        LocationData data = template.getForObject(fullUrl, LocationData.class);

        return data;
    }

    public List<LocationDetails> getLocationDetails() {
        LocationData data = getLocationData();
        if (data != null && !data.getData().isEmpty()) {
            List<LocationDetails> details = data.getData();
            return details;
        }
        return null;
    }
}
