package com.sprint4_activity.crm.dtos;


import com.sprint4_activity.crm.entity.Product;
import lombok.Data;

@Data
public class ProductDTOs {

    private long id;
    private String name;
    private long quantity;
    private float price;

    public ProductDTOs(Product product) {
        this.setId(product.getId());
        this.setName(product.getName());
        this.setQuantity(product.getQuantity());
        this.setPrice(product.getPrice());
    }
}
