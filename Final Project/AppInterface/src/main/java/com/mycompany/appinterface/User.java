/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appinterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author hazi_
 */
public class User {
    private final int id;
    private String[] courses;
    private String email, role, password, name;
    
    public User(String email, String role, String password, int id, String coursesIDs, String name){
        this.courses = coursesIDs.split(";");
        this.email = email;
        this.role = role;
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public User(String email, String role, int id, String coursesIDs, String name){
        this.courses = coursesIDs.split(";");
        this.email = email;
        this.role = role;
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String[] getCourses() {
        return courses;
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
}
