package com.sprint4_activity.crm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Info {

    @Column(name = "product_id")
    private long productID;
    @Column(name = "product_quantity")
    private long productQuantity;
}
