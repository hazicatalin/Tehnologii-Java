/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author hazi_
 */
public class Document {
    private String name;
    private Date endDate;
    private int id, userID, courseID;
    private boolean homework;

    public Document(String name, int userID, int courseID, Date endDate, int id) {
        this.name = name;
        this.userID = userID;
        this.courseID = courseID;
        this.endDate = endDate;
        this.id = id;
        this.homework = true;
    }

    public Document(String name, int id, int userID, int courseID) {
        this.name = name;
        this.id = id;
        this.userID = userID;
        this.courseID = courseID;
        this.homework = false;
    }
    
    public String getName() {
        return name;
    }

    public int getUserID() {
        return userID;
    }

    public int getCourseID() {
        return courseID;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getId() {
        return id;
    }

    public boolean isHomework() {
        return homework;
    }
    
    public void updateDocumentName(String newName){
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            String query = "UPDATE public.\"Documents\" SET name= '"+newName+"' WHERE ID = "+String.valueOf(this.id)+";";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            stmt.close();
            conn.close();
            this.name = newName;
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    public void updateDocumentEndDate(Date newEndDate){
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            String query = "UPDATE public.\"Documents\" SET endDate= "+String.valueOf(newEndDate)+" WHERE ID = "+String.valueOf(this.id)+";";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            stmt.close();
            conn.close();
            this.endDate = newEndDate;
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    public void deleteDocument(){
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            String query = "DELETE FROM public.\"Documents\" WHERE ID = "+String.valueOf(this.id)+";";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            stmt.close();
            conn.close();
            this.name = null;
            this.endDate = null;
            this.id = 0;
            this.userID = 0;
            this.courseID = 0;
            this.homework = false;
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }

}
