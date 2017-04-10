package com.helpers;

import static com.helpers.AuthenticateAuthorizeTest.accessToken;
import static com.helpers.AuthenticateAuthorizeTest.loginInstanceUrl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 *
 * @author admin
 */
public class LoginHelper {

    private static String accessCode = "";

    public static String getAccessToken(String code) throws Exception {

        if (code == "" || code == null) {
            code = accessCode;
        } else {
            accessCode = code;
        }

        ResourceBundle bundle = ResourceBundle.getBundle("config");

        String token = bundle.getString("token");
        String clientId = bundle.getString("clientId");
        String client_secret = bundle.getString("clientSecret");
        String redirectUri = bundle.getString("redirectUri");

//        System.out.println(token);
        String baseUrl = "https://login.salesforce.com/services/oauth2/token";

        HttpClient httpClient = new DefaultHttpClient();
        HttpParams params = httpClient.getParams();
//        HttpClientParams.setCookiePolicy(params, CookiePolicy.RFC_2109);
//        params.setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 30000);

        HttpPost post = new HttpPost(baseUrl);

        List<BasicNameValuePair> parametersBody = new ArrayList<>();

//        parametersBody.add(new BasicNameValuePair("grant_type", "password"));
        parametersBody.add(new BasicNameValuePair("grant_type", "authorization_code"));
        parametersBody.add(new BasicNameValuePair("client_id", clientId));
        parametersBody.add(new BasicNameValuePair("client_secret", client_secret));
        parametersBody.add(new BasicNameValuePair("code", code));
//        parametersBody.add(new BasicNameValuePair("username", uname));
//        parametersBody.add(new BasicNameValuePair("password", passd+token));
        parametersBody.add(new BasicNameValuePair("redirect_uri", redirectUri));
        parametersBody.add(new BasicNameValuePair("Content-Type", "application/x-www-form-urlencoded"));

        post.setEntity(new UrlEncodedFormEntity(parametersBody, HTTP.UTF_8));

        HttpResponse response = httpClient.execute(post);

        Map<String, Object> oauthResponse = new JSONObject(EntityUtils.toString(response.getEntity())).toMap();
//        System.out.println("OAuth login response");
//        for (Map.Entry<String, Object> entry : oauthResponse.entrySet())
//        {
//            System.out.println(String.format("  %s = %s", entry.getKey(), entry.getValue()));
//        }
//        System.out.println("");

        String temp = (String) oauthResponse.get("id");
        String array[] = temp.split("/");
        AuthenticateAuthorizeTest.UserId = array[array.length - 1];
//        System.out.println("User ID:\t"+AuthenticateAuthorizeTest.UserId);

        loginInstanceUrl = (String) oauthResponse.get("instance_url");
//        System.out.println("Instance URI:\t"+loginInstanceUrl);

        String authToken = (String) oauthResponse.get("access_token");
        AuthenticateAuthorizeTest.accessToken = authToken;
        return accessToken;
    }

}
