package com.sprint4_activity.crm.repository;

import com.sprint4_activity.crm.entity.Category;
import com.sprint4_activity.crm.entity.Client;
import com.sprint4_activity.crm.entity.Order;
import com.sprint4_activity.crm.entity.Product;
import com.sprint4_activity.crm.request.ClientRequest;
import com.sprint4_activity.crm.request.OrderRequest;
import com.sprint4_activity.crm.request.ProductRequest;
import jakarta.persistence.EntityManager;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CatergoryRepository catergoryRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Order successfully from DB")
    void findOrdersByDataRangeCase1() {

        criarOrder();

        LocalDate data1 = LocalDate.of(2023, 10, 8);
        LocalDate data2 = LocalDate.of(2023,11,30);

        List<Order> result = orderRepository.findOrdersByDataRange(data1,data2);

        assertThat(result.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Should not get Order from DB when Order not exists")
    void findOrdersByDataRangeCase2() {

        LocalDate data1 = LocalDate.of(2023, 10, 8);
        LocalDate data2 = LocalDate.of(2023,11,30);

        List<Order> result = orderRepository.findOrdersByDataRange(data1,data2);

        assertThat(result.isEmpty()).isTrue();
    }

    public void criarOrder() {
        ClientRequest clientRequest = new ClientRequest("Tiago", "Porto");
        Client client = createClient(clientRequest);
        List<Product> products = new ArrayList<>();

        ProductRequest productRequest = new ProductRequest("TV", 2L, 5F, "12345", "technology");
        products.add(createProduct(productRequest));

        List<Long> quantity = new ArrayList<>();
        quantity.add(productRequest.getQuantity());

        float price = productRequest.getPrice() * productRequest.getQuantity();
        Order order = new Order();
        order.setProducts(products);
        order.setProductQuantity(quantity);
        order.setClient(client);
        order.setCreationDate(LocalDate.now());
        order.setPrice(price);
        entityManager.persist(order);
    }

    public Client createClient(ClientRequest request){
        Client client = new Client();
        client.setName(request.getName());
        client.setLocal(request.getLocal());
        entityManager.persist(client);
        return client;
    }

    public Product createProduct(ProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setQuantity(request.getQuantity());
        product.setPrice(request.getPrice());
        product.setBarcode(request.getBarcode());
        Category category = new Category();
        category.setName(request.getCategory());
        entityManager.persist(category);
        product.setCategory(category);
        entityManager.persist(product);
        return product;
    }
}