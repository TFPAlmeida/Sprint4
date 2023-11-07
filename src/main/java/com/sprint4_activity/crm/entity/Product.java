package com.sprint4_activity.crm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Products_TBL")
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private long quantity;
	private float price;
	private String barcode;
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
}
