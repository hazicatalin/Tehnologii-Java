/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hazi_
 */
public class MyServlet extends HttpServlet {


    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Date date= new Date();
            Timestamp time = new Timestamp(date.getTime());
            String key = request.getParameter("key");
            int value = Integer.parseInt(request.getParameter("value"));
            boolean mock = request.getParameter( "mock" ) != null;
            boolean sync = request.getParameter( "sync" ) != null;
            //out.println(mock);
            Logger logger = Logger.getLogger(MyServlet.class.getName());
            String a = request.getMethod();
            String b = request.getRemoteAddr();
            String c = request.getRemoteUser();
            Locale d = request.getLocale();
            Enumeration<String> e = request.getParameterNames();
            
            logger.log(Level.INFO, "HTTP method: "+a);
            logger.log(Level.INFO, "IP: "+b);
            logger.log(Level.INFO, "user-agent: "+c);
            logger.log(Level.INFO, "client language: "+d);
            while (e.hasMoreElements())
                logger.log(Level.INFO, e.nextElement());
            if(mock == true)
                out.println("confirm");
            else
            {  
                if(sync == false){              
                    String s = "";
                    for(int i=0; i<value; i++){
                        s = s+key+", ";
                    }
                    BufferedWriter f = new BufferedWriter(new FileWriter("repository.txt", true));
                    f.write(s+ time);
                    f.newLine();
                    f.close();
                }
                else{
                    synchronized (this) {
                        String s = "";
                        for(int i=0; i<value; i++){
                            s = s+key+", ";
                        }
                        BufferedWriter f = new BufferedWriter(new FileWriter("repository.txt", true));
                        f.write(s+ time);
                        f.newLine();
                        f.close();
                    }
                }

                String[] strings = new String[100];
                BufferedReader reader = new BufferedReader(new FileReader("repository.txt"));
                String line = reader.readLine();
                int size =0;
                while(line!=null){
                    strings[size] = line;
                    size++;
                    line = reader.readLine();
                }
                for(int i = 0; i<size-1; i++) {
                    for (int j = i+1; j<size; j++) {
                       if(strings[i].split(",")[1].compareTo(strings[j].split(",")[1])>0) {
                          String temp = strings[i];
                          strings[i] = strings[j];
                          strings[j] = temp;
                       }
                    }
                 }
                for(int i = 0; i<strings.length-1; i++) {
                    out.println(strings[i]+"\n");
                }
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
