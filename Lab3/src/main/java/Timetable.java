
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

@ManagedBean(name="timetable")
@SessionScoped
public class Timetable {
    private ArrayList<String> exams = new ArrayList<String>();
    private ArrayList<String> studentExams = new ArrayList<String>();
    private int[][] graph = new int[100][100];
    int[] colors = new int[100];
    private String ret="";

    public ArrayList<String> getExams() {
        return exams;
    }

    public ArrayList<String> getStudentExams() {
        return studentExams;
    }

    public int[][] getGraph() {
        return graph;
    }

    public int[] getColors() {
        return colors;
    }

    public String getRet() {
        return ret;
    }
    
    public Timetable(){
        String query = "Select * from exam";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/timetable", "postgres", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            int i=0;
            while(rs.next())
            {
              colors[i]=0;
              i++;
              String str = rs.getString("name");
              exams.add(str);
            }
            query = "Select* from student";
            rs = st.executeQuery(query);
            while(rs.next())
            {
              String str = rs.getString("exams");
              studentExams.add(str);
            }
            rs.close();
            st.close();
            con.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Timetable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Timetable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setTimetable();
        int max=0;
        for(int i=0; i<exams.size(); i++){
            if(colors[i]>max)
                max=colors[i];
        }
        for(int i=1; i<=max;i++){
            ret = ret+String.valueOf(i)+"-";
            for(int j=0; j<exams.size(); j++)
                if(colors[j]==i)
                    ret=ret+exams.get(j)+"; ";
            ret = ret+";     ";
        }
    }
    
    public void setTimetable(){    
        for(int i=0; i<exams.size(); i++)
            for(int j=0; j<exams.size(); j++)
                for(int k=0; k<studentExams.size(); k++)
                    if(studentExams.get(k).contains(exams.get(i)) && studentExams.get(k).contains(exams.get(j))){
                        graph[i][j] =1;
                    }
                    else{
                        graph[i][j]=0;
                    }
        colors[0]=1;
        for(int i=1; i<exams.size(); i++)
        {
            int contor=1;
            for(int j=0; j<exams.size(); j++)
                if(graph[i][j]==1 && colors[j]==contor){
                    contor++;
                    j=0;
                }
            colors[i]=contor;
        }
    }
    
    public String go(){
        return "startPage";
    }
    
}
