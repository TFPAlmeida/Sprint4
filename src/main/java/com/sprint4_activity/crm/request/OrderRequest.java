package com.sprint4_activity.crm.request;

import java.util.List;

import com.sprint4_activity.crm.Info;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

	@NotNull
	private List<Info> productInfo;
	
	@NotNull
	private long clientID;

}
