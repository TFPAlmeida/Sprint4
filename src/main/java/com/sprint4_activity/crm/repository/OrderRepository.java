package com.sprint4_activity.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sprint4_activity.crm.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

    Order findOrderById(long id);
}
