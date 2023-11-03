package com.sprint4_activity.crm.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherData {

    @JsonProperty("owner")
    private String owner;
    @JsonProperty("country")
    private String country;
    @JsonProperty("data")
    private List<WeatherDetails> data;
    @JsonProperty("globalIdLocal")
    private int globalIdLocal;
    @JsonProperty("dataUpdate")
    private String dataUpdate;
}
