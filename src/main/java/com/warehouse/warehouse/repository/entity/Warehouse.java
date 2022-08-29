package com.warehouse.warehouse.repository.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "warehouses", schema = "warehouse")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "warehouse_number")
    private Integer warehouseNumber;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "warehouse")
    @EqualsAndHashCode.Exclude
    private Set<Product> product;

    public Warehouse(Integer warehouseNumber) {
        this.warehouseNumber = warehouseNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return Objects.equals(id, warehouse.id) && Objects.equals(warehouseNumber, warehouse.warehouseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, warehouseNumber);
    }
}
