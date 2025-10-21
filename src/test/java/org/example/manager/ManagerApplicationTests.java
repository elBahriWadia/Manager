package org.example.manager;

import org.example.manager.Models.*;
import org.example.manager.Builders.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManagerApplicationTests  {

    /**
     * Test 1: Affichage de la structure d'une entreprise
     * Une entreprise a un PDG, des usines dans différents pays
     * Chaque usine a des entrepôts avec stock de produits et nombre d'employés
     */
    @Test
    public void showCompanyStructure() {
        Company company = new CompanyBuilder()
                .setCEO("Fatima Zahra")
                .addFactory("Maroc", "500", "50", "300", "40", "200", "30")
                // (pays, stock_entrepot1, employés_entrepot1, stock_entrepot2, ...)
                .addFactory("Senegal", "400", "35", "250", "25")
                .build();

        assertEquals(
                "FatimaZahra:|M:<Me1:500-50,Me2:300-40,Me3:200-30>, S:<Se1:400-35,Se2:250-25>|",
                company.report()
        );
        // Format: CEO:|Pays:<Entrepot:stock-employés>|
    }

    /**
     * Test 2: Calcul de la capacité de production totale
     * La capacité = somme des stocks disponibles dans tous les entrepôts
     */
    @Test
    public void showCompanyProductionCapacity() {
        Company company = new CompanyBuilder()
                .setCEO("Ahmed")
                .addFactory("Maroc", "500", "50", "300", "40", "200", "30")
                .addFactory("Senegal", "400", "35", "250", "25")
                .build();

        // Total: 500 + 300 + 200 + 400 + 250 = 1650
        assertEquals(1650, company.currentProductionCapacity());
    }

    /**
     * Test 3: Stock de sécurité aux frontières (pour export)
     * Chaque usine peut avoir un stock de transit aux frontières
     */
    @Test
    public void companyHasTransitStockAtBorders() {
        Company company1 = new CompanyBuilder()
                .setCEO("Karim")
                .addFactory("Maroc", "500", "50", "300", "40", "200", "30")
                .addFactory("Senegal", "400", "35", "250", "25")
                .addTransitStock("150")
                .build();

        Company company2 = new CompanyBuilder()
                .setCEO("Sophia")
                .addFactory("France", "1000", "100", "800", "80", "600", "60", "400", "40")
                .build();

        assertEquals(
                "Karim:|M:<Me1:500-50,Me2:300-40,Me3:200-30>-150, S:<Se1:400-35,Se2:250-25>-150|",
                company1.report()
        );
        assertEquals(
                "Sophia:|F:<Fe1:1000-100,Fe2:800-80,Fe3:600-60,Fe4:400-40>|",
                company2.report()
        );

        assertEquals(1650, company1.currentProductionCapacity());
        assertEquals(2800, company2.currentProductionCapacity());
    }

    /**
     * Test 4: Préparation d'une commande importante
     * L'entreprise avec plus de capacité prépare une grosse commande
     * Elle déplace 40% du stock de chaque entrepôt vers le transit
     * en priorisant l'usine la plus proche du client
     */
    @Test
    public void companyCanPrepareForLargeOrder() {
        Company company1 = new CompanyBuilder()
                .setCEO("Karim")
                .addFactory("Maroc", "500", "50", "300", "40", "200", "30")
                .addFactory("Senegal", "400", "35", "250", "25")
                .addTransitStock("150")
                .build();

        Company company2 = new CompanyBuilder()
                .setCEO("Sophia")
                .addFactory("France", "600", "60", "400", "40")
                .addTransitStock("100")
                .build();

        assertEquals(1650, company1.currentProductionCapacity());
        assertEquals(1000, company2.currentProductionCapacity());

        SupplyChain supplyChain = new SupplyChainBuilder()
                .addCompany(company1)
                .addCompany(company2)
                .addLogisticsNetwork("Maroc:500:Senegal,Maroc:2000:France,Senegal:2500:France")
                // Format: Pays1:distance_km:Pays2
                .build();

        // L'entreprise avec plus de capacité (company1) prépare la commande
        // Elle déplace 40% du stock depuis l'usine la plus proche de l'autre entreprise
        supplyChain.prepareOrder();

        // Maroc est plus proche de France (2000km vs 2500km)
        // Maroc déplace 40% : 500*0.4=200, 300*0.4=120, 200*0.4=80
        // Total déplacé vers transit: 200+120+80 = 400
        // Nouveau transit Maroc: 150 + 400 = 550
        assertEquals(
                "Karim:|M:<Me1:300-50,Me2:180-40,Me3:120-30>-550, S:<Se1:400-35,Se2:250-25>-150|",
                company1.report()
        );
        assertEquals(
                "Sophia:|F:<Fe1:600-60,Fe2:400-40>-100|",
                company2.report()
        );
    }

    /**
     *  Test 5: Optimisation de la distribution
     * Ce test simule une situation où deux entreprises possèdent des usines avec des entrepôts
     *  et des stocks en transit.
     * Une commande de 250 unités doit être livrée à l'entreprise en déficit.
     * L'entreprise disposant du surplus prépare la commande en déplaçant 40% de ses stocks vers le transit
     * (selon la logique du test 4) puis exécute la livraison.
     * Le test vérifie que les stocks des entrepôts et du transit sont correctement mis à jour
     * et que les indicateurs de performance (efficacité et satisfaction client) sont conformes.
     **/

    @Test
    public void companiesCanOptimizeDistribution_CorrectedWith40Percent() {
        Company company1 = new CompanyBuilder()
                .setCEO("Youssef")
                .addFactory("Maroc", "500", "50", "300", "40")
                .addTransitStock("200")
                .build();

        Company company2 = new CompanyBuilder()
                .setCEO("Maria")
                .addFactory("Espagne", "300", "30", "200", "20")
                .addTransitStock("100")
                .build();

        SupplyChain supplyChain = new SupplyChainBuilder()
                .addCompany(company1)
                .addCompany(company2)
                .addLogisticsNetwork("Maroc:1500:Espagne")
                .setOrderSize(250) // Commande de 250 unités
                .build();
        // Déplace 40% du stock de chaque entrepôt vers le transit
        supplyChain.prepareOrder();
        supplyChain.executeDelivery();

        // Vérification des stocks après livraison
        assertEquals(
                "Youssef:|M:<Me1:300-50,Me2:180-40>-270|",
                company1.report()
        );
        assertEquals(
                "Maria:|E:<Ee1:300-30,Ee2:200-20>-350|",
                company2.report()
        );
        // KPIs (si calculés)
        assertEquals("EFFICIENT", supplyChain.deliveryStatus());
    }

}