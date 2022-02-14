/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appinterface;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

@ManagedBean(name="myHomeworks")
@SessionScoped
public class MyHomeworks {
    
    private String userID;

    public MyHomeworks() {
        
        try (InputStream input = new FileInputStream("D:\\Facultate\\Java\\AppInterface\\src\\main\\java\\META-INF\\user.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            this.userID = prop.getProperty("userID");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    ArrayList<Document> docs;
    public ArrayList<Document> findAll(){
        JSONArray jsonArray;
        try {
            docs = new ArrayList();
            jsonArray = new JSONArray();
            OkHttpClient httpClient = new OkHttpClient();
            HttpUrl url = HttpUrl.parse("HTTP://localhost:9080/data/db/get/student/documents").newBuilder()
                .addQueryParameter("userID", userID)
                .build();

            Request request = new Request.Builder()
               .url(url)
               .build();
            Response response = httpClient.newCall(request).execute();
            String str = response.body().string();
            System.out.println("hello: "+str);
            jsonArray = new JSONArray(str);  
              
                //Iterating JSON array  
            for (int i=0;i<jsonArray.length();i++){   
                JSONObject jsonLineItem = (JSONObject) jsonArray.get(i);
                Date date1=new SimpleDateFormat("yyyy-mm-dd").parse(jsonLineItem.getString("endDate"));  
                Document c = new Document(jsonLineItem.getString("name"), jsonLineItem.getInt("userID"), jsonLineItem.getInt("courseID"), date1, jsonLineItem.getInt("id"));
                docs.add(c);  
            }    
        } catch (IOException ex) {
            Logger.getLogger(MyGrades.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(MyHomeworks.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return docs;
        
    }
}
