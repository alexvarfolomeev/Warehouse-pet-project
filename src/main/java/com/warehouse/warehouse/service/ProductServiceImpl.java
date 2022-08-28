package com.warehouse.warehouse.service;

import com.warehouse.warehouse.repository.entity.Product;
import com.warehouse.warehouse.repository.ProductRepository;
import com.warehouse.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

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
        oldProd.setWarehouse(product.getWarehouse());
        productRepository.deleteById(oldProd.getId());
        return productRepository.save(oldProd);
    }

    public void moveProductFromBetweenWarehouses(Long productId, Long firstWarehouseId, Long secondWarehouseId) {
        var product = productRepository.findProductByIdAndWarehouse(productId, firstWarehouseId);
        var warehouse = warehouseRepository.findById(secondWarehouseId);
        product.setWarehouse(warehouse.get());
        updateProduct(product);
    }
}
