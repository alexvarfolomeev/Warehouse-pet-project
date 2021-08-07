package com.warehouse.warehouse.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article")
    private Integer article;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "last_purchase_price")
    private BigDecimal last_purchase_price;

    @Column(name = "last_sell_price")
    private BigDecimal last_sell_price;

}
