package com.warehouse.warehouse.model;


public enum Warehouse {
    STOCK1("Склад 1"), STOCK2("Склад 2"), STOCK3("Склад 3");

    private String stockName;

    Warehouse(String stockName) {
        this.stockName = stockName;
    }
}
