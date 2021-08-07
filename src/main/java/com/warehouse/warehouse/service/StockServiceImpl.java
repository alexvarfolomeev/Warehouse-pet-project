package com.warehouse.warehouse.service;

import com.warehouse.warehouse.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl {

    @Autowired
    private StockRepository stockRepository;


}
