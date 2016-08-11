/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringmastery.dao;

import java.io.FileWriter;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author apprentice
 */
public class FlooringMasteryDaoFileTest {

    FlooringMasteryDao testDAO;

    public FlooringMasteryDaoFileTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        FileWriter cleanFile;

        try {
            String fileName = "test_files/EmptyBox.txt";
            cleanFile = new FileWriter(fileName);
//            testDAO = new FlooringMasteryDaoImpl(fileName);
            cleanFile.close();

        } catch (IOException ex) {
        }
    }
    
    /**
     * Test Spec Rundown
     *
     * Add Order: 
     * - Make sure that adding
     *
     * Remove Order - remove existing address - remove non-existent order -
     * remove 'similar' order - remove existing order twice
     *
     * Order Count - count on empty is 0 - count after add new is +1 - count
     * after 'replace' is same - count after add similar is +1 - count after
     * remove existing is -1 - count after remove non-existent is same - count
     * after remove similar is -1 - count after remove existing twice is -1
     *
     * */
}
