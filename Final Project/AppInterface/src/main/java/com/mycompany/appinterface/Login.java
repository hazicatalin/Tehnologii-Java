/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appinterface;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
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

@ManagedBean(name="login")
@SessionScoped
public class Login {
    private String password;    
    private String email;    
    public String getPassword() {  
        return password;  
    }  
    public void setPassword(String password) {  
        this.password = password;  
    }  
    public String getEmail() {  
        return email;  
    }  
    public void setEmail(String email) {  
        this.email = email;  
    }  
    
    public String doLogin(){
        if(email=="" || password=="")
            return "errorLogin";
        password = Base64.getEncoder().encodeToString(password.getBytes());
        
        ArrayList<User> user = new ArrayList();
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray();
            OkHttpClient httpClient = new OkHttpClient();
            HttpUrl url = HttpUrl.parse("HTTP://localhost:9080/data/db/login").newBuilder()
                .addQueryParameter("email", this.email)
                .addQueryParameter("password", this.password)
                .build();

            Request request = new Request.Builder()
               .url(url)
               .build();
            Response response = httpClient.newCall(request).execute();
            String str = response.body().string();
            jsonArray = new JSONArray(str);  
            if(jsonArray.length()==0)
                return "errorLogin";
                //Iterating JSON array   
                JSONObject jsonLineItem = (JSONObject) jsonArray.get(0);
                User c = new User(jsonLineItem.getString("email"), jsonLineItem.getString("role"), jsonLineItem.getInt("id"),jsonLineItem.getString("courses"), jsonLineItem.getString("name"));
                user.add(c);  
                writeToProperties(c.getRole(), String.valueOf(c.getId()));
        } catch (IOException ex) {
            Logger.getLogger(MyGrades.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "page";
    }
    
    public void writeToProperties(String role, String id){
        try (OutputStream output = new FileOutputStream("D:\\Facultate\\Java\\AppInterface\\src\\main\\java\\META-INF\\user.properties")) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("userRole", role);
            prop.setProperty("userID", id);

            // save properties to project root folder
            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
    
    public String doRegister(){
        return "register";
    }
    
    public String doReturn(){
        return "login";
    }
}
