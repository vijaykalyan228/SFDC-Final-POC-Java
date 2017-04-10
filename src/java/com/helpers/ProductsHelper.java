/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpers;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.*;
import com.sforce.soap.enterprise.sobject.PricebookEntry;
import com.sforce.soap.enterprise.sobject.Product2;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author VijayKalyan
 */
public class ProductsHelper {

    public static String getProductList() {
        try {
            EnterpriseConnection conn = ServiceLocationsHelper.getSoapConnection();

            String query = "SELECT Id,Name,Pricebook2Id,Product2Id FROM PricebookEntry "
                    + "where Pricebook2Id='01s410000060yT5AAI'";

            QueryResult qResult = conn.query(query);
            SObject[] pbEntries = qResult.getRecords();
            return buildJson(pbEntries).toString(2);
        } catch (ConnectionException | JSONException ex) {
            return null;
        }
    }

    public static JSONObject buildJson(SObject[] pbEntries) {

        try {
            JSONObject retobj = new JSONObject();
            retobj = retobj.put("totalSize", pbEntries.length);

            JSONObject[] pbArr = new JSONObject[pbEntries.length];
            String[] ids = new String[pbEntries.length];
            HashMap<String, String> family;

            for (int iter = 0; iter < pbEntries.length; iter++) {
                PricebookEntry pb = (PricebookEntry) pbEntries[iter];
                ids[iter] = pb.getProduct2Id();
            }

            family = getProductFamily(ids);

            for (int i = 0; i < pbEntries.length; i++) {
                PricebookEntry pb = (PricebookEntry) pbEntries[i];

                JSONObject temp = new JSONObject();
                temp = temp.put("Id", pb.getId());
                temp = temp.put("Name", pb.getName());
                temp = temp.put("Pricebook2Id", pb.getPricebook2Id());
                temp = temp.put("Product2Id", pb.getProduct2Id());
                temp = temp.put("Family", family.get(pb.getProduct2Id()));
                pbArr[i] = temp;
            }
            retobj = retobj.put("records", pbArr);
//            System.out.println(retobj.toString(2));
            return retobj;
        } catch (JSONException e) {
            return null;
        }
    }

    public static HashMap<String, String> getProductFamily(String[] id) {
        try {
            EnterpriseConnection conn = ServiceLocationsHelper.getSoapConnection();
            SObject[] sObjects = conn.retrieve("ID, Family",
                    "Product2", id);
            HashMap<String, String> family = new HashMap<>();

            if (sObjects != null) {
                for (SObject sObject : sObjects) {
                    Product2 prod = (Product2) sObject;
                    if (prod != null) {
                        family.put(prod.getId(), prod.getFamily());
                    }
                }
            }

            return family;
        } catch (ConnectionException ex) {
            return null;
        }
    }
}
