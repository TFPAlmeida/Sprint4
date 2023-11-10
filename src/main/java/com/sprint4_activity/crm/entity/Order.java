package com.sprint4_activity.crm.entity;

import com.sprint4_activity.crm.Info;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Orders_TBL")
@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToMany
    @JoinTable(
        name = "Order_Product",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
	private List<Product> products;
	@ElementCollection
	@CollectionTable(name = "order_product_quantities", joinColumns = @JoinColumn(name = "order_id"))
	@Column(name = "product_quantity")
	private List<Info> info;
	@ManyToOne
	private Client client;
	private LocalDate creationDate;
	private LocalDate deliverDate;
	private float price;
}
