package com.sprint4_activity.crm.repository;

import com.sprint4_activity.crm.entity.Category;
import com.sprint4_activity.crm.entity.Product;
import com.sprint4_activity.crm.request.ProductRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;


@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CatergoryRepository catergoryRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should get Product successfully from DB")
    void findProductByBarCodeCase1() {
        String barcode = "12345";
        ProductRequest request = new ProductRequest("TV", 2L, 5F, barcode, "technology");
        createProduct(request);

        Product result = productRepository.findProductByBarCode(barcode);

        assertThat(result == null).isFalse();
    }

    @Test
    @DisplayName("Should not get Product from DB when Product not exists")
    void findProductByBarCodeCase2() {
        String barcode = "12345";
        Product result = productRepository.findProductByBarCode(barcode);

        assertThat(result == null).isTrue();
    }

    @Test
    @DisplayName("Should get Product successfully from DB")
    void findProductsByCategoryCase1() {
        String barcode = "12345";
        String category = "technology";
        ProductRequest request = new ProductRequest("TV", 2L, 5F, barcode, category);
        createProduct(request);

        List<Product> result = productRepository.findProductsByCategory(category);

        assertThat(result.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Should not get Product from DB when Product not exists")
    void findProductsByCategoryCase2() {
        String category = "technology";
        List<Product> result = productRepository.findProductsByCategory(category);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void findProductsInOrdersByCategory() {
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