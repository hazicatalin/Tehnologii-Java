
import java.time.LocalTime;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(name="exam")
@SessionScoped
public class ExamAdd {
    private String name;
    private LocalTime startingTime;
    private LocalTime duration;

    public ExamAdd() {
    }
    
    public ExamAdd(String name, LocalTime startingTime, LocalTime duration){
        this.name=name;
        this.duration = duration;
        this.startingTime = startingTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartingTime(LocalTime startingTime) {
        this.startingTime = startingTime;
    }

    public void setDuration(LocalTime duration) {
        this.duration = duration;
    }
    

    public String getName() {
        return name;
    }

    public LocalTime getStartingTime() {
        return startingTime;
    }

    public LocalTime getDuration() {
        return duration;
    }
    
    public String add(){
        String query = "insert into exam (name, starting_time, duration) values ('"+this.name+"', '"+this.startingTime+"', '"+this.duration+"')";
        //query = query+ this.name +", "+this.startingTime+", "+this.duration+")";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/timetable", "postgres", "root");
            Statement st = con.createStatement();
            st.executeUpdate(query);
            st.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ExamAdd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ExamAdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.name = null;
        this.duration = null;
        this.startingTime = null;
        return "startPage";
    }
    
}
