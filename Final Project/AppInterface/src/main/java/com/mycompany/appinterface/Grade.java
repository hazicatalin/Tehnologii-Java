/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appinterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Grade{
    private int studentID, courseID, teacherID, grade;
    private User student;
    private Course course;
    private User teacher;

    public Grade(int studentID, int courseID, int teacherID, int grade) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.teacherID = teacherID;
        this.grade = grade;
        readStudent();
        readTeacher();
        readCourse();
    }

    public int getStudentID() {
        return studentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public int getGrade() {
        return grade;
    }
    
    public String getStudentName(){
        return student.getName();
    }
    
    public String getTeacherName(){
        return teacher.getName();
    }
    
    public String getCourseName(){
        return course.getName();
    }
    
    private void readStudent(){
        try {
            OkHttpClient httpClient = new OkHttpClient();
            HttpUrl url = HttpUrl.parse("HTTP://localhost:9080/data/db/get/user").newBuilder()
                .addQueryParameter("userID", String.valueOf(this.studentID))
                .build();

            Request request = new Request.Builder()
               .url(url)
               .build();
            Response response = httpClient.newCall(request).execute();
            String str = response.body().string();
            System.out.print("aici: "+str); 
            JSONObject jsonLineItem = new JSONArray(str).getJSONObject(0);
            this.student = new User(jsonLineItem.getString("email"), jsonLineItem.getString("role"), jsonLineItem.getInt("id"),jsonLineItem.getString("courses"), jsonLineItem.getString("name"));
             
        } catch (IOException ex) {
            System.out.print("aici: error"+ex.toString());
            Logger.getLogger(MyGrades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void readTeacher(){
        try {
            OkHttpClient httpClient = new OkHttpClient();
            HttpUrl url = HttpUrl.parse("HTTP://localhost:9080/data/db/get/user").newBuilder()
                .addQueryParameter("userID", String.valueOf(this.teacherID))
                .build();

            Request request = new Request.Builder()
               .url(url)
               .build();
            Response response = httpClient.newCall(request).execute();
            String str = response.body().string();
            System.out.print("aici: "+str); 
            JSONObject jsonLineItem = new JSONArray(str).getJSONObject(0);
            this.teacher = new User(jsonLineItem.getString("email"), jsonLineItem.getString("role"), jsonLineItem.getInt("id"),jsonLineItem.getString("courses"), jsonLineItem.getString("name"));
             
        } catch (IOException ex) {
            System.out.print("aici: error"+ex.toString());
            Logger.getLogger(MyGrades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void readCourse(){
        try {
            OkHttpClient httpClient = new OkHttpClient();
            HttpUrl url = HttpUrl.parse("HTTP://localhost:9080/data/db/get/course").newBuilder()
                .addQueryParameter("courseID", String.valueOf(this.courseID))
                .build();

            Request request = new Request.Builder()
               .url(url)
               .build();
            Response response = httpClient.newCall(request).execute();
            String str = response.body().string();
            System.out.print("aici: "+str); 
            JSONObject jsonLineItem = new JSONArray(str).getJSONObject(0);
            this.course = new Course(jsonLineItem.getInt("id"), jsonLineItem.getInt("credits"), jsonLineItem.getInt("year"), jsonLineItem.getString("name"));
             
        } catch (IOException ex) {
            System.out.print("aici: error"+ex.toString());
            Logger.getLogger(MyGrades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
