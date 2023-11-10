package com.sprint4_activity.crm.controller;

import java.util.List;

import com.sprint4_activity.crm.dtos.OrderDTOs;
import com.sprint4_activity.crm.exception.ClientNotFoundException;
import com.sprint4_activity.crm.exception.OrderNotFoundException;
import com.sprint4_activity.crm.exception.ProductNotFoundException;
import com.sprint4_activity.crm.request.OrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sprint4_activity.crm.service.OrderService;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService service;

    @GetMapping("/getOrders")
    private ResponseEntity<List<OrderDTOs>> findAllOrders() throws OrderNotFoundException {
        return ResponseEntity.ok(service.getAllOrders());
    }

    @GetMapping("/getOrderById/{id}")
    private ResponseEntity<OrderDTOs> findOrderById(@PathVariable long id) throws OrderNotFoundException {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<OrderDTOs> placeOrder(@RequestBody @Valid OrderRequest orderRequest) throws ClientNotFoundException, ProductNotFoundException, OrderNotFoundException {
        OrderDTOs order = service.placeOrder(orderRequest);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
