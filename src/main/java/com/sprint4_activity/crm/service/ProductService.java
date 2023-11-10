package com.sprint4_activity.crm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sprint4_activity.crm.dtos.ProductDTOs;
import com.sprint4_activity.crm.exception.OrderNotFoundException;
import com.sprint4_activity.crm.exception.ProductNotFoundException;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private ProductRepository repository;

    public ProductDTOs saveProduct(ProductRequest request) throws ProductNotFoundException {
        ModelMapper modelMapper = new ModelMapper();
        Product product = modelMapper.map(request, Product.class);

        if (product == null){
            throw new ProductNotFoundException("Produto fornecido é nulo. Não é possível salvar.");
        }

        try {
            return modelMapper.map(repository.save(product), ProductDTOs.class);
        }catch (DataAccessException e){
            log.error("Erro ao salvar o produto.", e);
            throw new ProductNotFoundException("Erro ao salvar o produto. Detalhes: " + e.getMessage());
        }

    }

    public List<ProductDTOs> saveProducts(List<ProductRequest> requests) throws ProductNotFoundException {
        ModelMapper modelMapper = new ModelMapper();

        List<Product> products = requests.stream()
                .map(product -> modelMapper.map(product, Product.class))
                .collect(Collectors.toList());
        if (products.isEmpty()){
            throw new ProductNotFoundException("Produtos fornecidos são nulos. Não é possível salva-los.");
        }

        try {
            repository.saveAll(products);
            return products.stream()
                    .map(product -> modelMapper.map(product, ProductDTOs.class))
                    .collect(Collectors.toList());
        }catch (DataAccessException e){
            log.error("Erro ao salvar os produtos.", e);
            throw new ProductNotFoundException("Erro ao salvar os produtos. Detalhes: " + e.getMessage());
        }

    }

    public List<ProductDTOs> getAllProducts() throws ProductNotFoundException {
        ModelMapper modelMapper = new ModelMapper();

        if(repository.findAll().isEmpty()){
            throw new ProductNotFoundException("Não foram encontrados produtos na DB.");
        }

        try {
            return repository.findAll().stream()
                    .map(product -> modelMapper.map(product, ProductDTOs.class))
                    .collect(Collectors.toList());
        }catch (DataAccessException e){
            log.error("Erro ao obter os produtos.", e);
            throw new ProductNotFoundException("Erro ao obter os produtos. Detalhes: " + e.getMessage());
        }

    }

    public ProductDTOs getProductByID(long id) throws ProductNotFoundException {
        ModelMapper modelMapper = new ModelMapper();
        if(!repository.existsById(id)){
            throw new ProductNotFoundException("Não existe produto com o id: " + id + " na DB");
        }
        try {
            return modelMapper.map(repository.findProductById(id), ProductDTOs.class);
        }catch (DataAccessException e){
            log.error("Erro ao obter o produto.", e);
            throw new ProductNotFoundException("Erro ao obter o produto " + id + ". Detalhes: " + e.getMessage());
        }

    }

    public ProductDTOs getProductByName(String name) throws ProductNotFoundException {
        ModelMapper modelMapper = new ModelMapper();

        if (!repository.existsByName(name)){
            throw new ProductNotFoundException("Não existe produto com o nome: " + name + " na DB");
        }
        try {
            return modelMapper.map(repository.findProductByName(name), ProductDTOs.class);
        }catch (DataAccessException e){
            log.error("Erro ao obter o produto.", e);
            throw new ProductNotFoundException("Erro ao obter o produto " + name + ". Detalhes: " + e.getMessage());
        }
    }

    public ProductDTOs updateProduct(Product product) throws ProductNotFoundException {
        ModelMapper modelMapper = new ModelMapper();

        if(!repository.existsById(product.getId())){
            throw new ProductNotFoundException("Erro a atualizar o produto com o id:  " + product.getId());
        }

        try {
            return modelMapper.map(repository.save(product), ProductDTOs.class);
        }catch (DataAccessException e){
            log.error("Erro a atualizar o produto.", e);
            throw new ProductNotFoundException("Erro a atualizar o produto com o id:  " + product.getId() + ". Detalhes: " + e.getMessage());
        }

    }

    public ResponseEntity<String> deleteProduct(long id) throws ProductNotFoundException {
        if (!repository.existsById(id)) {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
        repository.deleteById(id);
        return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    }
}
