package com.sprint4_activity.crm.restcontroller;

import java.util.List;

import com.sprint4_activity.crm.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sprint4_activity.crm.entity.Product;
import com.sprint4_activity.crm.request.ProductRequest;
import com.sprint4_activity.crm.service.ProductService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductRestController {

    private ProductService service;

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody @Valid ProductRequest productRequest) {
        return new ResponseEntity<>(service.saveProduct(productRequest), HttpStatus.CREATED);
    }

    @PostMapping("/addProducts")
    public ResponseEntity<List<Product>> addProducts(@RequestBody @Valid List<ProductRequest> productRequest) {
        return new ResponseEntity<>(service.saveProducts(productRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getProducts")
    public ResponseEntity<List<Product>> findAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) throws ProductNotFoundException {
        return ResponseEntity.ok(service.getProductByID(id));
    }


    @GetMapping("/getProductByName/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) throws ProductNotFoundException {
        return ResponseEntity.ok(service.getProductByName(name));
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) throws ProductNotFoundException {
        return ResponseEntity.ok(service.updateProduct(product));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) throws ProductNotFoundException {
        return service.deleteProduct(id);
    }

    @GetMapping("/findProductsInOrdersByCategory/{cat}")
    public ResponseEntity<List<Product>> findProductsInOrderByCategory(@PathVariable String cat){
        return  ResponseEntity.ok(service.findProductsInOrdersByCategory(cat));
    }

}
