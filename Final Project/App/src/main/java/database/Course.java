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
public class Course {
    public int id, credits, year;
    public String name;

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
    
    public void updateCourseName(String newName){
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            String query = "UPDATE public.\"Courses\" SET name= '"+String.valueOf(newName)+"' WHERE ID = "+String.valueOf(this.id)+";";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            stmt.close();
            conn.close();
            this.name = newName;
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    public void updateCourseCredits(int newCredits){
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            String query = "UPDATE public.\"Courses\" SET credits= "+String.valueOf(newCredits)+" WHERE \"ID\" = "+String.valueOf(this.id)+";";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            stmt.close();
            conn.close();
            this.credits = newCredits;
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    public void updateCourseYear(int newYear){
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            String query = "UPDATE public.\"Courses\" SET year= "+String.valueOf(newYear)+" WHERE \"ID\" = "+String.valueOf(this.id)+";";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            stmt.close();
            conn.close();
            this.year = newYear;
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    public void deleteCourse(){
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            String query = "DELETE FROM public.\"Courses\" WHERE \"ID\" = "+String.valueOf(this.id)+";";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            stmt.close();
            conn.close();
            this.id = 0;
            this.credits = 0;
            this.year = 0;
            this.name = null;
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
      
    public ArrayList<Grade> getGrades(){
        ArrayList<Grade> userGrades = new ArrayList();
        String query = "select * from public.\"Catalog\" where courseID="+String.valueOf(this.id);
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
}
