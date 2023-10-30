package com.sprint4_activity.crm.controller;

import java.util.List;

import com.sprint4_activity.crm.exception.ClientNotFoundException;
import com.sprint4_activity.crm.exception.OrderNotFoundException;
import com.sprint4_activity.crm.exception.ProductNotFoundException;
import com.sprint4_activity.crm.request.OrderRequest;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sprint4_activity.crm.entity.Order;
import com.sprint4_activity.crm.service.OrderService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService service;

    @GetMapping("/getOrders")
    private ResponseEntity<List<Order>> findAllOrders() {
        return ResponseEntity.ok(service.getAllOrders());
    }

    @GetMapping("/getOrderById/{id}")
    private ResponseEntity<Order> findOrderById(@PathVariable long id) throws OrderNotFoundException {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestBody @Valid OrderRequest orderRequest) throws ClientNotFoundException, ProductNotFoundException{
        Order order = service.placeOrder(orderRequest);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
