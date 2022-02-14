/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appinterface;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
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

@ManagedBean(name="addGrade")
@SessionScoped
public class AddGrade {
    private String userID;
    private int studentID, courseID, grade; 
    public AddGrade() {
        try (InputStream input = new FileInputStream("D:\\Facultate\\Java\\AppInterface\\src\\main\\java\\META-INF\\prop.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            this.userID = prop.getProperty("userID");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getUserID() {
        return userID;
    }

    public int getStudentID() {
        return studentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public int getGrade() {
        return grade;
    }
    
    public String submit(){
        System.out.println("hello: "+String.valueOf(this.studentID)+";"+userID);
        if(this.studentID==0 || this.courseID==0 || this.grade <1 ||this.grade > 10)
            return "addGrade";
        try {
            OkHttpClient httpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                .build();
            HttpUrl url = HttpUrl.parse("HTTP://localhost:9080/data/db/add/grade").newBuilder()
                .addQueryParameter("studentID", String.valueOf(this.studentID))
                .addQueryParameter("courseID", String.valueOf(this.courseID))
                .addQueryParameter("grade", String.valueOf(this.grade))
                .addQueryParameter("teacherID", String.valueOf(this.userID))
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
        return "page";
    }
}
