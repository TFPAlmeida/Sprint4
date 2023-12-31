package com.sprint4_activity.crm.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Clients_TBL")
@Entity
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	@OneToMany
	@JsonIgnore
	private List<Order> orders = new ArrayList<>();

	public void addOrder(Order order) {
        this.getOrders().add(order);
    }

	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + "]";
	}
	
}
