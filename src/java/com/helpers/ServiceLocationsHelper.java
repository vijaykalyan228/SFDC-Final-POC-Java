/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helpers;

import static com.helpers.AuthenticateAuthorizeTest.SOAP_ENDPOINT;
import static com.helpers.AuthenticateAuthorizeTest.accessToken;
import static com.helpers.AuthenticateAuthorizeTest.baseUri;
import static com.helpers.AuthenticateAuthorizeTest.loginInstanceUrl;
import static com.helpers.AuthenticateAuthorizeTest.oauthHeader;
import static com.helpers.AuthenticateAuthorizeTest.prettyPrintHeader;
import com.sforce.ws.ConnectorConfig;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.SearchRecord;
import com.sforce.soap.enterprise.SearchResult;
import com.sforce.soap.enterprise.sobject.Pricesenz__ServLocOppJn__c;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.ws.ConnectionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import org.apache.http.ParseException;
import org.json.JSONException;

/**
 *
 * @author Vijay Kalyan
 */
public class ServiceLocationsHelper {

    public static String getLocations(String opportunityId) throws Exception {
        String accountId = getAccountId(opportunityId);

        final String soql = "SELECT+Id,Name,pricesenz__Account__c,pricesenz__Door_Number__c,pricesenz__Street_Name__c,pricesenz__City__c,pricesenz__State__c+FROM+pricesenz__Service_Location__c+where+pricesenz__Account__c+=+'" + accountId + "'";

        HttpClient httpClient = HttpClientBuilder.create().build();
        if (loginInstanceUrl == null) {
            try {
                LoginHelper.getAccessToken("");
            } catch (Exception ex) {
            }
        }
        baseUri = loginInstanceUrl + SOAP_ENDPOINT;
        oauthHeader = new BasicHeader("Authorization", "Bearer " + accessToken);

        String uri = baseUri + "/query?q=" + soql;
//        System.out.println("Query URL: " + uri);
        HttpGet httpGet = new HttpGet(uri);
        httpGet.addHeader(oauthHeader);
        httpGet.addHeader(prettyPrintHeader);

        // Make the request.
        HttpResponse httpResponse = httpClient.execute(httpGet);
        String getResponse = EntityUtils.toString(httpResponse.getEntity());
//        System.out.println("Query Result:\n" + getResponse);
        return getResponse;
    }

    private static String getAccountId(String opportunityID) {
//        System.out.println(opportunityID);
        String uri = AuthenticateAuthorizeTest.baseUri + "/sobjects/Opportunity/" + opportunityID;
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet(uri);
//            System.out.println("oauthHeader2: " + oauthHeader);
            httpGet.addHeader(AuthenticateAuthorizeTest.oauthHeader);
            httpGet.addHeader(AuthenticateAuthorizeTest.prettyPrintHeader);

            // Make the request.
            HttpResponse response = httpClient.execute(httpGet);

            String getResponse = EntityUtils.toString(response.getEntity());
//            System.out.println("Query Result:\n" + getResponse);

            return new JSONObject(getResponse).getString("AccountId");
        } catch (IOException | ParseException | JSONException e) {
        }
        return "";
    }

    public static void addLocationToOpportunity(String opportunityID, String locationID) throws ConnectionException {
        System.out.println("addLocationToOpportunity\t" + opportunityID + "\t" + locationID);

        String tempArr[] = locationID.split(Pattern.quote("***"));
        String addLocations = tempArr[0];
        String deleteLocations = tempArr.length > 1 ? tempArr[1] : null;

        StringTokenizer st = new StringTokenizer(addLocations, ",");

        List<Pricesenz__ServLocOppJn__c> locations = getLocationsForOpportunity(opportunityID);

        ArrayList<Pricesenz__ServLocOppJn__c> addList = new ArrayList<>();
        ArrayList<Pricesenz__ServLocOppJn__c> deleteList = new ArrayList<>();

        Pricesenz__ServLocOppJn__c tempJnObj;

        while (st.hasMoreTokens()) {
            boolean flag = true;
            String tempID = st.nextToken();
            for (Pricesenz__ServLocOppJn__c l : locations) {
                if (l.getId().equals(tempID)) {
//                    System.out.println(tempID);
                    flag = false;
                    break;
                }
            }
            if (flag) {
                tempJnObj = new Pricesenz__ServLocOppJn__c();
                tempJnObj.setPricesenz__Service_Location__c(tempID);
                tempJnObj.setPricesenz__Opportunity__c(opportunityID);
                addList.add(tempJnObj);
            }
        }
    }

    public static EnterpriseConnection getSoapConnection() {
        ConnectorConfig sfconfig = new ConnectorConfig();
//        System.out.println("AccessToken:\t"+accessToken);
        sfconfig.setSessionId(accessToken);
        sfconfig.setServiceEndpoint(loginInstanceUrl + SOAP_ENDPOINT);
        System.out.println("loginInstanceUrl:\t"+loginInstanceUrl);
        EnterpriseConnection con = null;
        try {
            con = Connector.newConnection(sfconfig);
        } catch (ConnectionException e) {
            System.out.println("Exception in connection creation");
        }
        System.out.println("Auth EndPoint: " + sfconfig.getAuthEndpoint());
        System.out.println("Service End Point: " + sfconfig.getServiceEndpoint());
        return con;
    }

    //Pricesenz__Service_Location__c[]
    private static List<Pricesenz__ServLocOppJn__c> getLocationsForOpportunity(String opportunityID) throws ConnectionException {
        EnterpriseConnection con = getSoapConnection();

//        String query = "SELECT+Id,pricesenz__Service_Location__c,pricesenz__Opportunity__c+FROM+pricesenz__ServLocOppJn__c+WHERE+pricesenz__Opportunity__c+=+'" + opportunityID + "'";
        String query = "FIND {\"" + opportunityID + "\" IN pricesenz__Opportunity__c FIELDS "
                + "RETURNING"
                + "pricesenz__ServLocOppJn__c(Id,pricesenz__Service_Location__c,pricesenz__Opportunity__c)";

        SearchResult sResult = con.search(query);
        SearchRecord[] records = sResult.getSearchRecords();

        List<Pricesenz__ServLocOppJn__c> locations = new ArrayList<>();

        if (records != null && records.length > 0) {
            for (SearchRecord record1 : records) {
                SObject record = record1.getRecord();
                if (record.getClass() == Pricesenz__ServLocOppJn__c.class) {
                    locations.add((Pricesenz__ServLocOppJn__c) record);
                }
            }
        }

        return locations;
    }
}
