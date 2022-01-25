/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.Lab11B;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/score")
@Singleton
public class ScoreManager {
    
    @GET
    @Path("/add")
    @Produces(MediaType.TEXT_PLAIN)
    public String addScore(
            @QueryParam("username") String username) {
        System.out.println(username);
        int scr=0;
        String query = "Select score from public.\"Users\" s where s.username='"+username+"'";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/UsersDB", "postgres", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
            {
                scr = rs.getInt("score");
            }
            st.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
            return ex.toString();
        }
        
        scr++;
        query = "update public.\"Users\" set score = "+String.valueOf(scr)+" where username = '"+username+"'";
            try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/UsersDB", "postgres", "root");
                Statement st = con.createStatement();
                st.executeUpdate(query);
                st.close();
                con.close();
            } catch (SQLException | ClassNotFoundException ex) {
                return ex.toString();
            }
        return "Success";
    }
    
    
    @GET
    @Path("/remove")
    @Produces(MediaType.TEXT_PLAIN)
    public String removeScore(
            @QueryParam("username") String username) {
        int scr=0;
        String query = "Select score from public.\"Users\" s where s.username='"+username+"'";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/UsersDB", "postgres", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
            {
                scr = rs.getInt("score");
            }
            st.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            return ex.toString();
        }
        
        scr--;
        query = "update public.\"Users\" set score = "+String.valueOf(scr)+" where username = '"+username+"'";
            try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/UsersDB", "postgres", "root");
                Statement st = con.createStatement();
                st.executeUpdate(query);
                st.close();
                con.close();
            } catch (SQLException | ClassNotFoundException ex) {
                return ex.toString();
            }
        return "Success";
    }
}
