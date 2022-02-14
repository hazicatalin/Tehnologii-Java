/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.appinterface;

/**
 *
 * @author hazi_
 */
public class Course {
    private int id, credits, year;
    private String name;

    public Course(int id, int credits, int year, String name) {
        this.id = id;
        this.credits = credits;
        this.year = year;
        this.name = name;
    }

    public int getId() {
        return id;
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
    
}
