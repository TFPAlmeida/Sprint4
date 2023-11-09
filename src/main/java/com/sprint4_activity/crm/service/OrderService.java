package com.sprint4_activity.crm.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.sprint4_activity.crm.Info;
import com.sprint4_activity.crm.dtos.OrderDTOs;
import com.sprint4_activity.crm.entity.Client;
import com.sprint4_activity.crm.entity.Product;
import com.sprint4_activity.crm.exception.ClientNotFoundException;
import com.sprint4_activity.crm.exception.OrderNotFoundException;
import com.sprint4_activity.crm.exception.ProductNotFoundException;
import com.sprint4_activity.crm.repository.ClientRepository;
import com.sprint4_activity.crm.request.OrderRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.sprint4_activity.crm.entity.Order;
import com.sprint4_activity.crm.repository.OrderRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OrderService {


    public OrderRepository orderRepository;

    public ClientRepository clientRepository;

    private ProductService productService;

    private ClientService clientService;

    public List<OrderDTOs> getAllOrders() {
        List<OrderDTOs> orderDTOsList = new ArrayList<>();
        for (Order order:orderRepository.findAll()) {
            OrderDTOs orderDTOs = new OrderDTOs(order);
            orderDTOsList.add(orderDTOs);
        }
        return orderDTOsList;
    }

    public OrderDTOs getOrderById(long id) throws OrderNotFoundException {

        if (orderRepository.existsById(id)) {
            return new OrderDTOs(orderRepository.findOrderById(id));
        } else {
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
    }

    @Transactional
    public OrderDTOs placeOrder(OrderRequest request) throws ClientNotFoundException, ProductNotFoundException {
        Client client = new Client(clientService.getClientById(request.getClientID()));
        if (client == null) {
            throw new ClientNotFoundException("Cliente não encontrado: " + request.getClientID());
        }
        List<Product> products = new ArrayList<>();
        List<Long> quantity = new ArrayList<>();
        float price = 0;
        for (Info info : request.getProductInfo()) {
            Product product = new Product(productService.getProductByID(info.getProductID()));
            if (product == null) {
                throw new ProductNotFoundException("Produto não encontrado: " + info.getProductID());
            }
            products.add(product);
            quantity.add(info.getProductQuantity());
            price = price + info.getProductQuantity() * product.getPrice();
        }

        Order order = new Order();
        order.setProducts(products);
        order.setProductQuantity(quantity);
        order.setClient(client);
        order.setCreationDate(LocalDate.now());
        order.setPrice(price);
        client.getOrders().add(order);
        //;
        orderRepository.save(order);
        clientRepository.save(client);
        return new OrderDTOs(order);

    }
}
