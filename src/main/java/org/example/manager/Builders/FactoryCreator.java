package org.example.manager.Builders;

import org.example.manager.Models.Factory;
import org.example.manager.Models.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class FactoryCreator {

    public WarehouseCreator warehouseCreator = new WarehouseCreator();
    public List<Warehouse> warehouseList;

    public Factory addFactory(String country, String... data) {
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("Data length must be even (pairs of stock and employees)");
        }
        if( country == null || country.isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
        if( data.length == 0) {
            throw new IllegalArgumentException("At least one warehouse data must be provided");
        }
        if(warehouseCreator == null) {
            warehouseCreator = new WarehouseCreator();
        }

        List<Warehouse> warehouseList = new ArrayList<>();

        Factory factory = new Factory();
        factory.setCountry(country);
        for(int i = 0; i < data.length; i += 2) {
            int stock = Integer.parseInt(data[i]);
            int employees = Integer.parseInt(data[i + 1]);
            Warehouse warehouse = warehouseCreator.addWarehouse(stock, employees);
            warehouseList.add(warehouse);
        }
        factory.setWarehouses(warehouseList);
        return factory;
    }

}
