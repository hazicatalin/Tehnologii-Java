/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.App;

import apptools.Email;
import database.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONArray;

@Path("/db")
@Singleton
public class DatabaseURLs {
    
    DBmanager database = DBmanager.getInstance();
    Email emailSender = Email.getInstance();
    
    @POST
    @Path("/upload")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String addFile(String base64Encode,
            @QueryParam("name") String name,
            @QueryParam("endDate") String endDate,
            @QueryParam("userID") String userID,
            @QueryParam("courseID") String courseID) {
        byte[] b = null;
        try {
            b = Base64.decodeBase64(new String(base64Encode).getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DatabaseURLs.class.getName()).log(Level.SEVERE, null, ex);
        }
        String path = "D:\\Facultate\\Java\\App\\Homeworks\\"+name+".pdf";
        if(Files.exists(Paths.get(path)))
            return"File already exists!";
        else{
            database.addFileToDB(name, endDate , userID, courseID);
            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(b);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DatabaseURLs.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DatabaseURLs.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "Success";
    } 
    
    @POST
    @Path("/add/course")
    public String addCourse(@QueryParam("name") String name,
            @QueryParam("credits") int credits,
            @QueryParam("year") int year) {
        try{
            database.insertCourse(name, credits, year);
        }catch(Exception ex){
            System.out.println(ex.toString());
            return "Fail";
        }
        return "Success";
    } 
    
    @POST
    @Path("/add/grade")
    public String addGrade(@QueryParam("studentID") int studentID,
            @QueryParam("courseID") int courseID,
            @QueryParam("teacherID") int teacherID,
            @QueryParam("grade") int grade) {
        try{
            database.insertGrade(studentID, courseID, teacherID, grade);
        }catch(Exception ex){
            System.out.println(ex.toString());
            return "Fail";
        }
        return "Success";
    } 
    
    @POST
    @Path("/add/user")
    @Produces(MediaType.TEXT_PLAIN)
    public String addUser(@QueryParam("email") String email,
            @QueryParam("password") String password,
            @QueryParam("name") String name,
            @QueryParam("role") String role) {
        System.out.println("register: "+email+password+role);
        try{
            emailSender.sendEmail(email, "Confirmation Email", "Account Created successfully!");
            database.insertUser(email, password, name, role, "");
        }catch(Exception ex){
            System.out.println("hihi:"+ex.toString());
            return "Fail";
        }
        return "Success";
    }
    
    @GET
    @Path("/get/user")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getUser(
            @QueryParam("userID") int userID) {
        return database.getUser(userID);
    }
    
    @GET
    @Path("/get/users")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getUsers() {
        return database.getUsers();
    }
    
    @GET
    @Path("/get/documents")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getDocuments() {
        return database.getDocuments();
    }
    
    @GET
    @Path("/get/user/documents")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getUserDocuments(
            @QueryParam("userID") int userID) {
        return database.getUserDocuments(userID);
    }
    
    @GET
    @Path("/get/student/documents")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getStudentDocuments(
            @QueryParam("userID") int userID) {
        return database.getStudentDocuments(userID);
    }
    
    @GET
    @Path("/get/course")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getCourse(
            @QueryParam("courseID") int courseID) {
        return database.getCourse(courseID);
    }
    
    @GET
    @Path("/get/courses")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getCourses() {
        return database.getCourses();
    }
    
    @GET
    @Path("/get/user/courses")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getUSerCourses(
            @QueryParam("userID") int userID)  {
        return database.getUserCourses(userID);
    }
    
    @GET
    @Path("/get/student/grades")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getStudentGrades(
            @QueryParam("studentID") int studentID) {
        return database.getStudentGrades(studentID); 
    }
    
    @GET
    @Path("/get/course/grades")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getCourseGrades(
            @QueryParam("courseID") int courseID) {
        return database.getCourseGrades(courseID);
    }
    
    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONArray getCourseGrades(
            @QueryParam("email") String email,
            @QueryParam("password") String password) {
        return database.loginUser(email, password);
    }
    
    @DELETE
    @Path("/delete/course")
    public boolean deletecourse(@QueryParam("id") int id) {
        return true;
    }
    
    
}
