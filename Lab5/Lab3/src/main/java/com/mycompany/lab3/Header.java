package com.mycompany.lab3;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

@ManagedBean(name="head")
@SessionScoped
public class Header {

    public Header() {
    }
    public String exams(){
        return "examAdd";
    }
    public String students(){
        return "studentAdd";
    }
    public String timetable(){
        return "timetable";
    }
    public String allStudents(){
        return "dataView";
    }
    public String edit(){
        return "modifyExam";
    }
}
