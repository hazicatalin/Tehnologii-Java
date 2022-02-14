/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appinterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
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
import org.json.JSONArray;
import org.json.JSONObject;

@ManagedBean(name="register")
@SessionScoped
public class Register {
    private Map<String, String> roles;
    private String password;  
    private String confirmPassword;    
    private String email;   
    private String role; 
    private String name;

    public Register() {
        roles = new HashMap<>();
        roles.put("teacher", "teacher");
        roles.put("student", "student");
    }

    public Map<String, String> getRoles() {
        return roles;
    }
    
    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public String doRegister(){
        if(email=="" || password=="" || !password.equals(confirmPassword)){
            return "errorRegister";}
        password = Base64.getEncoder().encodeToString(password.getBytes());
        
        try {
            OkHttpClient httpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                .build();
            HttpUrl url = HttpUrl.parse("HTTP://localhost:9080/data/db/add/user").newBuilder()
                .addQueryParameter("email", this.email)
                .addQueryParameter("password", this.password)
                .addQueryParameter("name", this.name)
                .addQueryParameter("role", this.role)
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
        
        return "login";
    }
    
    public String doLorin(){
        return "login";
    }
    
    public String doReturn(){
        return "register";
    }
}
