package com.sprint4_activity.crm.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {

	@NotNull
	@NotEmpty(message = "Client name shouldn't be null!")
	private String name;
	
}
