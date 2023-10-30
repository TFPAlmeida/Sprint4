package com.sprint4_activity.crm.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

	@NotNull
	@NotEmpty(message = "Product Name shouldn't be null!")
	private String name;
	
	@Min(value = 0, message  = "numer of quantity not available!")
	private long quantity;
	
	@Min(value = 0, message  = "price shouldn't be less than zero!")
	private float price;
}
