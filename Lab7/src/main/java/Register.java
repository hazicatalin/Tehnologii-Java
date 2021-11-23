
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="reg")
@SessionScoped
public class Register {
    String username;
    String password;
    String confirmPassword;
    String role;

    public Register() {
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public String getRole() {
        return role;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    public String registerUser(){
        if(this.password.equals(this.confirmPassword)){
            String query = "insert into public.\"Users\" (username, password, role) values ('"+this.username+"', '"+this.password+"', '"+this.role+"')";
            try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/authentication", "postgres", "root");
                Statement st = con.createStatement();
                st.executeUpdate(query);
                st.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.username = null;
            this.password = null;
            this.confirmPassword = null;
            this.role = null;
            }
        return "login";
    }
}
