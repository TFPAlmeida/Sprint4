package com.sprint4_activity.crm.service;

import com.sprint4_activity.crm.entity.Category;
import com.sprint4_activity.crm.entity.Client;
import com.sprint4_activity.crm.entity.Order;
import com.sprint4_activity.crm.entity.Product;
import com.sprint4_activity.crm.exception.ClientNotFoundException;
import com.sprint4_activity.crm.exception.ProductNotFoundException;
import com.sprint4_activity.crm.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Service
class OrderServiceTest {
    @Mock
    public OrderRepository orderRepository;
    @Mock
    private ProductService productService;
    @Mock
    private ClientService clientService;
    @Mock
    private LocationService locationService;
    @Mock
    private Weatherservice weatherservice;
    @Autowired
    @InjectMocks
    private OrderService orderService;


    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void placeOrderCase1() throws ClientNotFoundException, ProductNotFoundException {
        Client client = new Client();
        client.setName("Tiago"); client.setLocal("Porto");
        when(clientService.getClientById(client.getId())).thenReturn(client);

        List<Product> products = new ArrayList<>();
        List<Long> quantity = new ArrayList<>();
        Category category1 = new Category(0,"technology");
        Product product1 = new Product(0,"TV",2L,5000F,"12345",category1);
        Product product2 = new Product(0,"SmartPhone", 4L,1000F,"12346",category1);

        when(productService.getProductByID(product1.getId())).thenReturn(product1);
        when(productService.getProductByID(product2.getId())).thenReturn(product2);

        products.add(product1);
        quantity.add(2L);
        products.add(product2);
        quantity.add(3L);

        float price = products.get(0).getPrice() * quantity.get(0) + products.get(1).getPrice() * quantity.get(1);

        Order order = new Order();
        order.setProducts(products);
        order.setProductQuantity(quantity);
        order.setPrice(price);
        order.setCreationDate(LocalDate.now());
        order.setClient(client);

        orderRepository.save(order);
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void placeOrderCase2() throws ClientNotFoundException, ProductNotFoundException {
        Client client = new Client();
        client.setName("Tiago"); client.setLocal("Porto");
        when(clientService.getClientById(client.getId())).thenReturn(client);

        List<Product> products = new ArrayList<>();
        List<Long> quantity = new ArrayList<>();
        Category category1 = new Category(0,"technology");
        Product product1 = new Product(0,"TV",2L,5000F,"12345",category1);
        Product product2 = new Product(0,"SmartPhone", 4L,1000F,"12346",category1);

        when(productService.getProductByID(product1.getId())).thenReturn(product1);
        when(productService.getProductByID(product2.getId())).thenReturn(product2);

        products.add(product1);
        quantity.add(2L);
        products.add(product2);
        quantity.add(3L);

        float price = products.get(0).getPrice() * quantity.get(0) + products.get(1).getPrice() * quantity.get(1);

        Order order = new Order();
        order.setProducts(products);
        order.setProductQuantity(quantity);
        order.setPrice(price);
        order.setCreationDate(LocalDate.now());
        order.setClient(client);

        verify(orderRepository, times(0)).save(any());

    }



    @Test
    void setDeliverDate() {
    }

    @Test
    void delivaryDataStatus() {
    }
}