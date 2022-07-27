package com.warehouse.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product", schema = "warehouse")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article")
    private Integer article;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "last_purchase_price")
    private BigDecimal lastPurchasePrice;

    @Column(name = "last_sell_price")
    private BigDecimal lastSellPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Transient
    private MultipartFile file;

    public Product(Integer integer, String s, BigDecimal bigDecimal, BigDecimal bigDecimal1, Integer integer1) {
    }
}
