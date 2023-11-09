package com.sprint4_activity.crm.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sprint4_activity.crm.entity.Client;
import com.sprint4_activity.crm.entity.Order;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class ClientDTOs {

    private long id;
    private String name;
    //@JsonIgnore
    private List<Order> orders = new ArrayList<>();

    public ClientDTOs(Client client) {
        this.setId(client.getId());
        this.setName(client.getName());
        this.setOrders(client.getOrders());
    }
}
