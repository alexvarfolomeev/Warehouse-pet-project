package com.warehouse.warehouse.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "stocktable")
public class Stock {
    @Id
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "warehouse")
    private Warehouse warehouse;
}
