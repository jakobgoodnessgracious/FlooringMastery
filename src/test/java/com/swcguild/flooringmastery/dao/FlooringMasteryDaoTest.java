/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringmastery.dao;

import com.swcguild.flooringmastery.model.Flooring;
import com.swcguild.flooringmastery.model.Order;
import java.time.LocalDate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author apprentice
 */
public class FlooringMasteryDaoTest {

    FlooringMasteryDao testDao;

    public FlooringMasteryDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        //String currentDirName = "test_files";
//        FileWriter cleanFile;

//        new File("test_files");
//        try {
//            new FileWriter("EmptyBox.txt");
//        } catch (IOException ex) {
//            Logger.getLogger(FlooringMasteryDaoTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
        ApplicationContext ctxFactory = new ClassPathXmlApplicationContext("testApplicationContext.xml");
        testDao = ctxFactory.getBean("testDao", FlooringMasteryDao.class);
        //testDao = new FlooringMasteryDaoImpl(currentDirName);

    }

    @After
    public void tearDown() {
    }
    // Product Prices Aid

    final double CARPETMATCOST = 2.25;
    final double CARPETLABORCOST = 2.10;
    final double LAMINATEMATCOST = 1.75;
    final double LAMINATELABORCOST = 2.10;
    final double TILEMATCOST = 3.50;
    final double TILELABORCOST = 4.15;
    final double WOODMATCOST = 5.15;
    final double WOODLABORCOST = 4.75;

    // State Taxes Aid
    final double OH = 6.25;
    final double PA = 6.75;
    final double MI = 5.75;
    final double IN = 6.00;

    protected LocalDate[] testLocalDates = {
        LocalDate.of(1987, 12, 01),
        LocalDate.of(2003, 01, 15),
        LocalDate.of(1955, 10, 31),
        LocalDate.of(1979, 02, 24),
        LocalDate.of(1988, 06, 30),
        LocalDate.of(1903, 03, 20),
        LocalDate.of(1776, 07, 04),
        LocalDate.of(1944, 06, 06),
        LocalDate.of(1995, 06, 10),
        LocalDate.of(2000, 01, 01)
        
    };

    protected Flooring[] testFloorings = {
        new Flooring("laminate", LAMINATEMATCOST, LAMINATELABORCOST),
        new Flooring("carpet", CARPETMATCOST, CARPETLABORCOST),
        new Flooring("tile", TILEMATCOST, TILELABORCOST),
        new Flooring("wood", WOODMATCOST, WOODLABORCOST)
    };
    //Order: orderNum, orderDate, custName, state, taxRate, area, tax, total, flooring, totalmatcost, totlaborcost 
    protected Order[] testOrders = {
        new Order(0, testLocalDates[0], "james", "in", IN, 10.00, 2.61, 46.11, testFloorings[0], 22.5, 21.0),
        new Order(1, testLocalDates[1], "Fellow", "oh", OH, 0.0, 0.0, 0.0, testFloorings[1], 0.0, 0.0),
        new Order(2, testLocalDates[2], "Jacob", "mi", MI, 0.0, 0.0, 0.0, testFloorings[2], 0.0, 0.0),
        new Order(3, testLocalDates[3], "Jamie", "pa", PA, 0.0, 0.0, 0.0, testFloorings[3], 0.0, 0.0),
        new Order(4, testLocalDates[4], "Kota", "in", IN, 0.0, 0.0, 0.0, testFloorings[0], 0.0, 0.0),
        new Order(5, testLocalDates[5], "Bill", "oh", OH, 0.0, 0.0, 0.0, testFloorings[1], 0.0, 0.0),
        new Order(6, testLocalDates[6], "Janet", "mi", MI, 0.0, 0.0, 0.0, testFloorings[2], 0.0, 0.0),
        new Order(7, testLocalDates[7], "Mike", "pa", PA, 0.0, 0.0, 0.0, testFloorings[3], 0.0, 0.0),
        new Order(8, testLocalDates[8], "John", "in", IN, 0.0, 0.0, 0.0, testFloorings[0], 0.0, 0.0),
        new Order(9, testLocalDates[9], "Katie", "oh", OH, 0.0, 0.0, 0.0, testFloorings[1], 0.0, 0.0)
            
    };

    protected Order[] duplicateKeysDifferentNames = {
        new Order(0, testLocalDates[0], "newjames", "in", IN, 10.00, 2.61, 46.11, testFloorings[0], 22.5, 21.0),
        new Order(1, testLocalDates[1], "otherFellow", "oh", OH, 0.0, 0.0, 0.0, testFloorings[1], 0.0, 0.0),
        new Order(2, testLocalDates[2], "alsoJacob", "mi", MI, 0.0, 0.0, 0.0, testFloorings[2], 0.0, 0.0),
        new Order(3, testLocalDates[3], "notJamie", "pa", PA, 0.0, 0.0, 0.0, testFloorings[3], 0.0, 0.0),
        new Order(4, testLocalDates[4], "unKota", "in", IN, 0.0, 0.0, 0.0, testFloorings[0], 0.0, 0.0),
        new Order(5, testLocalDates[5], "aintBill", "oh", OH, 0.0, 0.0, 0.0, testFloorings[1], 0.0, 0.0),
        new Order(6, testLocalDates[6], "shorelynotJanet", "mi", MI, 0.0, 0.0, 0.0, testFloorings[2], 0.0, 0.0),
        new Order(7, testLocalDates[7], "tisntMike", "pa", PA, 0.0, 0.0, 0.0, testFloorings[3], 0.0, 0.0),
        new Order(8, testLocalDates[8], "otherJohn", "in", IN, 0.0, 0.0, 0.0, testFloorings[0], 0.0, 0.0),
        new Order(9, testLocalDates[9], "noKatie", "oh", OH, 0.0, 0.0, 0.0, testFloorings[1], 0.0, 0.0)
    };

    // this is probably pointless
//    protected Order[] similarKeys = {
//        new Order(00, testLocalDates[0], "james", "in", IN, 10.00, 2.61, 46.11, testFloorings[0], 22.5, 21.0),
//        new Order(01, testLocalDates[1], "Fellow", "oh", OH, 0.0, 0.0, 0.0, testFloorings[1], 0.0, 0.0),
//        new Order(02, testLocalDates[2], "Jacob", "mi", MI, 0.0, 0.0, 0.0, testFloorings[2], 0.0, 0.0),
//        new Order(03, testLocalDates[3], "Jamie", "pa", PA, 0.0, 0.0, 0.0, testFloorings[3], 0.0, 0.0),
//        new Order(04, testLocalDates[4], "Kota", "in", IN, 0.0, 0.0, 0.0, testFloorings[0], 0.0, 0.0)
//    };
    @Test
    public void testAgainstEmptyDao() {
        Assert.assertEquals("Expected order count does not match after adding no orders", 0, testDao.orderCount());
        Assert.assertNull("Asking for a non existent order should return null.", testDao.getOrderById(0));
        Assert.assertNotNull("List of all orders should not be null.", testDao.listAll());
        Assert.assertEquals("Expected order count of 'all orders' is nonzero with empty Dao.", 0, testDao.listAll().size());

    }

    @Test
    public void testAddOneOrder() {
        Order orderToAdd = testOrders[0];
        testDao.addOrder(orderToAdd);
        Assert.assertEquals("Expected order count does not match after adding on order.", 1, testDao.orderCount());
        Assert.assertEquals("Returned order does not match expected.", orderToAdd, testDao.getOrderById(0));
        Assert.assertNotNull("List of all orders should not be null.", testDao.listAll());
        Assert.assertEquals("Expected order count of 'all orders' does not match after adding one order.", 1, testDao.listAll().size());
        Assert.assertTrue("Returned order in listAll does not match expected.", testDao.listAll().contains(orderToAdd));
        Assert.assertTrue("Returned order has an order number of zero.", testDao.getOrderById(0).getOrderNum() == 0);
    }

    @Test
    public void testReplaceOneOrder() {
        testDao.addOrder(testOrders[0]);
        testDao.editOrder(duplicateKeysDifferentNames[0]);
        Assert.assertEquals("Replaced order get does not match expected.", duplicateKeysDifferentNames[0], testDao.getOrderById(0));
        Assert.assertNotNull("List of all orders should not be null.", testDao.listAll());
        Assert.assertEquals("Expected order count of 'all orders' does not match after replacing one order.", 1, testDao.listAll().size());
        Assert.assertTrue("Returned order in listAll does not match expected.", testDao.listAll().contains(duplicateKeysDifferentNames[0]));
    }

    @Test
    public void testAddMultipleAddresses() {
        for (Order order : testOrders) {
            testDao.addOrder(order);
        }

        Assert.assertEquals("Expected order count does not match after adding multiple orders.",
                testOrders.length, testDao.orderCount());
        Assert.assertNotNull("List of all orders should not be null.", testDao.listAll());
        Assert.assertEquals("Expected order count of 'all orders' does not match after adding several orders.",
                testOrders.length, testDao.listAll().size());
        for (int i = 0; i < testOrders.length; i++) {
            Assert.assertEquals("Returned order does not match expected.", testOrders[i], testDao.getOrderById(testOrders[i].getOrderNum()));
        }
    }

    @Test
    public void testEditMultipleOrders() {
        for (Order order : testOrders) {
            testDao.addOrder(order);
        }

        for (Order order : duplicateKeysDifferentNames) {
            testDao.editOrder(order);
        }

        Assert.assertEquals("Expected order count does not match after replacing multiple orders.",
                testOrders.length, testDao.orderCount());
        Assert.assertNotNull("List of all orders should not be null.", testDao.listAll());
        Assert.assertEquals("Expected order count of 'all orders' does not match after replacing several orders.",
                testOrders.length, testDao.listAll().size());

        for (int i = 0; i < duplicateKeysDifferentNames.length; i++) {
            Assert.assertEquals("Get order does not match expected return on replace.", duplicateKeysDifferentNames[i],
                    testDao.getOrderById(duplicateKeysDifferentNames[i].getOrderNum()));

        }

    }

    @Test
    public void testAddAndRemoveOneOrder() {
        testDao.addOrder(testOrders[0]);
        Order removed = testDao.removeOrder(testOrders[0].getOrderNum());

        Assert.assertNotNull("Order should be returned on removal.", removed);
        Assert.assertEquals("Order should be returned on removal.", testOrders[0], removed);
        Assert.assertEquals("Expected 0 orders after adding/removing one order.", 0, testDao.orderCount());
        Assert.assertNull("Order should return null after being removed.", testDao.getOrderById(testOrders[0].getOrderNum()));
        Assert.assertNotNull("List of all orders should not be null.", testDao.listAll());
        Assert.assertEquals("Expected order count of 'all orders' should be zero when adding/removing a single order.", 0, testDao.listAll().size());
    }

    @Test
    public void testAddAndRemoveOrderTwice() {
        testDao.addOrder(testOrders[0]);
        testDao.removeOrder(testOrders[0].getOrderNum());
        Order removed = testDao.removeOrder(testOrders[0].getOrderNum());

        Assert.assertNull("Order should be removed first time, and act as nonexistent address on second removal (returning null)", removed);
        Assert.assertEquals("Expected 0 orders after adding/removing the same order twice.", 0, testDao.orderCount());
        Assert.assertNull("Order should return null after being removed.", testDao.getOrderById(testOrders[0].getOrderNum()));;
        Assert.assertNotNull("List of all orders should not be null.", testDao.listAll());
        Assert.assertEquals("Expected order count of 'all orders' should e zero when adding/removing a single order twice.", 0, testDao.listAll().size());
    }

    @Test
    public void testAddAndRemovMultipleorders() {
        for (Order order : testOrders) {
            testDao.addOrder(order);
        }

        int ordersAdded = testOrders.length;
        for (int i = 0; i < testOrders.length; i += 2) {
            Order removed = testDao.removeOrder(testOrders[i].getOrderNum());
            Assert.assertNotNull("Order should be returned on removal.", removed);
            Assert.assertEquals("Order should be returned on removal.", testOrders[i], removed);
            ordersAdded--;
        }

        Assert.assertEquals("Expected order count does not match after adding two orders.", ordersAdded, testDao.orderCount());
        Assert.assertNotNull("List of all orders should not be null.", testDao.listAll());
        Assert.assertEquals("Expected order count of 'all orders' does not match after adding & removing several orders.",
                ordersAdded, testDao.listAll().size());

        for (int i = 0; i < testOrders.length; i++) {
            if (i % 2 != 0) {
                Assert.assertEquals("Returned order does not match expected.", testOrders[i], testDao.getOrderById(testOrders[i].getOrderNum()));
            } else {
                Assert.assertNull("Order should be removed and return null.", testDao.getOrderById(testOrders[i].getOrderNum()));
            }

        }
    }
    
    @Test
    public void testAddAndRemoveOrdersMultipleTimes(){
        for (Order order : testOrders){
            testDao.addOrder(order);
        }
        
        for (Order order : testOrders){
            testDao.removeOrder(order.getOrderNum());
        }
        
        for (Order order :testOrders){
            testDao.addOrder(order);
        }
        
        Assert.assertEquals("Expected " + testOrders.length + " orders after re-adding all orders.",
                testOrders.length, testDao.orderCount());
        Assert.assertEquals("Order should return after being re-added.",
                testOrders[0], testDao.getOrderById(testOrders[0].getOrderNum()));
        Assert.assertNotNull("List of all orders should not be null.", testDao.listAll());
        Assert.assertEquals("Expected order count of 'all orders' should be zero when adding/removing all orders.",
                testOrders.length, testDao.listAll().size());
        
        for (Order order : testOrders){
            testDao.removeOrder(order.getOrderNum());
            testDao.addOrder(order);
            testDao.removeOrder(order.getOrderNum());
        }
        
        Assert.assertEquals("Expected 0 orders after adding/removing all orders.", 0, testDao.orderCount());
        Assert.assertNull("Order should return null after being removed.", testDao.getOrderById(testOrders[0].getOrderNum()));
        Assert.assertNotNull("List of all orders should not be null.", testDao.listAll());
        Assert.assertEquals("Expected order count of 'all orders' should be zero when adding/removing all orders", 0, testDao.listAll().size());
    }
    
    @Test
    public void testOrderCountOnEmpty(){
        Assert.assertNotNull("Expected 0 orders before having added any orders", testDao.orderCount());
        Assert.assertTrue("Expected to equal 0 orders before adding any orders", testDao.orderCount() == 0);
    }
    
    
    @Test
    public void testOrderCountOnAdditioin(){
        for (int i = 0; i < testOrders.length; i++) {
            testDao.addOrder(testOrders[i]);
            Assert.assertEquals("Expected " + (i + 1) + " addresses after adding addresses.",
                    i + 1, testDao.orderCount());
        }
    }
    
    @Test 
    public void testOrderCountAfterRemoval(){
        
        for (int i = 0; i < testOrders.length; i++) {
            testDao.addOrder(testOrders[i]);
            
        }
        for (int i = 0; i < testOrders.length; i++) {
            testDao.removeOrder(testOrders[i].getOrderNum());
            Assert.assertEquals("Expected " + (testOrders.length - i - 1) + " orders after removing orders.",
                    testOrders.length - i - 1, testDao.orderCount());
            
        }
    }
    @Test
    public void testOrderCountAfterRemovalOfNonexistent(){
        for (int i = 0; i < testOrders.length; i++) {
            testDao.addOrder(testOrders[i]);
            
        }
        
        testDao.removeOrder(11);
        Assert.assertEquals("Expected " + testOrders.length + " orders after removing addresses.",
                testOrders.length, testDao.orderCount());
    }
    
    @Test
    public void testOrderCountAfterTwiceRemoved(){
        for (int i = 0; i < testOrders.length; i++) {
            testDao.addOrder(testOrders[i]);
            
        }
        
        for (int i = 0; i < testOrders.length; i++) {
            testDao.removeOrder(testOrders[i].getOrderNum());
            
        }
        
        Assert.assertEquals("Expected " + 0 + " orders after removing orderes.",
                0, testDao.orderCount());
        
        for (int i = 0; i < testOrders.length; i++) {
            testDao.removeOrder(testOrders[i].getOrderNum());
            
        }
        
        Assert.assertEquals("Expected " + 0 + " orders after attempting to re-remove orders.",
                0, testDao.orderCount());
    }
    
    @Test
    public void testOrderCountOnReplaceExisting(){
        
        for (int i = 0; i < testOrders.length; i++) {
            testDao.addOrder(testOrders[i]);
            
        }
        
        for (int i = 0; i < testOrders.length; i++) {
            testDao.editOrder(duplicateKeysDifferentNames[i]);
            Assert.assertEquals("Expected " + testOrders.length + " orders after adding & replacing orders.", 
                    testOrders.length, testDao.orderCount());
        }
    }

    
    @Test
    public void testGetStateTaxByState(){
        Assert.assertTrue("Expected state tax did not return expected value", testDao.getStateTaxByState("oh") == 6.25);
        Assert.assertTrue("Expected a return of -1 if state not entered correctly.", testDao.getStateTaxByState("silly") == -1);
        Assert.assertNotNull("Expected a return value not null . . . but value was null", testDao.getStateTaxByState("sillysguy"));
        Assert.assertNotNull("Put in a number instead of letters, expected a result of -1 but was null", testDao.getStateTaxByState("-1"));
        Assert.assertTrue("Expected -1 when state tax is capitalized -- though this should never happen :(", testDao.getStateTaxByState("OH") == -1);
        
        
    }
    
    @Test
    public void testGetFlooringByType(){
        Assert.assertNotNull("Expected product type was in fact null though expected not null", testDao.getFlooringByType("carpet"));
        Assert.assertNull("Expected a return of null if product not entered correctly.", testDao.getFlooringByType("silly"));
        Assert.assertTrue("Expected labor cost for flooring type were not the resulted values", testDao.getFlooringByType("carpet").getLaborCostPerSquareFoot() == 2.10);
        Assert.assertTrue("Expected material cost for flooring type were not the resulted values", testDao.getFlooringByType("carpet").getMatCostPerSquareFoot() == 2.25);
        Assert.assertTrue("Expected flooring for flooring type were not the resulted values", testDao.getFlooringByType("carpet").getProductType().equals("Carpet"));
        //Assert.assertNotNull("Expected a return value not null . . . but value was null", testDao.getStateTaxByState("sillysguy"));
        //Assert.assertNotNull("Put in a number instead of letters, expected a result of -1 but was null", testDao.getStateTaxByState("-1"));
        
    }
    
    @Test
    public void testFindMaxKey(){
        testDao.addOrder(testOrders[0]);
        Assert.assertNotNull("Expected max key when 1 items exist should not return null", testDao.findMaxKey());
        Assert.assertTrue("Expected max key to be 0 when only when item added but not true", testDao.findMaxKey() == 0);
        
        testDao.removeOrder(0);
        Assert.assertTrue("Expected 0 when 0 items exist",testDao.findMaxKey() == 0);
        Assert.assertNotNull("Expected not to be null when 0 items exist", testDao.findMaxKey());
    }
    
    @Test
    public void testEditOrder(){
        testDao.editOrder(testOrders[1]);
        Assert.assertTrue("There should be one item  after editing an order one decides to confirm re-adding that order",
                testDao.orderCount() == 1);
        testOrders[0].setOrderNum(1);
        testDao.editOrder(testOrders[0]);
        Assert.assertTrue("The test order that was edited should replace the first order edited because its order number was changed to match it", testDao.getOrderById(1).getCustName().equals("james"));
    }
    @Test
    public void testEditOrderAFterAddingOne(){
        testDao.addOrder(testOrders[0]);
        testDao.editOrder(duplicateKeysDifferentNames[0]);
        Assert.assertEquals("Expected order count does not match after replacing one order.", 1, testDao.orderCount());
    }
    
    @Test 
    public void testGetOrderByDate(){
        testDao.addOrder(testOrders[4]);
        Assert.assertNotNull("Should not be null after adding a date and correct order date entered to retrieve order", testDao.getOrderByDate(testOrders[4].getOrderDate()));
        testDao.removeOrder(testOrders[4].getOrderNum());
        Assert.assertNull("Should be null after removing only order and ordersBox is empty", testDao.getOrderByDate(testOrders[4].getOrderDate()));
        
    }
    
    
    
    
    // didn't finish tests
    // didn't finish eliminating exceptions thrown
}
