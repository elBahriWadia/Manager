package org.example.manager.Builders;

import org.example.manager.Models.Warehouse;

public class WarehouseCreator {

    public Warehouse addWarehouse(int stock, int employees) {
        Warehouse warehouse = new Warehouse();
        warehouse.setStock(stock);
        warehouse.setEmployees(employees);
        return warehouse;
    }

}
