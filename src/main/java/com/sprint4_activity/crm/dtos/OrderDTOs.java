package com.sprint4_activity.crm.dtos;

import com.sprint4_activity.crm.entity.Client;
import com.sprint4_activity.crm.entity.Order;
import com.sprint4_activity.crm.entity.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderDTOs {

    private long id;
    private List<Product> products;
    private List<Long> productQuantity;
    private Client client;
    private LocalDate creationDate;
    private LocalDate deliverDate;
    private float price;

    public OrderDTOs(Order order) {
        this.setId(order.getId());
        this.setProducts(order.getProducts());
        this.setProductQuantity(order.getProductQuantity());
        this.setClient(order.getClient());
        this.setCreationDate(order.getCreationDate());
        this.setDeliverDate(order.getDeliverDate());
        this.setPrice(order.getPrice());
    }
}
