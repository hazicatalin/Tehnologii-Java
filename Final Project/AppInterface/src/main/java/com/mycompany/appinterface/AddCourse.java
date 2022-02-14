/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appinterface;

import java.io.IOException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@ManagedBean(name="addCourse")
@SessionScoped
public class AddCourse {
    private int credits, year;
    private String name;

    public AddCourse() {
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public int getYear() {
        return year;
    }

    public String getName() {
        return name;
    }
    
    public String submit(){
        if(!this.name.equals("")){
            try {
                System.out.println("hello:"+name+"; ");
                OkHttpClient httpClient = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder()
                    .build();
                HttpUrl url = HttpUrl.parse("HTTP://localhost:9080/data/db/add/course").newBuilder()
                    .addQueryParameter("name", this.name)
                    .addQueryParameter("credits", String.valueOf(this.credits))
                    .addQueryParameter("year", String.valueOf(this.year))
                    .build();

                Request request = new Request.Builder()
                   .url(url)
                   .post(formBody)
                   .build();
                Response response = httpClient.newCall(request).execute();
                String str = response.body().string();
                if(str == "Failed")
                    return "errorRegister";
            } catch (IOException ex) {
                Logger.getLogger(MyGrades.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return "page";
    }
}
