package com.warehouse.warehouse.controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.warehouse.warehouse.model.Product;
import com.warehouse.warehouse.service.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductServiceImpl productService;

    Logger logger= LoggerFactory.getLogger(ProductController.class);

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
        List<Product>productsList = productService.findAll();
        if (productsList.isEmpty()) {
            System.out.println("Ни одного товара не найдено");
            ResponseEntity.notFound().build();
        }
        model.addAttribute("goods", productsList);
        logger.info("Получен список всех товаров");
        return "/index";
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
        logger.info(String.format("Товар успешно добавлен, артикул - %s", product.getArticle()));
         return "redirect:/api/product/show-all-products";
    }

    @PostMapping(value = "/save-several-products")
    public String saveSeveralProducts(@ModelAttribute Product product) {
        //String fileExtension = FilenameUtils.getFullPath(product.getFile().getOriginalFilename().);
            try {
                saveProductsFromCSVFile(product.getFile());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvException e) {
                e.printStackTrace();
            }
        return "redirect:/api/product/show-all-products";
    }

    public void saveProductsFromCSVFile(MultipartFile file) throws IOException, CsvException {
        InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream());
        CSVReader reader = new CSVReader(inputStreamReader);
        List<String[]> rows = reader.readAll();
        rows.stream().forEach(e -> {
            for (int i = 0; i <= e.length; i++) {
                String[] row = e[i].split(",");
                productService.saveProduct(new Product(
                                Integer.parseInt(row[0]),
                                row[1],
                                new BigDecimal(row[2]),
                                new BigDecimal(row[3]),
                                Integer.parseInt(row[4])
                        )
                );
            }
        });
    }

    public void saveProductsFromExcelFile(MultipartFile file) {

    }
}
