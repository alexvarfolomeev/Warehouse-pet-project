package com.warehouse.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MoveProducts {
    private Long productId;
    private Long firstWarehouse;
    private Long secondWarehouse;
}
