package com.sprint4_activity.crm.Weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherDetails {

    @JsonProperty("precipitaProb")
    private String precipitaProb;
    @JsonProperty("tMin")
    private String tMin;
    @JsonProperty("tMax")
    private String tMax;
    @JsonProperty("preWinddir")
    private String preWindDir;
    @JsonProperty("idWeatherType")
    private int idWeatherType;
    @JsonProperty("classWindSpeed")
    private int classWindSpeed;
    @JsonProperty("longitude")
    private String longitude;
    @JsonProperty("forecastDate")
    private String forecastDate;
    @JsonProperty("classPrecInt")
    private int classPrecInt;
    @JsonProperty("latitude")
    private String latitude;

}
