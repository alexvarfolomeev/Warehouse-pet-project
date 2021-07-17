package com.warehouse.warehouse.controller;

import com.warehouse.warehouse.model.Product;
import com.warehouse.warehouse.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/product")
public class ProductController {


    @Autowired
    ProductServiceImpl productService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> findProduct(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            ResponseEntity.notFound().build();
        }
         return ResponseEntity.ok(product);
    }

    @GetMapping(value = "/show-all-products")
    public ResponseEntity<List<Product>> showProductsList() {
        List<Product>productsList = productService.findAll();
        if (productsList.size() == 0) {
            System.out.println("Ни одного товара не найдено");
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productsList);
    }

    @PostMapping(value = "/save-product")
    public Product saveProducts(@RequestBody Product product) {
        productService.saveProduct(product);
        if (product == null) {
            System.out.println("Ни одного товара не найдено");
        }
        return product;
    }

    @PostMapping(value = "/save-several-products")
    public List<Product> saveSeveralProducts(@RequestBody Product[]products) {
        for (Product product:products) {
            productService.saveProduct(product);
        }
        return Arrays.asList(products);
    }
}
