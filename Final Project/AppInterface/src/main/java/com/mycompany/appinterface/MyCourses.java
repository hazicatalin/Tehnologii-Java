/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appinterface;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

@ManagedBean(name="myCourses")
@SessionScoped
public class MyCourses {
    ArrayList<Course> courses;
    private String userID;

    public MyCourses() {
        
        try (InputStream input = new FileInputStream("D:\\Facultate\\Java\\AppInterface\\src\\main\\java\\META-INF\\user.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            this.userID = prop.getProperty("userID");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public ArrayList<Course> findAll(){
        JSONArray jsonArray;
        try {
            courses = new ArrayList();
            jsonArray = new JSONArray();
            OkHttpClient httpClient = new OkHttpClient();
            HttpUrl url = HttpUrl.parse("HTTP://localhost:9080/data/db/get/user/courses").newBuilder()
                .addQueryParameter("userID", userID)
                .build();

            Request request = new Request.Builder()
               .url(url)
               .build();
            Response response = httpClient.newCall(request).execute();
            String str = response.body().string();
            System.out.println("hello: "+str);
            jsonArray = new JSONArray(str);  
            for (int i=0;i<jsonArray.length();i++){   
                JSONObject jsonLineItem = (JSONObject) jsonArray.get(i);
                Course c = new Course(jsonLineItem.getInt("id"), jsonLineItem.getInt("credits"), jsonLineItem.getInt("year"), jsonLineItem.getString("name"));
                courses.add(c);  
            }    
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        
        return courses;
        
    }
    
    public String delete(int id){
        System.out.println("delete: "+id);
        writeToProperties(String.valueOf(id));
        return "addHomework";
    }
    
    
    public void writeToProperties(String id){
        try (OutputStream output = new FileOutputStream("D:\\Facultate\\Java\\AppInterface\\src\\main\\java\\META-INF\\prop.properties")) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("courseID", id);

            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
