package com.sprint4_activity.crm.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sprint4_activity.crm.Info;
import com.sprint4_activity.crm.dtos.ClientDTOs;
import com.sprint4_activity.crm.dtos.OrderDTOs;
import com.sprint4_activity.crm.entity.Client;
import com.sprint4_activity.crm.entity.Product;
import com.sprint4_activity.crm.exception.ClientNotFoundException;
import com.sprint4_activity.crm.exception.OrderNotFoundException;
import com.sprint4_activity.crm.exception.ProductNotFoundException;
import com.sprint4_activity.crm.repository.ClientRepository;
import com.sprint4_activity.crm.request.OrderRequest;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.sprint4_activity.crm.entity.Order;
import com.sprint4_activity.crm.repository.OrderRepository;

import lombok.AllArgsConstructor;

import javax.transaction.Transactional;

@AllArgsConstructor
@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    public OrderRepository orderRepository;

    public ClientRepository clientRepository;

    private ProductService productService;

    private ClientService clientService;

    public List<OrderDTOs> getAllOrders() throws OrderNotFoundException {
        ModelMapper modelMapper = new ModelMapper();

        if (orderRepository.findAll().isEmpty()){
            throw new OrderNotFoundException("Não foram encontrados encomendas na DB.");
        }

        try {
            return orderRepository.findAll().stream()
                    .map(order -> modelMapper.map(order, OrderDTOs.class))
                    .collect(Collectors.toList());
        }catch (DataAccessException e){
            log.error("Erro a obter emcomendas.", e);
            throw new OrderNotFoundException("Erro a obter ecomendas. Detalhes: " + e.getMessage());
        }

    }

    public OrderDTOs getOrderById(long id) throws OrderNotFoundException {
        ModelMapper modelMapper = new ModelMapper();

        if (!orderRepository.existsById(id)){
            throw new OrderNotFoundException("Não existe encomenda com o id: " + id + " na DB");
        }

        try {
            return modelMapper.map(orderRepository.findOrderById(id), OrderDTOs.class);
        }catch (DataAccessException e){
            log.error("Erro a obter a encomenda.", e);
            throw new OrderNotFoundException("Erro a obter a encomenda com id: " + id + ". Detalhes: " + e.getMessage());
        }
    }

    @Transactional
    public OrderDTOs placeOrder(OrderRequest request) throws ClientNotFoundException, ProductNotFoundException, OrderNotFoundException {
        ModelMapper modelMapper = new ModelMapper();
        Client client = modelMapper.map(clientService.getClientById(request.getClientID()), Client.class);
        if (client == null) {
            throw new ClientNotFoundException("Erro a obter o cliente com id: " + request.getClientID());
        }

        try {
            List<Product> products = new ArrayList<>();
            float price = 0;
            for (Info info : request.getProductInfo()) {
                Product product = modelMapper.map(productService.getProductByID(info.getProductID()), Product.class);
                if (product == null) {
                    throw new ProductNotFoundException("Produto não encontrado: " + info.getProductID());
                }
                products.add(product);
                price = price + info.getProductQuantity() * product.getPrice();
            }

            Order order = new Order();
            order.setProducts(products);
            order.setInfo(request.getProductInfo());
            order.setClient(client);
            order.setCreationDate(LocalDate.now());
            order.setPrice(price);
            client.getOrders().add(order);
            orderRepository.save(order);
            clientRepository.save(client);
            return modelMapper.map(order,OrderDTOs.class);

        }catch (DataAccessException e){
            log.error("Erro a criar a encomenda.", e);
            throw new OrderNotFoundException("Erro a criar encomenda. Detalhes: " + e.getMessage());
        }

    }
}
