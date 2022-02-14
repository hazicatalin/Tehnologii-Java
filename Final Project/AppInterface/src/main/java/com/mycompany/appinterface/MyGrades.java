/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appinterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import org.json.JSONArray;
import org.json.JSONObject;

@ManagedBean(name="myGrades")
@SessionScoped
public class MyGrades {
    private String userID;

    public MyGrades() {
        
        try (InputStream input = new FileInputStream("D:\\Facultate\\Java\\AppInterface\\src\\main\\java\\META-INF\\user.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            this.userID = prop.getProperty("userID");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    ArrayList<Grade> grades;
    public ArrayList<Grade> findAll(){
        int id = 1;
        JSONArray jsonArray;
        try {
            grades = new ArrayList();
            jsonArray = new JSONArray();
            OkHttpClient httpClient = new OkHttpClient();
            HttpUrl url = HttpUrl.parse("HTTP://localhost:9080/data/db/get/student/grades").newBuilder()
                .addQueryParameter("studentID", userID)
                .build();

            Request request = new Request.Builder()
               .url(url)
               .build();
            Response response = httpClient.newCall(request).execute();
            String str = response.body().string();
            System.out.print("aici: "+str);
            jsonArray = new JSONArray(str);  
              
                //Iterating JSON array  
            for (int i=0;i<jsonArray.length();i++){   
                JSONObject jsonLineItem = (JSONObject) jsonArray.get(i);
                Grade c = new Grade(jsonLineItem.getInt("studentID"), jsonLineItem.getInt("courseID"), jsonLineItem.getInt("teacherID"), jsonLineItem.getInt("grade"));
                grades.add(c);  
            }    
        } catch (IOException ex) {
            System.out.print("aici: error"+ex.toString());
            Logger.getLogger(MyGrades.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return grades;
        
    }
}
