<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 19/03/2025
  Time: 2:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Customer" %>
<html>
<%
    Customer customer = (Customer) session.getAttribute("loggedInUser");

    // Capitalise first character of name
    String firstName = customer.getFirstName();
    firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
%>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" href="../assets/css/base.css">
    <link rel="stylesheet" href="../assets/css/welcome.css">
</head>
<body>
<div class="welcome-card">
    <!-- Banner Image -->
    <img src="../assets/img/Logo.png" alt="Welcome Banner" class="banner-img">

    <!-- Welcome Message -->
    <h1>Welcome, <%= firstName %>!</h1>
    <p>Your email: <strong><%= customer.getEmail() %></strong></p>
    <p class="description">We're excited to have you join IoTBay <br> Start exploring now!</p>

    <!-- Go to Main Page -->
    <a href="main.jsp"><button class="style1">Go to Main Page</button></a>
</div>
</body>
</html>
