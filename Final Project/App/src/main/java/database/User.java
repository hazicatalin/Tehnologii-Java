/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

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
    private int id;
    private String courses;
    private String email, role, password, name;

    public User() {
    }
    
    public User(String email, String role, String password, int id, String coursesIDs, String name){
        this.courses = coursesIDs;
        this.email = email;
        this.role = role;
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public String[] getUserCourses(){
        return this.courses.split(";");
    }
    
    public int getId() {
        return id;
    }

    public String getCourses() {
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
    
    public ArrayList<Grade> getGrades(){
        ArrayList<Grade> userGrades = new ArrayList();
        String query = "select * from public.\"Catalog\" where studentID="+String.valueOf(this.id);
        try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                if (rs.next()) {
                    Grade grade = new Grade(rs.getInt("studentID"), rs.getInt("courseID"), rs.getInt("teacherID"), rs.getInt("grade"));
                    userGrades.add(grade);
                }
                st.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            } catch (ClassNotFoundException ex) {
               System.out.println(ex.toString());
            }        
        return userGrades;
    }
    
    public void addCourse(int courseID){
        String query = "select * from public.\"Users\" where \"ID\"="+String.valueOf(this.id)+";";
        String courses = null;
        try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                if (rs.next()) {
                    courses = rs.getString("courses");
                }
                st.close();
                con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
        
        courses = courses + String.valueOf(courseID) + ";";
        
        query = "UPDATE public.\"Users\" SET courses='"+courses+"' WHERE \"ID\"="+String.valueOf(this.id)+";";
        try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
                Statement st = con.createStatement();
                st.executeQuery(query);
                st.close();
                con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
    }
       
    
    public void removeCourse(int courseID){
        String query = "select * from public.\"Users\" where \"ID\"="+String.valueOf(this.id)+";";
        String courses = null;
        try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                if (rs.next()) {
                    courses = rs.getString("courses");
                }
                st.close();
                con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
        
        courses = courses.replace(String.valueOf(courseID)+";", "");
        
        query = "UPDATE public.\"Users\" SET courses='"+courses+"' WHERE \"ID\"="+String.valueOf(this.id)+";";
        try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
                Statement st = con.createStatement();
                st.executeQuery(query);
                st.close();
                con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
    }
    
    public void deleteUser(){
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            String query = "DELETE FROM public.\"Users\" WHERE \"ID\" = "+String.valueOf(this.id)+";";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            stmt.close();
            conn.close();
            this.courses = null;
            this.email = null;
            this.role = null;
            this.password = null;
            this.name = null;
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
}
