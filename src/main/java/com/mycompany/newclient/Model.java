/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.newclient;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author thainguyen
 */
public class Model {

    public boolean sendTruck(String id) {
        boolean postSuccessfully = false;
        try {
            String uri = "http://localhost:8080/newWebServer-1.0/webresources/firetrucks/send-truck";

            HttpClient client = new DefaultHttpClient();

            HttpPost post;

            ArrayList<NameValuePair> postParameters;
            post = new HttpPost(uri);

            postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("id", id));

            post.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

            HttpResponse response = client.execute(post);
            
            System.out.println(response.getStatusLine().getStatusCode());

            if (response.getStatusLine().getStatusCode() == 201) {
                postSuccessfully = true;
            }

//            HttpEntity entity = response.getEntity();
//
//            String string = EntityUtils.toString(entity, "UTF-8");
//
//            System.out.println(string);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(NewClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        return postSuccessfully;
    }

    public List getFire() {
        String uri = "http://localhost:8080/newWebServer-1.0/webresources/fire/get-fire";
        List<String> ids = new ArrayList();
        try {

            HttpGet httpGet = new HttpGet(uri);

            HttpClient hc = new DefaultHttpClient();

            HttpResponse httpResponse = hc.execute(httpGet);

            HttpEntity entity = httpResponse.getEntity();

            String jsonString = EntityUtils.toString(entity, "UTF-8");

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<String>>() {
            }.getType();
            ids = new Gson().fromJson(jsonString, listType);
            

        } catch (IOException ex) {

            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ids;
    }

    public String getReport() {
        String uri = "http://localhost:8080/newWebServer-1.0/webresources/fire/get-fire";

        try {

            HttpGet httpGet = new HttpGet(uri);

            HttpClient hc = new DefaultHttpClient();

            HttpResponse httpResponse = hc.execute(httpGet);

            HttpEntity entity = httpResponse.getEntity();

            String jsonString = EntityUtils.toString(entity, "UTF-8");

            JsonParser parser = new JsonParser();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            JsonElement el = parser.parse(jsonString);
            jsonString = gson.toJson(el);
            return jsonString;
        } catch (IOException ex) {

            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            return "unable to retrieve report, please try again";
        }

    }

}
