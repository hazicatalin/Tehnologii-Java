package com.mycompany.lab3;


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

@ManagedBean(name="student")
@SessionScoped
public class StudentAdd {
    private String name;
    private String exam;
    ArrayList<String> exams = new ArrayList<String>();

    public String getExam() { 
        return exam;
    }

    public StudentAdd() {        
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getExams() {
        this.exams.clear();
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/timetable", "postgres", "root");
            String query = "SELECT * FROM exam";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next())
            {
              String str = rs.getString("name");
                exams.add(str);
            }
            stmt.close();
            rs.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentAdd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(StudentAdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exams;
    }
    
    public String add(){
        if(this.exam != null){
            String query = "insert into student (name, exams) values ('"+this.name+"', '{\""+this.exam+"\"}');";
            String query1 = "Select * from student where name='"+this.name+"';";
            String query2 ="update student set exams = array_append(exams, '"+this.exam+"')  where name='"+this.name+"';";
            try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/timetable", "postgres", "root");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query1);
                if(rs.next() == false)
                    st.executeUpdate(query);
                else{
                    if(!rs.getString("exams").contains(this.exam))
                        st.executeUpdate(query2);
                }
                rs.close();
                st.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ExamAdd.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ExamAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.name = null;
            this.exam = null;
        }
        return "startPage";
    }
}
