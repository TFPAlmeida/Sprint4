package com.sprint4_activity.crm.dtos;

import com.sprint4_activity.crm.Info;
import com.sprint4_activity.crm.entity.Client;
import com.sprint4_activity.crm.entity.Order;
import com.sprint4_activity.crm.entity.Product;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTOs {

    private long id;
    private List<Product> products;
    private List<Info> info;
    private Client client;
    private LocalDate creationDate;
    private LocalDate deliverDate;
    private float price;


}
