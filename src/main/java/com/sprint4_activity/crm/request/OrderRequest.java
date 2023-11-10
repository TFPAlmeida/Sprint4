package com.sprint4_activity.crm.request;

import java.util.List;

import com.sprint4_activity.crm.Info;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

	@NotNull
	private List<Info> productInfo;
	
	@NotNull
	private long clientID;

}
