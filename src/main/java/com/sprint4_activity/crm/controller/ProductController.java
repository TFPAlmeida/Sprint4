package com.sprint4_activity.crm.controller;

import java.util.List;

import com.sprint4_activity.crm.dtos.ProductDTOs;
import com.sprint4_activity.crm.exception.ProductNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
public class ProductController {

    private ProductService service;

    @PostMapping("/addProduct")
    public ResponseEntity<ProductDTOs> addProduct(@RequestBody @Valid ProductRequest productRequest) {
        return new ResponseEntity<>(service.saveProduct(productRequest), HttpStatus.CREATED);
    }

    @PostMapping("/addProducts")
    public ResponseEntity<List<ProductDTOs>> addProducts(@RequestBody @Valid List<ProductRequest> productRequest) {
        return new ResponseEntity<>(service.saveProducts(productRequest), HttpStatus.CREATED);
    }

    @GetMapping("/getProducts")
    public ResponseEntity<List<ProductDTOs>> findAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @GetMapping("/getProductById/{id}")
    public ResponseEntity<ProductDTOs> getProductById(@PathVariable long id) throws ProductNotFoundException {
        return ResponseEntity.ok(service.getProductByID(id));
    }


    @GetMapping("/getProductByName/{name}")
    public ResponseEntity<ProductDTOs> getProductByName(@PathVariable String name) throws ProductNotFoundException {
        return ResponseEntity.ok(service.getProductByName(name));
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDTOs> updateProduct(@RequestBody Product product) throws ProductNotFoundException {
        return ResponseEntity.ok(service.updateProduct(product));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) throws ProductNotFoundException {
        return service.deleteProduct(id);
    }

}
