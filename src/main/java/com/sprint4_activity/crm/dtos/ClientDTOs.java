package com.sprint4_activity.crm.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sprint4_activity.crm.entity.Client;
import com.sprint4_activity.crm.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class ClientDTOs {

    private long id;
    private String name;
    private List<Order> orders = new ArrayList<>();

}
