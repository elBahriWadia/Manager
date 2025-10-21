package org.example.manager.Models;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private String ceo;
    private List<Factory> factoryList;

    public Company (String CEO, List<Factory> FactoryList) {
        ceo = CEO;
        factoryList = FactoryList;
    }

    public Company () {}

    public String getCeo() {
        return ceo;
    }

    public List<Factory> getFactoryList() {
        return factoryList;
    }

    public void setCeo(String CEO) {
        ceo = CEO;
    }

    public void setFactoryList(List<Factory> factoryList) {
        this.factoryList = factoryList;
    }

    public void addFactorytoComp(Factory factory) {
        if (this.factoryList == null) {
            this.factoryList = new ArrayList<>();
        }
        this.factoryList.add(factory);
    }

    public String report() {
        StringBuilder report = new StringBuilder();
        report.append(ceo.replace(" ", "")).append(":|");
        if (factoryList != null) {
            for (int j = 0; j < factoryList.size(); j++) {
                Factory factory = factoryList.get(j);
                report.append(factory.getCountry().charAt(0)).append(":<");
                List<Warehouse> warehouses = factory.getWarehouses();
                if (warehouses != null) {
                    for (int i = 0; i < warehouses.size(); i++) {
                        Warehouse warehouse = warehouses.get(i);
                        int id = i + 1;

                        report.append(factory.getCountry().charAt(0))
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
                if (factory.getTransitStock() != 0) report.append("-").append(factory.getTransitStock());
                if ((j+1) != factoryList.size()) {
                    report.append(", ");
                }
            }
            report.append("|");
        }
        return report.toString();
    }

    public int currentProductionCapacity() {
        int totalStock = 0;
        if (factoryList != null) {
            for (Factory factory : factoryList) {
                List<Warehouse> warehouses = factory.getWarehouses();
                if (warehouses != null) {
                    for (Warehouse warehouse : warehouses) {
                        totalStock += warehouse.getStock();
                    }
                }
            }
        }
        return totalStock;
    }
}
