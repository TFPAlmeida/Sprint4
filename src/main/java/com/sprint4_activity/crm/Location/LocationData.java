package com.sprint4_activity.crm.Location;

import lombok.Data;

import java.util.List;

@Data
public class LocationData {

    private String owner;
    private String country;
    private List<LocationDetails> data;
}
