
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.file.UploadedFile;

@ManagedBean(name="uf")
@SessionScoped
@Named
public class UploadFile {
    
    private String username;    
    private UploadedFile file;
    
    public UploadFile() {
        try {
        File myObj = new File("User.txt");
        Scanner myReader = new Scanner(myObj);
        this.username = myReader.nextLine();
        myReader.close();
      } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }File myObj = new File("User.txt"); 
        if (myObj.delete()) { 
          System.out.println("Deleted the file: " + myObj.getName());
        } else {
          System.out.println("Failed to delete the file.");
        } 
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public UploadedFile getFile() {
        return file;
    }
    public void setFile(UploadedFile file) {
        this.file = file;
    }
    public String getUsername() {
        return username;
    }
    
    public void upload() {
        System.out.println(this.username);
        if (file != null) {
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            try{
                FileOutputStream fos = new FileOutputStream(new File("D:\\Facultate\\Java\\Lab7\\Docs\\", this.file.getFileName()));
                InputStream is = this.file.getInputStream();
                int BUFFER_SIZE = 8192;
                byte[] buffer = new byte[BUFFER_SIZE];
                int a;
                while(true){
                    a = is.read(buffer);
                    if(a < 0) break;
                    fos.write(buffer, 0, a);
                    fos.flush();
                }
                fos.close();
                is.close();
            }catch(IOException e){
                System.out.println(e);}
            
            String query = "insert into public.\"Documents\" (name, author) values ('"+this.file.getFileName()+"', '"+this.username+"')";
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
        }
    }
}
