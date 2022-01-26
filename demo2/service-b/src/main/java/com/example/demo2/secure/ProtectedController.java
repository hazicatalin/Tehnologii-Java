package com.example.demo2.secure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.ClaimValue;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Path("/protected")
@RequestScoped
public class ProtectedController {

    @Inject
    @Claim("custom-value")
    private ClaimValue<String> custom;

    @GET
    @RolesAllowed("protected")
    public String getJWTBasedValue() {
        return "Protected Resource; Custom value : " + custom.getValue();
    }
    
    @GET
    @RolesAllowed("protected")
    @Path("/{user}")
    @Produces(MediaType.TEXT_PLAIN)
    public String geDocs(@PathParam("user") String user) {
        String docs = new String();
        String query;
        query = "Select * from public.\"Documents\" s where s.author='"+user+"'"; 
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/UsersDB", "postgres", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
            {
              String str = rs.getString("name");
              System.out.println(str);
              docs = docs + "; " +str;
            }
            st.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.print("Error");
        }
        return docs;
    }
    
    @GET
    @Path("/all")
    @RolesAllowed("protected")
    @Produces(MediaType.TEXT_PLAIN)
    public String geDocs() {
        String docs = new String();
        String query;
        query = "Select * from public.\"Documents\""; 
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/UsersDB", "postgres", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
            {
                String str = rs.getString("name");
                System.out.println(str);
                docs = docs + ";" +str;
            }
            st.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.print("Error");
        }
        return docs;
    }
}
