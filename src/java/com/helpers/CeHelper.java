/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class CeHelper {
    public static String getStaticProductList() {
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("ce.json").getPath();
            File json = new File(path);
            Scanner in = new Scanner(new FileReader(json));
            String retVal = "";
            while(in.hasNextLine()){
                retVal += in.nextLine();
            }
            return retVal;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProductsHelper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
