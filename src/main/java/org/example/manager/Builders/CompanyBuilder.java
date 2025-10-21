package org.example.manager.Builders;

import org.example.manager.Models.Company;
import org.example.manager.Models.Factory;

public class CompanyBuilder {

    public Company company;
    public FactoryCreator factoryCreator;

    public CompanyBuilder() {
        company = new Company();
    }

    public CompanyBuilder setCEO(String ceo) {
        company.setCeo(ceo);
        return this;
    }

    public CompanyBuilder addFactory(String country, String... stockAndEmployees) {
        if (factoryCreator == null) {
            factoryCreator = new FactoryCreator();
        }
        company.addFactorytoComp(factoryCreator.addFactory(country, stockAndEmployees));
        return this;
    }

    public CompanyBuilder addTransitStock(String transitStock) {
        int TStock = Integer.parseInt(transitStock);
        for (Factory factory : company.getFactoryList()) {
            factory.setTransitStock(TStock);
        }
        return this;
    }

    public Company build() {
        return company;
    }

}
