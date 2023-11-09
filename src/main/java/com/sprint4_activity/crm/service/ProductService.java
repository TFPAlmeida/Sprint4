package com.sprint4_activity.crm.service;

import java.util.ArrayList;
import java.util.List;

import com.sprint4_activity.crm.dtos.ProductDTOs;
import com.sprint4_activity.crm.exception.OrderNotFoundException;
import com.sprint4_activity.crm.exception.ProductNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sprint4_activity.crm.entity.Product;
import com.sprint4_activity.crm.repository.ProductRepository;
import com.sprint4_activity.crm.request.ProductRequest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProductService {

    private ProductRepository repository;

    public ProductDTOs saveProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setQuantity(request.getQuantity());
        product.setPrice(request.getPrice());
        return new ProductDTOs(repository.save(product));
    }

    public List<ProductDTOs> saveProducts(List<ProductRequest> requests) {
        List<ProductDTOs> productDTOsList = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        for (ProductRequest request : requests) {
            Product product = new Product();
            product.setName(request.getName());
            product.setQuantity(request.getQuantity());
            product.setPrice(request.getPrice());
            products.add(product);
            ProductDTOs productDTOs = new ProductDTOs(product);
            productDTOsList.add(productDTOs);
        }
        repository.saveAll(products);
        return productDTOsList;
    }

    public List<ProductDTOs> getAllProducts() {
        List<ProductDTOs> productDTOsList = new ArrayList<>();
        for (Product product: repository.findAll()) {
            ProductDTOs productDTOs = new ProductDTOs(product);
            productDTOsList.add(productDTOs);
        }
        return productDTOsList;
    }

    public ProductDTOs getProductByID(long id) throws ProductNotFoundException {
        if (repository.existsById(id)) {
            return new ProductDTOs(repository.findProductById(id));
        } else {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
    }

    public ProductDTOs getProductByName(String name) throws ProductNotFoundException {
        if (repository.existsByName(name)) {
            return new ProductDTOs(repository.findProductByName(name));
        } else {
            throw new ProductNotFoundException("Product not found with id: " + name);
        }
    }

    public ProductDTOs updateProduct(Product product) throws ProductNotFoundException {

        if (!repository.existsById(product.getId())) {
            throw new ProductNotFoundException("Product not found: " + product.getId());
        }
        return new ProductDTOs(repository.save(product));
    }

    public ResponseEntity<String> deleteProduct(long id) throws ProductNotFoundException {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    }
}
