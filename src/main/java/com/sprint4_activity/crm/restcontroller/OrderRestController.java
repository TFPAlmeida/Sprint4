package com.sprint4_activity.crm.restcontroller;

import java.util.List;

import com.sprint4_activity.crm.exception.ClientNotFoundException;
import com.sprint4_activity.crm.exception.DaysException;
import com.sprint4_activity.crm.exception.OrderNotFoundException;
import com.sprint4_activity.crm.exception.ProductNotFoundException;
import com.sprint4_activity.crm.request.OrderRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sprint4_activity.crm.entity.Order;
import com.sprint4_activity.crm.service.OrderService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderRestController {

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
    public ResponseEntity<Order> placeOrder(@RequestBody @Valid OrderRequest orderRequest) throws ClientNotFoundException, ProductNotFoundException, DaysException {
        Order order = service.placeOrder(orderRequest);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/getDelivaryDayStatus/{orderId}")
    public ResponseEntity<String> getDelivaryDayStatus(@PathVariable long orderId) throws OrderNotFoundException {
        return service.delivaryDataStatus(orderId);
    }
}
