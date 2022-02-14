/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author hazi_
 */
public class Grade {
    private int studentID, courseID, teacherID, grade;

    public Grade(int studentID, int courseID, int teacherID, int grade) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.teacherID = teacherID;
        this.grade = grade;
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
    
    public void updateGrade(int newGrade){
        try{
            int id;
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            String query = "UPDATE public.\"Catalog\" SET grade= "+String.valueOf(newGrade)+" WHERE \"studentID\" = "+String.valueOf(this.studentID)+" AND \"courseID\" = "+String.valueOf(this.courseID)+";";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            stmt.close();
            conn.close();
            this.grade = newGrade;
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    public void deleteGrade(){
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            String query = "DELETE FROM public.\"Catalog\" WHERE \"studentID\" = "+String.valueOf(this.studentID)+" AND \"courseID\" = "+String.valueOf(this.courseID)+";";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            stmt.close();
            conn.close();
            this.studentID = 0;
            this.courseID = 0;
            this.grade = 0;
            this.teacherID = 0;
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
}
