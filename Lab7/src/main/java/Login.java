
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

@ManagedBean(name="log")
@Named
@SessionScoped
public class Login {
    private String username;
    private String password;

    public Login() {
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    } 
    public String register(){
        return "register";
    }  
    
    public String logIn(){
        if(time()){
            if(!username.equals("") && !password.equals("")){
                createFile();
                String query = "select * from public.\"Users\" where username='"+this.username+"' and password='"+this.password+"'";
                try {
                    Class.forName("org.postgresql.Driver");
                    Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/authentication", "postgres", "root");
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    if (rs.next()) {
                        if(rs.getString("role").equals("author"))
                            return "uploadFile";
                        if(rs.getString("role").equals("admin") || rs.getString("role").equals("reviewer"))
                            return "readFiles";                    
                    }
                    st.close();
                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else{
            return "timeWrong";}
        return "wrongUser";
    } 
    
    public void createFile(){
        try {
        File myObj = new File("User.txt");
        if (myObj.createNewFile()) {
          System.out.println("File created: " + myObj.getName());
        } else {
          System.out.println("File already exists.");
        }
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
        try {
          FileWriter myWriter = new FileWriter("User.txt");
          myWriter.write(this.username);
          myWriter.close();
          System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
    }
    
    public Boolean time(){
         Date currentDate = new Date();
         SimpleDateFormat formatter = new SimpleDateFormat("kk");
        return (Integer.parseInt(formatter.format(currentDate))<23 && Integer.parseInt(formatter.format(currentDate))>5);
    }
    
}
