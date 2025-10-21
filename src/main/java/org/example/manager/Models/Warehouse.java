package org.example.manager.Models;

public class Warehouse {
    private int stock;
    private int employees;

    public Warehouse(int Stock, int Employees) {
        stock = Stock;
        employees = Employees;
    }

    public Warehouse() {}

    public int getStock() {
        return stock;
    }

    public int getEmployees() {
        return employees;
    }

    public void setStock(int Stock) {
        stock = Stock;
    }

    public void setEmployees(int Employees) {
        employees = Employees;
    }
}
