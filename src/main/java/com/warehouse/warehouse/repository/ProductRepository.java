package com.warehouse.warehouse.repository;

import com.warehouse.warehouse.repository.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Product findProductByArticle(Integer article);

    @Query("SELECT p, wh.id, wh.warehouseNumber FROM Product  p " +
            "inner join p.warehouse wh where p.id = :id and p.warehouse.id = :warehouseId")
    Product findProductByIdAndWarehouse(@Param("id") Long id, @Param("warehouseId") Long warehouseId);
}
