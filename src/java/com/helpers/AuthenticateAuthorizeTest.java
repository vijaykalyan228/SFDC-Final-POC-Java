package com.helpers;

import java.net.URI;
import java.util.ResourceBundle;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 *
 * @author VijayKalyan
 */
public class AuthenticateAuthorizeTest {

    public static String accessToken = "";
    public static String opportunityID = "";
    public static String accountID = "";
    public static String accountName = "";
    public static String baseUri;
    public static final String REST_ENDPOINT = "/services/data";
    public static final String SOAP_ENDPOINT = "/services/Soap/c/39.0";
    public static final String API_VERSION = "/v32.0";
    public static String loginInstanceUrl = "";
    public static Header oauthHeader;
    public static Header prettyPrintHeader = new BasicHeader("X-PrettyPrint", "1");
    public static String UserId = "";

    public static URI getURI() throws Exception {

        ResourceBundle bundle = ResourceBundle.getBundle("config");

        String clientId = bundle.getString("clientId");
        String client_secret = bundle.getString("clientSecret");
        String redirectUri = bundle.getString("redirectUri");

//        System.out.println(token);
        String baseUrl = "https://login.salesforce.com/services/oauth2/token";
        String authorizeUrl = "https://login.salesforce.com/services/oauth2/authorize";

        HttpClient httpClient = new DefaultHttpClient();
        HttpParams params = httpClient.getParams();
        HttpClientParams.setCookiePolicy(params, CookiePolicy.RFC_2109);
        params.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 30000);

        URIBuilder builder = new URIBuilder();
        builder.setScheme("https").setHost("login.salesforce.com").setPath("/services/oauth2/authorize")
                .setParameter("response_type", "code")
                .setParameter("client_id", clientId)
                .setParameter("redirect_uri", redirectUri);
//        System.out.println(redirectUri);
        URI uri = builder.build();
        return uri;
    }

    public static String getAccessToken(String code) throws Exception {
        return LoginHelper.getAccessToken(code);
    }

    public static String getOpportunities() {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();

            baseUri = loginInstanceUrl + REST_ENDPOINT + API_VERSION;
            oauthHeader = new BasicHeader("Authorization", "Bearer " + accessToken);

            String uri = baseUri + "/query?q=select+name,id+from+opportunity+where+IsClosed=FALSE+and+OwnerId='" + UserId + "'+order+by+CreatedDate+desc";
            System.out.println("Query URL: " + uri);
            HttpGet httpGet = new HttpGet(uri);
//            System.out.println("oauthHeader2: " + oauthHeader);
            httpGet.addHeader(oauthHeader);
            httpGet.addHeader(prettyPrintHeader);

            // Make the request.
            HttpResponse response = httpClient.execute(httpGet);
//            System.out.println("Query Result:\n" + EntityUtils.toString(response.getEntity()));
            return new JSONObject(EntityUtils.toString(response.getEntity())).toString(2);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteRecord(String id) throws Exception {
        String uri = baseUri + "/sobjects/Opportunity/" + opportunityID;
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();

            HttpDelete httpDelete = new HttpDelete(uri);
            httpDelete.addHeader(oauthHeader);
            httpDelete.addHeader(prettyPrintHeader);

            //Make the request
            HttpResponse response = httpClient.execute(httpDelete);

            //Process the response
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 204) {
                System.out.println("Deleted the lead successfully.");
            } else {
                System.out.println("Error deleting opportunity. Status code is " + statusCode);
            }
        } catch (Exception e) {
            System.out.println("Issue creating JSON or processing results");
        }

        return true;
    }

    public static boolean updateRESTAmount(double amount) throws Exception {

        String uri = baseUri + "/sobjects/Opportunity/" + opportunityID;
        try {
            //Create the JSON object containing the updated lead last name
            //and the id of the lead we are updating.
            JSONObject jsonOpp = new JSONObject();
            jsonOpp.put("Amount", amount);
            System.out.println("JSON for updated Opportunity:\n" + jsonOpp.toString(2));

            //Set up the objects necessary to make the request.
            //DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpClient httpClient = HttpClientBuilder.create().build();

            HttpPatch httpPatch = new HttpPatch(uri);
            httpPatch.addHeader(oauthHeader);
            httpPatch.addHeader(prettyPrintHeader);
            StringEntity body = new StringEntity(jsonOpp.toString(1));
            body.setContentType("application/json");
            httpPatch.setEntity(body);

            //Make the request
            HttpResponse response = httpClient.execute(httpPatch);

            //Process the response
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 204) {
                System.out.println("Updated the Opportunity successfully.");
            } else {
                System.out.println("Error updating Opportunity:\t" + statusCode);
            }
        } catch (Exception e) {
            System.out.println("Issue creating JSON or processing results");
        }

        return true;
    }

    public static String getServiceLocations() {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();

            baseUri = loginInstanceUrl + REST_ENDPOINT + API_VERSION;// + "/sobjects/pricesenz__Service_Location__c";
            oauthHeader = new BasicHeader("Authorization", "Bearer " + accessToken);

            String uri = baseUri + "/query?q=select"
                    + "+Name"
                    + ",pricesenz__Account__c"
                    + ",pricesenz__Door_Number__c"
                    + ",pricesenz__Street_Name__c"
                    + ",pricesenz__City__c"
                    + ",pricesenz__State__c"
                    + "+from+pricesenz__Service_Location__c";
            System.out.println("Query URL: " + uri);
            HttpGet httpGet = new HttpGet(uri);
//            System.out.println("oauthHeader2: " + oauthHeader);
            httpGet.addHeader(oauthHeader);
            httpGet.addHeader(prettyPrintHeader);

            // Make the request.
            HttpResponse response = httpClient.execute(httpGet);

            String getResponse = EntityUtils.toString(response.getEntity());
            System.out.println("Query Result:\n" + getResponse);

            return new JSONObject(getResponse).toString(2);
        } catch (Exception e) {
        }
        return null;
    }
    
    
}
