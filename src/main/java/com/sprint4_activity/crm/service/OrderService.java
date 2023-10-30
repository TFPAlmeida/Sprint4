package com.sprint4_activity.crm.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sprint4_activity.crm.entity.Client;
import com.sprint4_activity.crm.entity.Product;
import com.sprint4_activity.crm.exception.ClientNotFoundException;
import com.sprint4_activity.crm.exception.OrderNotFoundException;
import com.sprint4_activity.crm.exception.ProductNotFoundException;
import com.sprint4_activity.crm.request.OrderRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.sprint4_activity.crm.entity.Order;
import com.sprint4_activity.crm.repository.OrderRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OrderService {


    public OrderRepository repository;

    private ProductService pService;

    private ClientService cService;

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order getOrderById(long id) throws OrderNotFoundException {

        if (repository.existsById(id)) {
            return repository.findOrderById(id);
        } else {
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
    }

    @Transactional
    public Order placeOrder(OrderRequest request) throws ClientNotFoundException, ProductNotFoundException {
        Client client = cService.getClientById(request.getClientID());
        if (client == null) {
            throw new ClientNotFoundException("Cliente não encontrado: " + request.getClientID());
        }
        List<Product> products = new ArrayList<>();
        float price = 0;
        for (int i = 0; i < request.getProductID().size(); i++) {
            Product product = pService.getProductByID(request.getProductID().get(i));
            if (product == null) {
                throw new ProductNotFoundException("Produto não encontrado: " + request.getProductID().get(i));
            }
            products.add(product);
            price = price + request.getProductQuantity().get(i) * product.getPrice();
        }
        Order order = new Order();
        order.setProducts(products);
        order.setProductQuantity(request.getProductQuantity());
        order.setClient(client);
        order.setCreationDate(LocalDate.now());
        order.setPrice(price);
        client.getOrders().add(order);
        return repository.save(order);

    }
}
