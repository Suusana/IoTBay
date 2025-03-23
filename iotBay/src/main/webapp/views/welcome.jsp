<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 19/03/2025
  Time: 2:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");
    String email = request.getParameter("email");
    String pw= request.getParameter("password");

    String street= request.getParameter("street");
    String unit= request.getParameter("unit");
    String state= request.getParameter("state");
    String city= request.getParameter("city");
    String postalCode= request.getParameter("postalCode");
    String country= request.getParameter("country");
    //String username=email.split("@")[0]; //Can be used as a unique username
%>
<head>
    <title>Welcome</title>
</head>
<body>
<h1>Welcome <%=firstName%></h1>
</body>
</html>
