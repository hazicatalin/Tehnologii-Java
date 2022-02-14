/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import org.json.simple.JSONArray;
import java.sql.Date;  
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONObject;


public class DBmanager {
    private static DBmanager instance = new DBmanager();
    
    private DBmanager() {
    }
    
    public static DBmanager getInstance(){
        return instance;
    }
    
    public void insertCourse(String name, int credits, int year){
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            String query = "INSERT INTO public.\"Courses\" (name, credits, year) VALUES ('"+name+"', "+credits+", "+year+");";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            stmt.close();
            conn.close();
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
     public void insertDocument(String name, Date date, int courseID, boolean homework){
        try{
            int id;
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            String query = "INSERT INTO public.\"Documents\"(name, \"endDate\", \"userID\", \"courseID\", homework) VALUES ('"+name+"', "+date+", "+courseID+", "+homework+") RETURNING ID;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next())
            {
              id = rs.getInt("ID");
            }
            stmt.close();
            rs.close();
            conn.close();
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
     
    public void insertGrade(int studentID, int courseID, int teacherID, int grade){
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            String query = "INSERT INTO public.\"Catalog\"(\"studentID\", \"courseID\", \"teacherID\", grade) VALUES ('"+studentID+"', "+courseID+", "+teacherID+", "+grade+");";
            Statement stmt = conn.createStatement();
            stmt.executeQuery(query);
            stmt.close();
            conn.close();
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    public void insertUser(String email, String password, String name, String role, String courses){
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            String query = "INSERT INTO public.\"Users\" (email, password, role, courses, name) VALUES ('"+email+"', '"+password+"', '"+role+"', '"+courses+"', '"+name+"');";
            Statement stmt = conn.createStatement(); 
            stmt.executeQuery(query);           
            stmt.close();
            conn.close();
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    public JSONArray getCourse(int id){
        JSONArray course = new JSONArray();
        String query = "select * from public.\"Courses\" where \"ID\"="+String.valueOf(id)+";";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                course.add(new Course(rs.getInt("ID"), rs.getInt("credits"), rs.getInt("year"), rs.getString("name")));
            }
            st.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
        return course;
    }
    
    public JSONArray getCourses(){
        JSONArray courses = new JSONArray();
        String query = "select * from public.\"Courses\";";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                Course course = new Course(rs.getInt("ID"), rs.getInt("credits"), rs.getInt("year"), rs.getString("name"));
                courses.add(course);
            }
            st.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
        return courses;
    }
    
    public JSONArray getUserCourses(int id){
        User user = getUseru(id);
        String [] userCoursesArr = user.getUserCourses();
        JSONArray courses = new JSONArray();
        for(int i=0; i<userCoursesArr.length; i++){
            System.out.println("hihi: "+userCoursesArr[i]);
            String query = "select * from public.\"Courses\" where \"ID\" = "+userCoursesArr[i]+";";
            try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next()) {
                    Course course = new Course(rs.getInt("ID"), rs.getInt("credits"), rs.getInt("year"), rs.getString("name"));
                    courses.add(course);
                }
                st.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.toString());
            }
        }
        return courses;
    }
    
    public JSONArray getGrade(int studentID, int courseID){
        JSONArray grade = null;
        String query = "select * from public.\"Catalog\" where \"studentID\"="+String.valueOf(studentID)+" AND \"courseID\"="+String.valueOf(courseID)+";";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                grade.add(new Grade(rs.getInt("studentID"), rs.getInt("courseID"), rs.getInt("teacherID"), rs.getInt("grade")));
            }
            st.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
        return grade;
    }
    
    public JSONArray getStudentGrades(int id){
        JSONArray grades = new JSONArray();
        String query = "select * from public.\"Catalog\" WHERE \"studentID\"="+id+";";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                Grade grade = new Grade(rs.getInt("studentID"), rs.getInt("courseID"), rs.getInt("teacherID"), rs.getInt("grade"));
                grades.add(grade);
            }
            st.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
        return grades;
    }
    
    public JSONArray getCourseGrades(int id){
        JSONArray grades = new JSONArray();
        String query = "select * from public.\"Catalog\" WHERE \"courseID\"="+id+";";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                Grade grade = new Grade(rs.getInt("studentID"), rs.getInt("courseID"), rs.getInt("teacherID"), rs.getInt("grade"));
                grades.add(grade);
            }
            st.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
        return grades;
    }
    
    public JSONArray getDocument(int id){
        JSONArray doc = null;
        String query = "select * from public.\"Documents\" where \"ID\"="+String.valueOf(id)+";";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                doc.add(new Document(rs.getString("name"), rs.getInt("userID"), rs.getInt("CourseID"), rs.getDate("endDate"), rs.getInt("ID")));
            }
            st.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
        return doc;
    }
    
    public JSONArray getDocuments(){
        JSONArray docs = new JSONArray();
        String query = "select * from public.\"Documents\";";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                Document doc = new Document(rs.getString("name"), rs.getInt("userID"), rs.getInt("CourseID"), rs.getDate("endDate"), rs.getInt("ID"));
                docs.add(doc);
            }
            st.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
        return docs;
    }
    
    public JSONArray getUserDocuments(int id){
        JSONArray docs = new JSONArray();
        String query = "select * from public.\"Documents\" WHERE \"userID\" = "+id+";";
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                Document doc = new Document(rs.getString("name"), rs.getInt("userID"), rs.getInt("CourseID"), rs.getDate("endDate"), rs.getInt("ID"));
                docs.add(doc);
            }
            st.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
        return docs;
    }
    
    public JSONArray getStudentDocuments(int id){
        String[] courses = getUseru(id).getCourses().split(";");
        JSONArray docs = new JSONArray();
        for (int i = 0; i < courses.length; i++) {
            String query = "select * from public.\"Documents\" WHERE \"courseID\" = "+courses[i]+";";
            try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next()) {
                    Document doc = new Document(rs.getString("name"), rs.getInt("userID"), rs.getInt("CourseID"), rs.getDate("endDate"), rs.getInt("ID"));
                    docs.add(doc);
                }
                st.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            } catch (ClassNotFoundException ex) {
                System.out.println(ex.toString());
            }
        }
        return docs;
    }
    
    public JSONArray getUser(int id){
        JSONArray user = new JSONArray();
        String query = "select * from public.\"Users\" where \"ID\"="+String.valueOf(id)+";";
        try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.next()) {
                    user.add(new User(rs.getString("email"), rs.getString("role"), rs.getString("password"), rs.getInt("ID"), rs.getString("courses"), rs.getString("name")));
                }
                st.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            } catch (ClassNotFoundException ex) {
               System.out.println(ex.toString());
            }
        return user;
    }
    
    public User getUseru(int id){
        System.out.println("ID: "+id);
        User user = null;
        String query = "select * from public.\"Users\" where \"ID\"="+String.valueOf(id)+";";
        try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                if(rs.next()) {
                    user=new User(rs.getString("email"), rs.getString("role"), rs.getString("password"), rs.getInt("ID"), rs.getString("courses"), rs.getString("name"));
                }
                st.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            } catch (ClassNotFoundException ex) {
               System.out.println(ex.toString());
            }
        return user;
    }
    
     public JSONArray getUsers(){
         
        JSONArray users = new JSONArray();
        String query = "select * from public.\"Users\";";
        try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next()) {
                    User user = new User(rs.getString("email"), rs.getString("role"), rs.getString("password"), rs.getInt("ID"), rs.getString("courses"), rs.getString("name"));
                    users.add(user);
                }
                st.close();
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            } catch (ClassNotFoundException ex) {
               System.out.println(ex.toString());
            }
        return users;
    }    
    
    public JSONArray loginUser(String email, String password){
        JSONArray user = new JSONArray();
        if(!email.equals("") && !password.equals("")){
            String query = "select * from public.\"Users\" where email='"+email+"' and password='"+password+"'";
            try {
                    Class.forName("org.postgresql.Driver");
                    Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    if (rs.next()) {
                        user.add(new User(rs.getString("email"), rs.getString("role"), rs.getString("password"), rs.getInt("ID"), rs.getString("courses"), rs.getString("name")));
                    }
                    st.close();
                    con.close();
                } catch (SQLException ex) {
                System.out.println(ex.toString());
                } catch (ClassNotFoundException ex) {
                System.out.println(ex.toString());
                }
           }
        return user;
    }  
    
    
    public void addFileToDB(String name, String endDate, String userID, String courseID){
        Date date = null;
        String query = "insert into public.\"Documents\" (name, \"endDate\", \"userID\", \"courseID\") VALUES ('"+name+"', '"+endDate+"', "+userID+", "+courseID+");";
            try {
                Class.forName("org.postgresql.Driver");
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Project", "postgres", "root");
                Statement st = con.createStatement();
                st.executeUpdate(query);
                st.close();
                con.close();
            } catch (SQLException | ClassNotFoundException ex) {
                System.out.println(ex.toString());
            }
    }
    
}
