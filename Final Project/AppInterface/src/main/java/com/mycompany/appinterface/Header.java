package com.mycompany.appinterface;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Properties;

@ManagedBean(name="head")
@SessionScoped
public class Header {
    Properties prop = new Properties();
    private String userRole;
    
    public Header() {
    }
    
    public void init(){
        try (InputStream input = new FileInputStream("D:\\Facultate\\Java\\AppInterface\\src\\main\\java\\META-INF\\user.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            this.userRole = prop.getProperty("userRole");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    public String myGrades(){
        return "myGrades";
    }
    public String myHomeworks(){
        return "myHomeworks";
    }
    public String myCourses(){
        return "myCourses";
    }
    public String addGrade(){
        return "addGrade";
    }
    public String addHomework(){
        return "addHomework";
    }
    public String addCourse(){
        return "addCourse";
    }
    
    public Boolean disableStudent(){
        init();
        if(userRole.equals("student")){
            return true;
        }
        return false;
    }
    
    public Boolean disableTeacher(){
        init();
        if(userRole.equals("teacher")){
            return true;
        }
        return false;
    }
}
