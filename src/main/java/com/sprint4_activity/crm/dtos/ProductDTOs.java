package com.sprint4_activity.crm.dtos;


import com.sprint4_activity.crm.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTOs {

    private long id;
    private String name;
    private long quantity;
    private float price;

}
