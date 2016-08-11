/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.flooringmastery;

import com.swcguild.flooringmastery.controller.FlooringMasteryController;
import java.io.FileNotFoundException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author apprentice
 */
public class FlooringMasteryApp {
    public static void main(String[] args) throws FileNotFoundException {
    
        ApplicationContext ctxFactory = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController flooringMasteryController = ctxFactory.getBean("controllerBean", FlooringMasteryController.class);
       
        flooringMasteryController.run();
    }


}
