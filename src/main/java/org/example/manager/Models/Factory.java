package org.example.manager.Models;

import java.util.List;

public class Factory {

    private String country;

    private int transitStock = 0;

    private List<Warehouse> warehouses;

    public Factory(String Country, List<Warehouse> Warehouses) {
        country = Country;
        warehouses = Warehouses;
    }

    public Factory() {}

    public void setCountry(String Country) {
        country = Country;
    }

    public void setWarehouses(List<Warehouse> Warehouses) {
        warehouses = Warehouses;
    }

    public String getCountry() {
        return country;
    }
    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public int getTransitStock() {
        return transitStock;
    }

    public void setTransitStock(int TransitStock) {
        transitStock = TransitStock;
    }

    public String report() {
        StringBuilder report = new StringBuilder();
        report.append(country.charAt(0)).append(":<");
        if (warehouses != null) {
            for (int i = 0; i < warehouses.size(); i++) {
                Warehouse warehouse = warehouses.get(i);
                int id = i + 1;

                report.append(country.charAt(0))
                        .append("e").append(id)
                        .append(":")
                        .append(warehouse.getStock())
                        .append("-")
                        .append(warehouse.getEmployees());
                if (id != warehouses.size()) {
                    report.append(",");
                }
            }
        }
        report.append(">");
        return report.toString();
    }


}
