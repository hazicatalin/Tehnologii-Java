
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="studentViewer")
@SessionScoped
public class StudentsViewer {
    private StudentAdd student = new StudentAdd();

    public StudentAdd getStudent() {
        return student;
    }
    
    public ArrayList<StudentAdd> findAll(){
        ArrayList<StudentAdd> students = new ArrayList<StudentAdd>();
        String query = "Select * from student";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/timetable", "postgres", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
            {
              StudentAdd stud = new StudentAdd();
              stud.setName(rs.getString("name")); 
              stud.setExam(rs.getString("exams"));
              students.add(stud);
            }
            st.close();
            rs.close();
            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentsViewer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StudentsViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return students;
    }
}
