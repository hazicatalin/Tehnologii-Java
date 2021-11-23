
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="rf")
@SessionScoped
public class ReadFiles {
    private ArrayList<String> docs = new ArrayList<String>();

    public ReadFiles() {
        
    }

    public void setDocs(ArrayList<String> docs) {
        this.docs = docs;
    }
    public ArrayList<String> getDocs() {
        this.docs.clear();
        String query = "Select * from public.\"Documents\"";
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
        Logger.getLogger(ReadFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        return docs;
    }
}
