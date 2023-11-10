package com.sprint4_activity.crm.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {

	@NotNull
	@NotEmpty(message = "Client name shouldn't be null!")
	private String name;
	
}
