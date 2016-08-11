/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringmastery.dao;

import com.swcguild.flooringmastery.model.Flooring;
import com.swcguild.flooringmastery.model.Order;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author apprentice
 */
public interface FlooringMasteryDao {

    public void addOrder(Order order);

    public Order removeOrder(int orderNumber);

    public List<Order> getOrderByDate(LocalDate localdate);

    public Order editOrder(Order orderToEdit);

    public Order getOrderById(int orderNumber);

    public Flooring getFlooringByType(String flooringType);

    public double getStateTaxByState(String state);

    public void saveWork();

    public List<Order> listAll();

    public int orderCount();

    public boolean checkMode() throws FileNotFoundException;

    public int findMaxKey();

}
