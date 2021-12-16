package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;



@Path("/app") 
public class RestFul {
    
    public ArrayList<String> docs = new ArrayList<String>();
    
    @GET
    @Path("/docs/{user}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> geDocs(@PathParam("user") String user) {
        ArrayList<String> docs = new ArrayList<String>();
        String query;
        query = "Select * from public.\"Documents\" s where s.author='"+user+"'"; 
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/authentication", "postgres", "root");
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
    @Path("/docs")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> geDocs() {
        if(docs.isEmpty()){
            String query;
            query = "Select * from public.\"Documents\""; 
            try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/authentication", "postgres", "root");
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
        docs.add(filename);
        byte[] b = Base64.decode(base64Encode);
        System.out.print("asata-i b: "+base64Encode);
        String path = "D:\\Facultate\\Java\\Lab7\\Docs\\"+filename;
        if(Files.exists(Paths.get(path)))
            return"File already exists!";
        else{
            addToDB(filename, username);
            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(b);
             } catch (FileNotFoundException ex) {
                Logger.getLogger(RestFul.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                 System.out.println("alabala1");
                Logger.getLogger(RestFul.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "Success";
    } 
    @PUT
    @Path("/update/{filename}")
    public String updateDoc(@PathParam("filename") String filename,
        String base64Encode) {
        byte[] b = Base64.decode(base64Encode);
        System.out.print("asata-i b: "+base64Encode);
        String path = "D:\\Facultate\\Java\\Lab7\\Docs\\"+filename;
        if(!Files.exists(Paths.get(path)))
            return"File doesn't exists!";
        else{
            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(b);
             } catch (FileNotFoundException ex) {
                Logger.getLogger(RestFul.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                 System.out.println("alabala1");
                Logger.getLogger(RestFul.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "success";
    }
    
    @DELETE
    @Path("delete/{filename}")
    public String deleteProduct(@PathParam("filename") String filename){
        docs.remove(filename);
        File myObj = new File("D:\\Facultate\\Java\\Lab7\\Docs\\"+filename); 
        if (!myObj.delete()) { 
          return"Failed to delete the folder.";
        } 
        String query = "Delete from public.\"Documents\" s where s.name='"+filename+"'"; 
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/authentication", "postgres", "root");
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
    
    public void addToDB(String filename, String username){
        String query = "insert into public.\"Documents\" (name, author) values ('"+filename+"', '"+username+"')";
            try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/authentication", "postgres", "root");
                Statement st = con.createStatement();
                st.executeUpdate(query);
                st.close();
                con.close();
            } catch (SQLException | ClassNotFoundException ex) {
                System.out.println(ex.toString());
            }
    }
}
