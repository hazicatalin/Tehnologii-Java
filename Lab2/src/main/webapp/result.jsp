<%-- 
    Document   : result
    Created on : Oct 23, 2021, 3:09:55 PM
    Author     : hazi_
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1> Key: <%=request.getAttribute("key") %></h1><br>
        <h1> Value: <%=request.getAttribute("value") %></h1><br>
        <h1> Category: <%=request.getAttribute("category") %></h1><br>
    </body>
</html>
