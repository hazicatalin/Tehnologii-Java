/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appinterface;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author hazi_
 */
public class Document {
    private String name;
    private Date endDate;
    private int id, userID, courseID;
    private boolean homework;

    public Document(String name, int userID, int courseID, Date endDate, int id) {
        this.name = name;
        this.userID = userID;
        this.courseID = courseID;
        this.endDate = endDate;
        this.id = id;
        this.homework = true;
    }

    public Document(String name, int id, int userID, int courseID) {
        this.name = name;
        this.id = id;
        this.userID = userID;
        this.courseID = courseID;
        this.homework = false;
    }
    
    public String getName() {
        return name;
    }

    public String getCourseName() {
         JSONArray jsonArray;
         Course course = null;
        try {
            jsonArray = new JSONArray();
            OkHttpClient httpClient = new OkHttpClient();
            HttpUrl url = HttpUrl.parse("HTTP://localhost:9080/data/db/get/course").newBuilder()
                .addQueryParameter("courseID", String.valueOf(courseID))
                .build();

            Request request = new Request.Builder()
               .url(url)
               .build();
            Response response = httpClient.newCall(request).execute();
            String str = response.body().string();
            System.out.println("hello: "+str);
            jsonArray = new JSONArray(str);  
                JSONObject jsonLineItem = (JSONObject) jsonArray.get(0);
                Course c = new Course(jsonLineItem.getInt("id"), jsonLineItem.getInt("credits"), jsonLineItem.getInt("year"), jsonLineItem.getString("name"));
                course = c;   
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        return course.getName();
    }
    
    public int getUserID() {
        return userID;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getStringDate(){
        return endDate.toString();
    }
    
    public Date getEndDate() {
        return endDate;
    }

    public int getId() {
        return id;
    }

    public boolean isHomework() {
        return homework;
    }
}
