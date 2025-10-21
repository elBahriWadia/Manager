package org.example.manager.Models;

import java.util.ArrayList;
import java.util.List;

public class SupplyChain {

    private Company company1;
    private Company company2;
    private List<LogisticsNetwork> logisticsNetworks;

    public SupplyChain() {}

    public SupplyChain(Company company1, Company company2, List<LogisticsNetwork> logisticsNetworks) {
        this.company1 = company1;
        this.company2 = company2;
        this.logisticsNetworks = logisticsNetworks;
    }

    public Company getCompany1() {
        return company1;
    }

    public Company getCompany2() {
        return company2;
    }

    public List<LogisticsNetwork> getLogisticsNetworks() {
        return logisticsNetworks;
    }

    public void setCompany1(Company company1) {
        this.company1 = company1;
    }

    public void setCompany2(Company company2) {
        this.company2 = company2;
    }

    public void setLogisticsNetworks(List<LogisticsNetwork> logisticsNetworks) {
        this.logisticsNetworks = logisticsNetworks;
    }

    public void prepareOrder() {
        Company sender;
        Company receiver;
        if (company1.currentProductionCapacity() >= company2.currentProductionCapacity()) {
            sender = company1;
            receiver = company2;
        } else {
            sender = company2;
            receiver = company1;
        }

        List<Factory> senderFactories = sender.getFactoryList();
        List<Factory> receiverFactories = receiver.getFactoryList();
        List<String> senderCountries = new ArrayList<>();
        List<String> receiverCountries = new ArrayList<>();

        for (Factory factory : senderFactories) {
            senderCountries.add(factory.getCountry());
        }
        for (Factory factory : receiverFactories) {
            receiverCountries.add(factory.getCountry());
        }

        LogisticsNetwork closestNetwork = null;

        for (LogisticsNetwork logNet : logisticsNetworks) {
            boolean senderInvolved = senderCountries.contains(logNet.getCountry1()) || senderCountries.contains(logNet.getCountry2());
            boolean receiverInvolved = receiverCountries.contains(logNet.getCountry1()) || receiverCountries.contains(logNet.getCountry2());

            if (senderInvolved && receiverInvolved) {
                if (closestNetwork == null || logNet.getDistance() < closestNetwork.getDistance()) {
                    closestNetwork = logNet;
                }
            }
        }

        if (closestNetwork != null) {
            String senderCountry = senderCountries.contains(closestNetwork.getCountry1()) ? closestNetwork.getCountry1() : closestNetwork.getCountry2();

            for (Factory factory : senderFactories) {
                if (factory.getCountry().equals(senderCountry)) {
                    for (Warehouse warehouse : factory.getWarehouses()) {
                        int stockToMove = (int) (warehouse.getStock() * 0.4);
                        warehouse.setStock(warehouse.getStock() - stockToMove);
                        factory.setTransitStock(factory.getTransitStock() + stockToMove);
                    }
                }
            }
        }

    }

}
