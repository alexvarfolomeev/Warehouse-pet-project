package com.warehouse.warehouse.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.warehouse.warehouse.model.Product;
import com.warehouse.warehouse.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl {

    @Autowired
    ProductRepository productRepository;

    public Product findById(Long id) {
        Optional product = productRepository.findById(id);
        return (Product) product.get();
    }

    public Product findProductByArticle(Integer article) {
        return productRepository.findProductByArticle(article);
    }

    public Product saveProduct(Product product) {
        productRepository.save(product);
        return product;
    }

    public void saveListOfProducts(List<Product>productList) {
        for (Product product : productList) {
            if (checkIfProductExists(product.getArticle())) {
                updateProduct(product);
            } else {
                saveProduct(product);
            }
        }
    }

    public List<Product> findAll() {
        List<Product> productsList = (List<Product>) productRepository.findAll();
        return productsList;
    }

    private Boolean checkIfProductExists(Integer article) {
        Product product = productRepository.findProductByArticle(article);
        return product != null;
    }

    private Product updateProduct(Product product) {
        Product oldProd = findProductByArticle(product.getArticle());
        oldProd.setQuantity(oldProd.getQuantity() + product.getQuantity());
        oldProd.setLastPurchasePrice(product.getLastPurchasePrice());
        productRepository.deleteById(oldProd.getId());
        return productRepository.save(oldProd);
    }
}
