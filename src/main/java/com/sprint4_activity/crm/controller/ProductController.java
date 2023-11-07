package com.sprint4_activity.crm.controller;


import com.sprint4_activity.crm.entity.Category;
import com.sprint4_activity.crm.entity.Product;
import com.sprint4_activity.crm.repository.CatergoryRepository;
import com.sprint4_activity.crm.repository.ProductRepository;
import com.sprint4_activity.crm.request.ProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.awt.desktop.PreferencesEvent;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Controller
public class ProductController {

    private ProductRepository repository;
    private CatergoryRepository catergoryRepository;

    @GetMapping("/CreateProduct")
    public String createProductView() {
        return "CreateProduct";
    }

    @PostMapping("/CreateProduct")
    public String createProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setQuantity(request.getQuantity());
        product.setPrice(request.getPrice());
        product.setBarcode(request.getBarcode());



        Category cat;

        if (catergoryRepository.findCatByName(request.getCategory()) == null){
            cat = new Category();
            cat.setName(request.getCategory());
            catergoryRepository.save(cat);
        }else{
            cat = catergoryRepository.findCatByName(request.getCategory());
        }

        product.setCategory(cat);
        repository.save(product);
        return "redirect:/ProductList";
    }

    @GetMapping("/ProductList")
    public ModelAndView getProducts() {
        ModelAndView mv = new ModelAndView("ProductList");
        mv.addObject("products", repository.findAll());
        return mv;
    }

    @GetMapping("/editProduct/{id}")
    public ModelAndView editProducts(@PathVariable("id") long id){
        ModelAndView mv = new ModelAndView("UpdateProduct");
        System.out.println(id);
        Product product = repository.findById(id).orElse(null);
        mv.addObject("product", product);
        return mv;
    }

    @GetMapping("/removeProduct/{id}")
    public ModelAndView removeProducts(@PathVariable("id") long id){
        ModelAndView mv = new ModelAndView("ProductList");
        repository.deleteById(id);
        mv.addObject("products", repository.findAll());
        return mv;
    }

    @PostMapping("/SaveProduct")
    public String updateProduct(Product product) {
        if(repository.existsById(product.getId())){
            Product productfound = repository.findById(product.getId()).orElse(null);
            productfound.setName(product.getName());
            productfound.setQuantity(product.getQuantity());
            productfound.setPrice(product.getPrice());
            productfound.setBarcode(product.getBarcode());
            productfound.setCategory(product.getCategory());
            repository.save(product);
            return "redirect:/ProductList";
        }
        return "redirect:/ProductList";
    }

    @GetMapping("/products/barcode")
    public ModelAndView getProductsByBarcode(@RequestParam("barcode") String barcode){
        List<Product> products = new ArrayList<>();
        ModelAndView mv = new ModelAndView("ProductList");
        Product product = repository.findProductByBarCode(barcode);
        products.add(product);
        mv.addObject("products", products);
        return mv;
    }

    @GetMapping("/products/category")
    public ModelAndView getProductsByCategory(@RequestParam("category") String category){
        System.out.println(category);
        ModelAndView mv = new ModelAndView("ProductList");
        List<Product> products = repository.findProductsByCategory(category);
        mv.addObject("products", products);
        return mv;
    }
}
