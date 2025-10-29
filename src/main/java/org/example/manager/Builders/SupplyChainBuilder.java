package org.example.manager.Builders;

import org.example.manager.Models.Company;
import org.example.manager.Models.LogisticsNetwork;
import org.example.manager.Models.SupplyChain;

import java.util.ArrayList;
import java.util.List;

public class SupplyChainBuilder {
    public SupplyChain supplyChain;
    public List<LogisticsNetwork> logisticsNetworks;
    public SupplyChainBuilder() {
        supplyChain = new SupplyChain();
    }
    public SupplyChainBuilder addCompany(Company company) {
        if (supplyChain.getCompany1() == null) {
            supplyChain.setCompany1(company);
        } else {
            supplyChain.setCompany2(company);
        }
        return this;
    }

    public SupplyChainBuilder addLogisticsNetwork(String data) {
        if (logisticsNetworks == null) {
            logisticsNetworks = new ArrayList<>();
        }
        String[] networks = data.split(",");
        for (String network : networks) {
            String[] parts = network.split(":");
            String country1 = parts[0];
            int distance = Integer.parseInt(parts[1]);
            String country2 = parts[2];
            LogisticsNetwork logisticsNetwork = new LogisticsNetwork(country1, country2, distance);
            logisticsNetworks.add(logisticsNetwork);
        }
        supplyChain.setLogisticsNetworks(logisticsNetworks);
        return this;
    }

    public SupplyChainBuilder setOrderSize(int orderSize) {
        supplyChain.setOrderSize(orderSize);
        return this;
    }

    public SupplyChain build() {
        return supplyChain;
    }
}
