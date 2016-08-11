/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringmastery.model;

/**
 *
 * @author apprentice
 */
public class Flooring {
    private String productType;
    private double matCostPerSquareFoot;
    private double laborCostPerSquareFoot;

    public Flooring(){};
    
    public Flooring(String productType, double costPerSquareFoot, double laborCostPerSquareFoot) {
        this.productType = productType;
        this.matCostPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public double getMatCostPerSquareFoot() {
        return matCostPerSquareFoot;
    }

    public void setMatCostPerSquareFoot(double matCostPerSquareFoot) {
        this.matCostPerSquareFoot = matCostPerSquareFoot;
    }

    public double getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(double laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }
    
    
    
}
