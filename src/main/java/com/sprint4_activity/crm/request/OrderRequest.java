package com.sprint4_activity.crm.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

	@NotNull
	private List<Long> productID;
	
	@NotNull
	private long clientID;
	
	@NotNull
	private List<Long> productQuantity;
}
