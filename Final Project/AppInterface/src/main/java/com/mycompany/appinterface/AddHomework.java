/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appinterface;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.codec.binary.Base64;
import org.primefaces.model.file.UploadedFile;

@ManagedBean(name="addHomework")
@SessionScoped
public class AddHomework {
    private String userID, courseID;
    private UploadedFile file;
    private Date date;
    public AddHomework() {
      try (InputStream input = new FileInputStream("D:\\Facultate\\Java\\AppInterface\\src\\main\\java\\META-INF\\prop.properties")) {
        Properties prop = new Properties();
        // load a properties file
        prop.load(input);
        this.courseID = prop.getProperty("courseID");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
      try (InputStream input = new FileInputStream("D:\\Facultate\\Java\\AppInterface\\src\\main\\java\\META-INF\\user.properties")) {
        Properties prop = new Properties();
        // load a properties file
        prop.load(input);
        this.userID = prop.getProperty("userID");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getCourseID() {
        return courseID;
    }

    public Date getDate() {
        return date;
    }

    public String getUserID() {
        return userID;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public String upload(){
        int year = date.getYear()+1900;
        String endDate = String.valueOf(year)+"."+date.getMonth()+"."+date.getDay();
        System.out.println("date: "+endDate);
        try {
            InputStream finput = file.getInputStream();
            byte[] imageBytes = new byte[9000];
            finput.read(imageBytes, 0, imageBytes.length);
            finput.close();
            String base64Encode = Base64.encodeBase64String(imageBytes);
            OkHttpClient httpClient = new OkHttpClient();
           
            System.out.println("date: "+base64Encode);
            HttpUrl url = HttpUrl.parse("HTTP://localhost:9080/data/db/upload").newBuilder()
                .addEncodedPathSegment(base64Encode)
                .addQueryParameter("name", file.getFileName())
                .addQueryParameter("endDate", endDate)
                .addQueryParameter("userID", userID)
                .addQueryParameter("courseID", courseID)
                .build();
            Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("text/plain"), base64Encode))
                .build();
            
            Response response = httpClient.newCall(request).execute();
            String str = response.body().string();
            if(str == "Failed")
                return "errorRegister";
        } catch (IOException ex) {
            Logger.getLogger(MyGrades.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return "page";
    }
}
