package com.warehouse.warehouse.controller;

import com.warehouse.warehouse.model.MoveProducts;
import com.warehouse.warehouse.repository.entity.Product;
import com.warehouse.warehouse.service.ProductServiceImpl;
import com.warehouse.warehouse.utils.DocumentsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    DocumentsUtils documentsUtils;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping(value = "/")
    public String homePage() {
        return "/home";
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> findProduct(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping(value = "/show-all-products")
    public String showProductsList(Model model) {
        List<Product> productsList = productService.findAll();
        if (productsList.isEmpty()) {
            System.out.println("Ни одного товара не найдено");
            ResponseEntity.notFound().build();
        }
        model.addAttribute("goods", productsList);
        logger.info("Получен список всех товаров");
        return "/products";
    }

    @GetMapping(value = "/save-product")
    public String saveProductsForm(Model model) {
        model.addAttribute("product", new Product());
        return "/save_products";
    }

    @PostMapping(value = "/save-product")
    public String saveProductsSubmit(@ModelAttribute Product product) {
        if (product == null) {
            System.out.println("Ни одного товара не найдено");
        }
        productService.saveProduct(product);
        logger.info(String.format("Товар успешно добавлен, артикул - %s, наименование - %s", product.getArticle(), product.getProductName()));
        return "redirect:/api/product/show-all-products";
    }

    @PostMapping(value = "/save-several-products")
    public String saveSeveralProducts(@ModelAttribute Product product) throws IOException {
        List<Product> productsFromExcelFile = documentsUtils.getProductsFromExcelFile(product.getFile());
        productService.saveListOfProducts(productsFromExcelFile);
        return "redirect:/api/product/show-all-products";
    }

    @GetMapping(value = "/download-products-list")
    public String downloadProductsList() throws IOException {
        documentsUtils.downloadProductsListInExcel();
        return "redirect:/api/product/show-all-products";
    }

    @GetMapping(value = "/move-products")
    public String moveProduct(Model model) {
        var productList = productService.findAll();
        var move = new MoveProducts();
        model.addAttribute("productList", productList);
        model.addAttribute("move", move);
        return "/move_product";
    }

    @PostMapping(value = "/move-products")
    public String moveProduct(@ModelAttribute MoveProducts moveProducts) {
        productService.moveProductFromBetweenWarehouses(moveProducts.getProductId(), moveProducts.getFirstWarehouse(), moveProducts.getSecondWarehouse());
        return "redirect:/api/product/show-all-products";
    }
}
