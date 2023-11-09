package com.sprint4_activity.crm.entity;

import com.sprint4_activity.crm.dtos.ProductDTOs;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

	public Product(ProductDTOs productDTOs) {
		this.setId(productDTOs.getId());
		this.setName(productDTOs.getName());
		this.setQuantity(productDTOs.getQuantity());
		this.setPrice(productDTOs.getPrice());
	}
}
