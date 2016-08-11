/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringmastery.dao;

import com.swcguild.flooringmastery.model.Flooring;
import com.swcguild.flooringmastery.model.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author apprentice
 */
public class FlooringMasteryDaoImpl implements FlooringMasteryDao {

    private HashMap<Integer, Order> ordersBox = new HashMap<>();
    private HashMap<String, Double> statesBox = new HashMap<>();
    private HashMap<String, Flooring> flooringBox = new HashMap<>();
    private String currentDirName = "orders";
    private String fileName = "Orders_MMDDYYYY.txt";
    private String stateFile = "Data/stateFile.txt";
    private String productFile = "Data/productFile.txt";
    private String configFileName = "config.properties";
    private final String DELIMITER = "::";
    private boolean isReadable;
    private DateTimeFormatter customDate = DateTimeFormatter.ofPattern("MMddyyyy");

    private FlooringMasteryDaoImpl(String currentDirName) {
        this.currentDirName = currentDirName;
        ordersBox = new HashMap<>();
        try {
            loadBoxFromFile();
        } catch (FileNotFoundException e) {

        }


    }

    private FlooringMasteryDaoImpl() {
        ordersBox = new HashMap<>();
        try {
            loadBoxFromFile();
        } catch (FileNotFoundException e) {
        }


    }

/// for this part think about fruit writer and mpaa rating on dvd library.    
    private void saveBoxToFile() throws FileNotFoundException {
        if (isReadable) {
            HashMap<LocalDate, ArrayList<Order>> orderByDate = new HashMap<>();
            for (Order order : ordersBox.values()) {
                LocalDate localDate = order.getOrderDate();

                if (orderByDate.get(localDate) == null) {
                    orderByDate.put(localDate, new ArrayList<Order>());
                }

                ArrayList<Order> orderThatDate = orderByDate.get(localDate);
                orderThatDate.add(order);

            }

            File dir = new File("orders");
            for(File file: dir.listFiles()) file.delete();
            orderByDate.keySet().stream().forEach((localDate) -> {
                for (Order orders : orderByDate.get(localDate)) {
                    LocalDate orderDate = orders.getOrderDate();
//                        
                    fileName = ("Orders_" + customDate.format(orders.getOrderDate()) + ".txt");

                    File actualFile = new File(dir, fileName);

                    try (PrintWriter boxWriter = new PrintWriter(new FileWriter(actualFile))) {
                        
                        orderByDate.get(localDate).stream().filter((order) -> (order.getOrderDate().equals(orderDate))).forEach((order) -> {
                            boxWriter.println(order.getOrderNum() + DELIMITER
                                    + order.getOrderDate() + DELIMITER
                                    + order.getCustName() + DELIMITER
                                    + order.getState() + DELIMITER
                                    + order.getTaxRate() + DELIMITER
                                    + order.getFlooring().getProductType() + DELIMITER
                                    + order.getArea() + DELIMITER
                                    + order.getFlooring().getMatCostPerSquareFoot() + DELIMITER
                                    + order.getFlooring().getLaborCostPerSquareFoot() + DELIMITER
                                    + order.getTotalMaterialCost() + DELIMITER
                                    + order.getTotalLaborCost() + DELIMITER
                                    + order.getTax() + DELIMITER
                                    + order.getTotal());
                        });

                        // });
                        boxWriter.flush();
                        boxWriter.close();
                    } catch (IOException e) {
                    }
                }
            });
        }

    }


  
    private void loadBoxFromFile() throws FileNotFoundException {

        if (checkMode())
            isReadable = true;
        final String extension = ".txt";
        final File currentDir = new File(currentDirName);
        File[] files = currentDir.listFiles((File pathname) -> pathname.getName().endsWith(extension));

        for (File file : files) {
            Scanner orderScanner = new Scanner(new BufferedReader(new FileReader(file)));
            while (orderScanner.hasNextLine()) {
                Order order = new Order();
                Flooring flooring = new Flooring();
                String lineOOrder = orderScanner.nextLine();
                String[] orderProps = lineOOrder.split(DELIMITER);
                if (orderProps.length != 13) {
                    continue;
                }
                order.setFlooring(flooring);
                order.setOrderNum(Integer.parseInt(orderProps[0]));
                order.setOrderDate(LocalDate.parse(orderProps[1]));
                order.setCustName(orderProps[2]);
                order.setState(orderProps[3]);
                order.setTaxRate(Double.parseDouble(orderProps[4]));
                order.getFlooring().setProductType(orderProps[5]);
                order.setArea(Double.parseDouble(orderProps[6]));
                order.getFlooring().setMatCostPerSquareFoot(Double.parseDouble(orderProps[7]));
                order.getFlooring().setLaborCostPerSquareFoot(Double.parseDouble(orderProps[8]));
                order.setTotalMaterialCost(Double.parseDouble(orderProps[9]));
                order.setTotalLaborCost(Double.parseDouble(orderProps[10]));
                order.setTax(Double.parseDouble(orderProps[11]));
                order.setTotal(Double.parseDouble(orderProps[12]));

                ordersBox.put(order.getOrderNum(), order);
            }
        }
        Scanner fileScanner = new Scanner(new BufferedReader(new FileReader(stateFile)));
        while (fileScanner.hasNextLine()) {
            String lineOOrder = fileScanner.nextLine();
            String[] stateProps = lineOOrder.split(DELIMITER);
            if (stateProps.length != 2) {
                continue;
            }

            statesBox.put(stateProps[0], Double.parseDouble(stateProps[1]));
        }
        fileScanner.close();

        fileScanner = new Scanner(new BufferedReader(new FileReader(productFile)));
        while (fileScanner.hasNextLine()) {
            Flooring flooring = new Flooring();
            String lineOOrder = fileScanner.nextLine();
            String[] productProps = lineOOrder.split(DELIMITER);
            if (productProps.length != 3) {
                continue;
            }

            flooring.setProductType(productProps[0]);
            flooring.setMatCostPerSquareFoot(Double.parseDouble(productProps[1]));
            flooring.setLaborCostPerSquareFoot(Double.parseDouble(productProps[2]));

            flooringBox.put(flooring.getProductType(), flooring);
        }
        fileScanner.close();
    }

    @Override
    public int orderCount() {
        return ordersBox.size();
    }

    @Override
    public void addOrder(Order orderToAdd) {
        if (orderCount() == 0) {
                orderToAdd.setOrderNum(0);
            } else {
                orderToAdd.setOrderNum(findMaxKey() + 1);
            }
        ordersBox.put(orderToAdd.getOrderNum(), orderToAdd);

    }

    @Override
    public Order getOrderById(int orderNumber) {
        
        if (ordersBox.get(orderNumber) != null) {
            return ordersBox.get(orderNumber);

        } else {
            return null;
        }
    }

    @Override
    public Order removeOrder(int orderNumber) {
        Order order = ordersBox.get(orderNumber);
        ordersBox.remove(orderNumber);

        return order;
    }

    @Override
    public List<Order> getOrderByDate(LocalDate localDate) {
        List<Order> ordersList = new ArrayList<>();
        for (Order order : ordersBox.values()) {
            if (order.getOrderDate().equals(localDate)) {
                ordersList.add(ordersBox.get(order.getOrderNum()));
            }
            
        }
        
        if (ordersList.isEmpty())
            return null;
        else
        return ordersList;

    }
    
    

    @Override
    public Order editOrder(Order orderToEdit) {

        return ordersBox.put(orderToEdit.getOrderNum(), orderToEdit);

    }
    @Override
    public int findMaxKey() {
        int largestKeyNumber;
        if (orderCount() > 0){
        largestKeyNumber = Collections.max(ordersBox.keySet());
        }
        else 
            largestKeyNumber = 0;
        
        return largestKeyNumber;
    }

    public List<String> getFlooringType(){
        List<String> flooringTypeList = new ArrayList<>();
        for (String flooring : flooringBox.keySet())
            flooringTypeList.add(flooring);
        
        return flooringTypeList;
    }
    
    @Override
    public Flooring getFlooringByType(String flooringType) {
        Flooring flooring = new Flooring();
        for (String floorings : flooringBox.keySet()) {
            if (floorings.toLowerCase().equals(flooringType)) {
                flooring = flooringBox.get(floorings);
                break;
            } else {
                flooring = null;
            }
        }

        return flooring;
    }

    public List<String> getStateName(){
        List<String> statesList = new ArrayList<>();
        for (String states : statesBox.keySet())
            statesList.add(states);
        
        return statesList;
    }
    @Override
    public double getStateTaxByState(String state) {
        double doubleToReturn = 0;
        for (String states : statesBox.keySet()) {
            if (states.toLowerCase().equals(state)) {
                doubleToReturn = statesBox.get(states);
                break;
            } else {
                doubleToReturn = -1;
            }
        }
        return doubleToReturn;

    }

    // tester -- could be enabled in test mode. . . v2.0!!
    @Override
    public List<Order> listAll() {
        Collection<Order> ordersCollection = ordersBox.values();
        List<Order> orders = new ArrayList<>(ordersCollection);

        return orders;

    }

    private HashMap<LocalDate, ArrayList<Order>> getDates() {
        HashMap<LocalDate, ArrayList<Order>> orderByDate = new HashMap<>();
        for (Order order : ordersBox.values()) {
            LocalDate localDate = order.getOrderDate();

            if (orderByDate.get(localDate) == null) {
                orderByDate.put(localDate, new ArrayList<Order>());
            }

            ArrayList<Order> orderThatDate = orderByDate.get(localDate);
            orderThatDate.add(order);

        }
        return orderByDate;
    }

    @Override
    public void saveWork() {
        try {
            saveBoxToFile();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FlooringMasteryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean checkMode() throws FileNotFoundException {
        Scanner configScanner;
        try {
            configScanner = new Scanner(new BufferedReader(new FileReader(configFileName)));
            String lineOConfig = configScanner.nextLine();

            if (lineOConfig.equals("Prod"))
                return true;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FlooringMasteryDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
