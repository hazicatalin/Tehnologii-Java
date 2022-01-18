package com.mycompany.lab3;
        
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

@ManagedBean(name="modifyExam")
@SessionScoped
public class ModifyExam {
    private LocalTime startingTime;
    private LocalTime duration;
    private String exam;
    ArrayList<String> exams = new ArrayList<String>();

    public String getExam() {
        return exam;
    }

    public ModifyExam() {
    }
    
    public ModifyExam(String name, LocalTime startingTime, LocalTime duration){
        this.exam=name;
        this.duration = duration;
        this.startingTime = startingTime;
    }

    public ArrayList<String> getExams() {
        this.exams.clear();
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/timetable", "postgres", "root");
            String query = "SELECT * FROM exam";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next())
            {
              String str = rs.getString("name");
                exams.add(str);
            }
            stmt.close();
            rs.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentAdd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StudentAdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exams;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public void setExams(ArrayList<String> exams) {
        this.exams = exams;
    }

    public void setStartingTime(LocalTime startingTime) {
        this.startingTime = startingTime;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }

    public LocalTime getStartingTime() {
        return startingTime;
    }

    public LocalTime getDuration() {
        return duration;
    }
    
    public String update(){
        String query = "update exam set starting_time = '"+this.startingTime+"', duration = '"+this.duration+"' where name='"+this.exam+"'";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/timetable", "postgres", "root");
            Statement st = con.createStatement();
            st.executeUpdate(query);
            st.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExamAdd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ExamAdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.exam = null;
        this.duration = null;
        this.startingTime = null;
        this.exams.clear();
        return "startPage";
    }
    
}
