<%-- 
    Document   : input
    Created on : Oct 23, 2021, 3:09:09 PM
    Author     : hazi_
--%>

<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Form input</title>
    </head>
    <body>
        <h1>Formular!</h1>
        <form name="Myform" action="Servlet2" method="GET">
            <p>Key: </p><input type="text" name="key"/><br>
            <p>Value: </p><input type="text" name="value"/><br>
            <p>Category: </p>
            <select name="category"/>
                <%ArrayList<String> std =(ArrayList<String>)request.getAttribute("data");
            for(String s:std){%>
                <option><%=s%></option>
             <%}%>
            </select> <br><br>
            <input type="submit" value="submit"/>         
        </form>
    </body>
</html>
