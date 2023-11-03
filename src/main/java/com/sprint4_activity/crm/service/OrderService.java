package com.sprint4_activity.crm.service;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sprint4_activity.crm.entity.Client;
import com.sprint4_activity.crm.entity.Product;
import com.sprint4_activity.crm.exception.ClientNotFoundException;
import com.sprint4_activity.crm.exception.DaysException;
import com.sprint4_activity.crm.exception.OrderNotFoundException;
import com.sprint4_activity.crm.exception.ProductNotFoundException;
import com.sprint4_activity.crm.location.LocationDetails;
import com.sprint4_activity.crm.request.OrderRequest;
import com.sprint4_activity.crm.weather.WeatherDetails;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private LocationService lService;

    private Weatherservice wService;

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
    public Order placeOrder(OrderRequest request) throws ClientNotFoundException, ProductNotFoundException, DaysException {
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
            if(product.getQuantity() >= request.getProductQuantity().get(i)){
                products.add(product);
                price = price + request.getProductQuantity().get(i) * product.getPrice();
            }else{
                throw new ProductNotFoundException("Product id: " + product.getId() + " Quantity of available product: " + product.getQuantity() + " Quantaty of product requested: " + request.getProductQuantity().get(i));
            }

        }
        Order order = new Order();
        order.setProducts(products);
        order.setProductQuantity(request.getProductQuantity());
        order.setClient(client);
        order.setCreationDate(LocalDate.now());
        order.setPrice(price);
        setDeliverDate(client.getLocal(), order);
        client.getOrders().add(order);
        return repository.save(order);
    }

    public void setDeliverDate(String local, Order order) throws DaysException {
        System.out.println(local);
        LocalDate cData = LocalDate.now();
        List<LocationDetails> locationDetails = lService.getLocationDetails();
        int globalIdLocal = 0;

        for (LocationDetails details : locationDetails) {
            if (details.getLocal().equals(local)){
                globalIdLocal = details.getGlobalIdLocal();
            }
        }
        //System.out.println(globalIdLocal);
        List<WeatherDetails> weatherDetails = wService.getWeatherDetails(globalIdLocal);

        int day;
        //System.out.println(weatherDetails.get(0).getPrecipitaProb());
        for (day = 0; day < 4; day++) {
            float precipitaProb = Float.parseFloat(weatherDetails.get(day).getPrecipitaProb());
            if(precipitaProb >= 50){
                order.setDeliverDate(cData.plusDays(day));
            }else{
                break;
            }
        }

        /*if(day == 0){
            order.setDeliverDate(cData.plusDays(1));
            throw new DaysException("Order vai ser entrega dentro de previsto: " + cData.plusDays(1));
        }else{
            throw new DaysException("Devido ao tempo climático vai haver um atraso de  " + day + " dias. Data prvista: " + cData.plusDays(day-1));
        }*/
    }

    public ResponseEntity<String> delivaryDataStatus(long orderId) throws OrderNotFoundException {
        Order order = getOrderById(orderId);
        long days = ChronoUnit.DAYS.between(order.getCreationDate(), order.getDeliverDate());
        if(order.getCreationDate().plusDays(1) == order.getDeliverDate()){
            return new ResponseEntity<>("Order vai ser entrega dentro de previsto: " + order.getCreationDate().plusDays(1), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Devido ao tempo climático vai haver um atraso de  " + days + " dias. Data prvista: " + order.getDeliverDate(), HttpStatus.OK);
        }
    }
}
