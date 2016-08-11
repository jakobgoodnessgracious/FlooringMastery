/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringmastery.model;

import java.time.LocalDate;

/**
 *
 * @author apprentice
 */
public class Order {

    private int orderNum;
    private String custName;
    private String state;
    private double taxRate;
    private double area;
    private double tax;
    private double total;
    private LocalDate orderDate;
    private Flooring flooring;
    private double totalMaterialCost;
    private double totalLaborCost;

    public Order(int orderNum,  LocalDate orderDate, String custName, String state, double taxRate, double area, double tax, double total, Flooring flooring, double totalMaterialCost, double totalLaborCost) {
        this.orderNum = orderNum;
        this.custName = custName;
        this.state = state;
        this.taxRate = taxRate;
        this.area = area;
        this.tax = tax;
        this.total = total;
        this.orderDate = orderDate;
        this.flooring = flooring;
        this.totalMaterialCost = totalMaterialCost;
        this.totalLaborCost = totalLaborCost;
    }

    public Order() {
    }

    public double getTotalMaterialCost() {
        return totalMaterialCost;
    }

    public void setTotalMaterialCost(double totalMaterialCost) {
        this.totalMaterialCost = totalMaterialCost;
    }

    public double getTotalLaborCost() {
        return totalLaborCost;
    }

    public void setTotalLaborCost(double totalLaborCost) {
        this.totalLaborCost = totalLaborCost;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Flooring getFlooring() {
        return flooring;
    }

    public void setFlooring(Flooring flooring) {
        this.flooring = flooring;
    }

}
