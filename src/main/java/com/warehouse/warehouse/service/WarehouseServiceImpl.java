package com.warehouse.warehouse.service;

import com.warehouse.warehouse.repository.entity.Warehouse;
import com.warehouse.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WarehouseServiceImpl {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public Optional<Warehouse> getWarehouseById(Long id) {
        return warehouseRepository.findById(id);
    }
}
