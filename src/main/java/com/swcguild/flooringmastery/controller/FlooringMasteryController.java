/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringmastery.controller;

import com.swcguild.flooringmastery.dao.FlooringMasteryDaoImpl;
import com.swcguild.flooringmastery.model.Flooring;
import com.swcguild.flooringmastery.model.Order;
import com.swcguild.flooringmastery.ui.ConsoleIO;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author apprentice
 */
public class FlooringMasteryController {

    private String again = "";
    private int orderNumber = 0;
    private int maxChoice = 6;

    DecimalFormat df = new DecimalFormat("#.00");

    DateTimeFormatter customDate = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    ConsoleIO console;
    FlooringMasteryDaoImpl daoLayer;

    public FlooringMasteryController(ConsoleIO console, FlooringMasteryDaoImpl daoLayer) {
        this.console = console;
        this.daoLayer = daoLayer;

    }

    public void run() throws FileNotFoundException {
        boolean keepRunning = true;
        int userChoice;
        console.print("\nWelcome to Little Bear Flooring Company!");
        while (keepRunning) {
            printMenu();
            userChoice = console.readInt("\nPlease make a choice: ", 1, maxChoice);
            switch (userChoice) {
                case 1: // Display Order
                    displayOrders();
                    break;
                case 2: // Add Order
                    addOrder();
                    break;
                case 3: // Edit Order Info
                    editOrder();
                    break;
                case 4: // Remove Order
                    removeOrder();
                    break;
                case 5: // Save
                    daoLayer.saveWork();
                    break;
                case 6: // Save and Exit
                    daoLayer.saveWork();
                    console.print("\nThank you for ordering your flooring!");
                    keepRunning = false;
                    break;
                case 7: //Testing purposes only
                    listAll();
                    break;
                default:
                    console.print("\nDanger Will Robinson.");
                    break;
            }
        }
    }

    public void printMenu() throws FileNotFoundException {

        if (!daoLayer.checkMode()) {
            maxChoice = 4;
            console.print("\nYou are currently in TEST mode.");
        }
        console.print("\n1. Display Orders");
        console.print("2. Add an Order");
        console.print("3. Edit an Order");
        console.print("4. Remove an Order");

        if (daoLayer.checkMode()) {
            console.print("5. Save Current Work");
            console.print("6. Save and Exit");
        }
    }

    public void addOrder() {
        Order order = new Order();
        Flooring flooring = new Flooring();
        int qCounter = 0;
        double area;
        again = "y";
        while (again.equals("y")) {
            order.setFlooring(flooring);

            order.setCustName(console.readString("What is your first and last name?"));

            printFlooringType();
            String flooringType = console.readString("");
            while (daoLayer.getFlooringByType(flooringType) == null) {
                console.print("You have typed an incorrect flooring type.");
                printFlooringType();
                flooringType = console.readString("");
                qCounter++;
                if (!questionCounter(qCounter)) {
                    return;
                }
            }
            order.getFlooring().setProductType(flooringType);
            qCounter = 0;

            order.getFlooring().setLaborCostPerSquareFoot(daoLayer.getFlooringByType(flooringType).getLaborCostPerSquareFoot());
            order.getFlooring().setMatCostPerSquareFoot(daoLayer.getFlooringByType(flooringType).getMatCostPerSquareFoot());
            area = console.readDouble("How many square feet of flooring would you like?");
            while (area <= 0) {
                console.print("Sorry but you must enter a number greater than 0");
                area = console.readDouble("How many square feet of flooring would you like?");
                qCounter++;
                if (!questionCounter(qCounter)) {
                    return;
                }
            }
            order.setArea(area);

            qCounter = 0;
            printStateName();
            String state = (console.readString("").toLowerCase());
            while (daoLayer.getStateTaxByState(state) == -1) {
                console.print("Sorry but you must enter two letters that match with the state of your choosing");
                printStateName();
                state = (console.readString("").toLowerCase());
                qCounter++;
                if (!questionCounter(qCounter)) {
                    return;
                }
            }
            order.setState(state);
            order.setTaxRate(daoLayer.getStateTaxByState(state));

            String date = getDateFromUser("add");
            if (date == null) {
                return;
            }

            LocalDate retrievalDate = LocalDate.parse(date, customDate);

            order.setOrderDate(retrievalDate);

            

            double totalLaborCost = order.getFlooring().getLaborCostPerSquareFoot() * order.getArea();
            order.setTotalLaborCost(Math.floor(totalLaborCost * 100) / 100);

            double totalMaterialCost = order.getFlooring().getMatCostPerSquareFoot() * order.getArea();
            order.setTotalMaterialCost(Math.floor(totalMaterialCost * 100) / 100);

            double totalBeforeTax = (((order.getFlooring().getLaborCostPerSquareFoot() * order.getArea()) + (flooring.getMatCostPerSquareFoot() * order.getArea())));

            double total = totalBeforeTax + totalBeforeTax * (order.getTaxRate() * .01);
            order.setTotal(Math.floor(total * 100) / 100);

            double tax = (order.getTaxRate() * .01) * totalBeforeTax;
            order.setTax(Math.floor(tax * 100) / 100);

            console.print("Here is your order to review: ");
            console.print("\nOrder Number : " + (daoLayer.findMaxKey() + 1) + "\nOrder Date: " + customDate.format(order.getOrderDate()) + "\nName : " + order.getCustName() + "\nState: " + order.getState().toUpperCase()
                    + "\nState Tax Rate: " + order.getTaxRate() + "%\nFlooring Type: " + order.getFlooring().getProductType().substring(0, 1).toUpperCase() + order.getFlooring().getProductType().substring(1) + "\nArea: " + order.getArea() + "\nFlooring Cost Per Sq. Ft.: $" + df.format(order.getFlooring().getMatCostPerSquareFoot())
                    + "\nLabor Cost per Sq. Ft. $" + df.format(order.getFlooring().getLaborCostPerSquareFoot()) + "\nTotal Material Cost: $" + df.format(order.getTotalMaterialCost()) + "\nTotal Labor Cost: $"
                    + df.format(order.getTotalLaborCost()) + "\nSubTotal: $" + df.format(order.getTotal() - order.getTax()) + "\nTax: $" + df.format(order.getTax()) + "\nTotal: $" + df.format(order.getTotal()));
            String likeToAdd = console.readString("Would you like to add this order (y/n) ? ").toLowerCase();
            if (likeToAdd.equals("y")) {
                daoLayer.addOrder(order);
            }
            likeToAdd = console.readString("Would you like to add another order (y/n)?").toLowerCase();

            if (likeToAdd.equals("y")) {
            } else {
                return;
            }

        }
    }

    public boolean questionCounter(int questionCounter) {

        String wantReturn;

        if (questionCounter % 3 == 0) {
            wantReturn = console.readString("You have entered an incorrect value. Would you like to return to the main menu (y/n)?").toLowerCase();
            return "n".equals(wantReturn);
        }
        return true;

    }

    public void displayOrders() {
        String date = getDateFromUser("display");
            if (date == null)
                return;
        
        LocalDate retrievalDate = LocalDate.parse(date, customDate);
        if (daoLayer.getOrderByDate(retrievalDate) == null) {
            console.print("There are no corresponding orders for this date.");
            return;
        }

        console.print("Here are your orders for the date : " + retrievalDate + "\n");
        daoLayer.getOrderByDate(retrievalDate).stream()
                .forEach(order -> console.print("\nOrder Number : " + order.getOrderNum() + "\nOrder Date: " + customDate.format(order.getOrderDate()) + "\nName : " + order.getCustName() + "\nState: " + order.getState().toUpperCase()
                        + "\nState Tax Rate: " + order.getTaxRate() + "%\nFlooring Type: " + order.getFlooring().getProductType().substring(0, 1).toUpperCase() + order.getFlooring().getProductType().substring(1) + "\nArea: " + order.getArea() + "\nFlooring Cost Per Sq. Ft.: $" + df.format(order.getFlooring().getMatCostPerSquareFoot())
                        + "\nLabor Cost per Sq. Ft. $" + df.format(order.getFlooring().getLaborCostPerSquareFoot()) + "\nTotal Material Cost: $" + df.format(order.getTotalMaterialCost()) + "\nTotal Labor Cost: $"
                        + df.format(order.getTotalLaborCost()) + "\nSubTotal: $" + df.format(order.getTotal() - order.getTax()) + "\nTax: $" + df.format(order.getTax()) + "\nTotal: $" + df.format(order.getTotal())));

    }

    public void removeOrder() {
        Order order;
        String again = "y";
        while (again.equals("y")) {
            if (daoLayer.orderCount() == 0) {
                console.readString("There are currently no orders to remove.");
                return;
            }
            String date = getDateFromUser("remove");
            if (date == null)
                return;
            LocalDate retrievalDate = LocalDate.parse(date, customDate);

            //order.setOrderDate(retrievalDate);
            int orderNum = console.readInt("What is the order number of the order you would like to remove?");

            if (!daoLayer.getOrderById(orderNum).getOrderDate().equals(retrievalDate)) {
                again = console.readString("We're sorry, but this order does not exist. Would you like to remove another order (y/n)? ").toLowerCase();
                if (again.equals("n")) {
                    break;
                } else {
                    continue;
                }
            }
            console.print("\nYour order has been retrieved:");
            order = daoLayer.getOrderById(orderNum);
            console.print("\nOrder Number : " + order.getOrderNum() + "\nOrder Date: " + order.getOrderDate() + "\nName : " + order.getCustName() + "\nState: " + order.getState().toUpperCase()
                    + "\nState Tax Rate: " + order.getTaxRate() + "%\nFlooring Type: " + order.getFlooring().getProductType().substring(0, 1).toUpperCase() + order.getFlooring().getProductType().substring(1) + "\nArea: " + order.getArea() + "\nFlooring Cost Per Sq. Ft.: $" + df.format(order.getFlooring().getMatCostPerSquareFoot())
                    + "\nLabor Cost per Sq. Ft. $" + df.format(order.getFlooring().getLaborCostPerSquareFoot()) + "\nTotal Material Cost: $" + df.format(order.getTotalMaterialCost()) + "\nTotal Labor Cost: $"
                    + df.format(order.getTotalLaborCost()) + "\nSubTotal: $" + df.format(order.getTotal() - order.getTax()) + "\nTax: $" + df.format(order.getTax()) + "\nTotal: $" + df.format(order.getTotal()));
            String likeToAdd = console.readString("Would you like to remove this order (y/n)? ").toLowerCase();
            if (likeToAdd.equals("y")) {
                daoLayer.removeOrder(orderNum);
                likeToAdd = console.readString("Would you like to remove another order (y/n)?").toLowerCase();
            }
            if (likeToAdd.equals("n")) {
                break;
            }
        }

    }

    public void editOrder() {
        Order order;

        double areaDouble;
        int qCounter = 0;
        again = "y";
        if (daoLayer.orderCount() == 0) {
            console.print("\nYou currently have no orders saved.");
        } else {
            while (again.equals("y")) {
                String date = console.readString("What is the date of the order you would like to edit? (MM-dd-yyyy)");
                String[] splitted = date.split("\\-");
                while (date.length() != 10 || splitted[0].length() != 2 || splitted[1].length() != 2 || splitted[2].length() != 4 || !date.contains("-")) {

                    console.print("Sorry but your date format is incorrect");
                    date = console.readString("What is the date of the order you would like to change? (MM-dd-yyyy)");
                    splitted = date.split("\\-");
                    qCounter++;
                    if (!questionCounter(qCounter)) {
                        return;
                    }
                }
                LocalDate retrievalDate = LocalDate.parse(date, customDate);

                int orderNum = console.readInt("What is the order number of the order you would like to edit?");
                if (daoLayer.getOrderById(orderNum) == null) {
                    again = console.readString("We're sorry, but this order does not exist. Would you like to edit another order (y/n)? ").toLowerCase();
                    if (again.equals("n")) {
                        break;
                    } else {
                        continue;
                    }
                }
                if (!daoLayer.getOrderById(orderNum).getOrderDate().equals(retrievalDate)) {
                    again = console.readString("We're sorry, but an order matching both parameters does not exist. Would you like to remove another order (y/n)?").toLowerCase();
                    if (again.equals("n")) {
                        break;
                    } else {
                        continue;
                    }
                }

                console.print("Your order has been retrieved: ");
                order = daoLayer.getOrderById(orderNum);
                console.print("\nPrevious Name: " + order.getCustName());

                String custName = (console.readString("\nWhat is your first and last name?"));

                if (custName.equals("")) {
                    custName = order.getCustName();
                }

                qCounter = 0;
                console.print("\nPrevious Flooring Type: " + order.getFlooring().getProductType().substring(0, 1).toUpperCase() + order.getFlooring().getProductType().substring(1));
                printFlooringType();
                String flooringType = console.readString("");
                if (flooringType.equals("")) {
                    flooringType = (order.getFlooring().getProductType());
                }
                while (daoLayer.getFlooringByType(flooringType) == null) {
                    console.print("You have typed an incorrect flooring type.");
                    printFlooringType();
                    flooringType = console.readString("");
                    qCounter++;
                    if (!questionCounter(qCounter)) {
                        return;
                    }
                }

                console.print("\nPrevious Sq. Ft. of order: " + order.getArea());

                String area = (console.readString("\nHow many square feet of flooring would you like?"));

                if (area.equals("")) {
                    areaDouble = (order.getArea());
                } else {
                    areaDouble = Double.parseDouble(area);

                }
                console.print("\nPrevious State of order: " + order.getState().toUpperCase());

                qCounter = 0;
                printStateName();
                String state = console.readString("");
                if (state.equals("")) {
                    state = (order.getState());
                }
                while (daoLayer.getStateTaxByState(state) == -1) {
                    console.print("Sorry but you must enter a two letters that match with the state of your choosing");
                    printStateName();
                    state = console.readString("");
                    qCounter++;
                    if (!questionCounter(qCounter)) {
                        return;
                    }
                }

                console.print("\nPrevious Order Date: " + customDate.format(order.getOrderDate()));
                
                date = getDateFromUser("edit");
                if (date == null) {
                    return;
                }

                retrievalDate = LocalDate.parse(date, customDate);

                double totalLaborCost = daoLayer.getFlooringByType(flooringType).getLaborCostPerSquareFoot() * areaDouble;

                double totalMaterialCost = daoLayer.getFlooringByType(flooringType).getMatCostPerSquareFoot() * areaDouble;

                double totalBeforeTax = (((daoLayer.getFlooringByType(flooringType).getLaborCostPerSquareFoot() * areaDouble) + (daoLayer.getFlooringByType(flooringType).getMatCostPerSquareFoot() * areaDouble)));

                double total = totalBeforeTax + (totalBeforeTax * (daoLayer.getStateTaxByState(state) * .01));

                double tax = (daoLayer.getStateTaxByState(state) * .01) * totalBeforeTax;

                console.print("\nHere is your order to review: ");
                console.print("\nOrder Number : " + orderNum + "\nOrder Date: " + customDate.format(retrievalDate) + "\nName : " + custName + "\nState: " + state.toUpperCase()
                        + "\nState Tax Rate: " + daoLayer.getStateTaxByState(state) + "%\nFlooring Type: " + flooringType.substring(0, 1).toUpperCase() + flooringType.substring(1) + "\nArea: " + areaDouble + "\nFlooring Cost Per Sq. Ft.: $" + df.format(daoLayer.getFlooringByType(flooringType).getMatCostPerSquareFoot())
                        + "\nLabor Cost per Sq. Ft. $" + df.format(daoLayer.getFlooringByType(flooringType).getLaborCostPerSquareFoot()) + "\nTotal Material Cost: $" + df.format(totalMaterialCost) + "\nTotal Labor Cost: $"
                        + df.format(totalLaborCost) + "\nSubTotal: $" + df.format(totalBeforeTax) + "\nTax: $" + (Math.floor(tax * 100) / 100) + "\nTotal: $" + (Math.floor(total * 100) / 100));
                String likeToAdd = console.readString("\nWould you like to alter this order given the above changes (y/n) ? ").toLowerCase();
                if (likeToAdd.equals("y")) {

                    order.getFlooring().setProductType(flooringType);
                    order.getFlooring().setLaborCostPerSquareFoot(daoLayer.getFlooringByType(flooringType).getLaborCostPerSquareFoot());
                    order.getFlooring().setMatCostPerSquareFoot(daoLayer.getFlooringByType(flooringType).getMatCostPerSquareFoot());
                    order.setState(state);
                    order.setTaxRate(daoLayer.getStateTaxByState(state));
                    order.setOrderDate(retrievalDate);
                    order.setTotalLaborCost(Math.floor(totalLaborCost * 100) / 100);
                    order.setTotalMaterialCost(Math.floor(totalMaterialCost * 100) / 100);
                    order.setTotal(Math.floor(total * 100) / 100);
                    order.setTax(Math.floor(tax * 100) / 100);
                    order.setArea(areaDouble);
                    order.setCustName(custName);

                    daoLayer.editOrder(order);
                    break;
                } else {
                    likeToAdd = console.readString("Would you like to edit another order (y/n)?").toLowerCase();
                }
                if (likeToAdd.equals("y")) {
                    break;
                } else {
                    return;
                }

            }
        }
    }

// this is for testing purposes only
    private void listAll() {

        console.print("\nHere are the orders currently in your box: \n");

        daoLayer.listAll().stream().forEach((order) -> {
            console.print("\nOrder Number : " + order.getOrderNum() + "\nOrder Date: " + order.getOrderDate() + "\nName : " + order.getCustName() + "\nState: " + order.getState().toUpperCase()
                    + "\nState Tax Rate: " + order.getTaxRate() + "%\nFlooring Type: " + order.getFlooring().getProductType().substring(0, 1).toUpperCase() + order.getFlooring().getProductType().substring(1) + "\nArea: " + order.getArea() + "\nFlooring Cost Per Sq. Ft.: $" + df.format(order.getFlooring().getMatCostPerSquareFoot())
                    + "\nLabor Cost per Sq. Ft. $" + df.format(order.getFlooring().getLaborCostPerSquareFoot()) + "\nTotal Material Cost: $" + df.format(order.getTotalMaterialCost()) + "\nTotal Labor Cost: $"
                    + df.format(order.getTotalLaborCost()) + "\nSubTotal: $" + df.format(order.getTotal() - order.getTax()) + "\nTax: $" + df.format(order.getTax()) + "\nTotal: $" + df.format(order.getTotal()));
        });
    }

    private void printStateName() {
        console.print("In what state are you purchasing flooring? Here are our locations to choose from: ");
        daoLayer.getStateName().stream().forEach((states) -> {
            if (daoLayer.getStateName().indexOf(states) == daoLayer.getStateName().size() - 1) {
                console.printSameLine("or " + states + "?");
            } else {
                console.printSameLine(states + ", ");
            }
        });

    }

    private void printFlooringType() {

        console.print("What type of flooring would you like :");
        daoLayer.getFlooringType().stream().forEach((floorings) -> {
            if (daoLayer.getFlooringType().indexOf(floorings) == (daoLayer.getFlooringType().size() - 1)) {
                console.printSameLine("or " + floorings + "?");
            } else {
                console.printSameLine(floorings + ", ");
            }
        });

    }

    private String getDateFromUser(String whichFunction) {
        String date;
        String word = "";
        String[] splitted;
        Order order = new Order();
        int qCounter = 0;
        if (whichFunction.equals("edit")){
            word = "edit";
        } else if (whichFunction.equals("add")){
            word = "add";
        } else if (whichFunction.equals("display")){
            word = "display";
        } else if (whichFunction.equals("remove")){
            word = "remove";
        }
        date = console.readString("What is the date of the order you would like to " + word + "? (MM-dd-yyyy)");
        if (date.equals("")) {
            date = (customDate.format(order.getOrderDate()));
        }
        splitted = date.split("\\-");
        while (date.length() != 10 || splitted[0].length() != 2 || splitted[1].length() != 2 || splitted[2].length() != 4 || !date.contains("-")) {

            console.print("Sorry but your date format is incorrect");
            date = console.readString("What is the date of the order you would like to retrieve? (MM-dd-yyyy)");
            splitted = date.split("\\-");
            qCounter++;
            if (!questionCounter(qCounter)) {
                return null;
            }
        }
        return date;
    }

}
