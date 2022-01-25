package com.example.Lab11;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Base64;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.HttpUrl.Builder;
import okhttp3.Response;

/**
 *
 */
@Path("/docs")
@Singleton
public class HelloController {

    private final OkHttpClient httpClient = new OkHttpClient();
    
    public ArrayList<String> docs = new ArrayList<String>();
    
    @GET
    @Path("/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> geDocs(@PathParam("user") String user) {
        ArrayList<String> docs = new ArrayList<String>();
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
              docs.add(str);
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
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> geDocs() {
        if(docs.isEmpty()){
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
                  docs.add(str);
                }
                st.close();
                rs.close();
                con.close();
            } catch (ClassNotFoundException | SQLException ex) {
                System.out.print("Error");
            }
        }
        return docs;
    }
    
    @POST
    @Path("/upload")
    public String addFile(String base64Encode,
            @QueryParam("filename") String filename,
            @QueryParam("username") String username) {
        byte[] b = Base64.getDecoder().decode(base64Encode);
        System.out.print("asata-i b: "+base64Encode);
        String path = "D:\\Facultate\\Java\\Lab7\\Docs\\"+filename;
        if(Files.exists(Paths.get(path)))
            return"File already exists!";
        else{
            String response = addToDB(filename, username);
            if(!response.equals("Success"))
                return response;
            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(b);
             } catch (FileNotFoundException ex) {
                Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                 System.out.println("alabala1");
                Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        docs.add(filename);
        return "Success";
    } 
    @PUT
    @Path("/update/{filename}")
    public String updateDoc(@PathParam("filename") String filename,
        String base64Encode) {
        byte[] b = Base64.getDecoder().decode(base64Encode);
        System.out.print("asata-i b: "+base64Encode);
        String path = "D:\\Facultate\\Java\\Lab7\\Docs\\"+filename;
        if(!Files.exists(Paths.get(path)))
            return"File doesn't exists!";
        else{
            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(b);
             } catch (FileNotFoundException ex) {
                Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                 System.out.println("alabala1");
                Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "success";
    }
    
    @DELETE
    @Path("delete/{filename}")
    public String deleteProduct(@PathParam("filename") String filename){
        String del = removeFromDB(filename);
        if(!del.equals("Success"))
            return del;
        docs.remove(filename);
        File myObj = new File("D:\\Facultate\\Java\\Lab7\\Docs\\"+filename); 
        if (!myObj.delete()) { 
          return"Failed to delete the folder.";
        } 
        String query = "Delete from public.\"Documents\" s where s.name='"+filename+"'"; 
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/UsersDB", "postgres", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            st.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.print("Error");
        }
        return "success";
    }
    
    public String addToDB(String filename, String username){
        String rsp = "Error";
        HttpUrl url = HttpUrl.parse("http://localhost:9051/data/score/add").newBuilder()
        .addQueryParameter("username", username)
        .build();

        Request request = new Request.Builder()
               .url(url)
               .build();
        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            rsp = response.body().string();
        } catch (IOException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
        String query = "insert into public.\"Documents\" (name, author) values ('"+filename+"', '"+username+"')";
            try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/UsersDB", "postgres", "root");
                Statement st = con.createStatement();
                st.executeUpdate(query);
                st.close();
                con.close();
            } catch (SQLException | ClassNotFoundException ex) {
                System.out.println(ex.toString());
            }
            
        return rsp;
    }
    
    public String removeFromDB(String filename){
        String username = null;
         String query = "Select author from public.\"Documents\" s where s.name='"+filename+"'";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/UsersDB", "postgres", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
            {
                username = rs.getString("author");
            }
            st.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
            return ex.toString();
        }
        String rsp = "Error";
        HttpUrl url = HttpUrl.parse("http://localhost:9051/data/score/remove").newBuilder()
        .addQueryParameter("username", username)
        .build();

        Request request = new Request.Builder()
               .url(url)
               .build();
        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            rsp = response.body().string();
        } catch (IOException ex) {
            Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
        return rsp;
    }
}
