package com.sprint4_activity.crm.repository;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sprint4_activity.crm.entity.Order;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

    Order findOrderById(long id);

    @Query("FROM Order Where creationDate>=:data1 and creationDate<=:data2")
    public List<Order> findOrdersByDataRange(LocalDate data1, LocalDate data2);

}
