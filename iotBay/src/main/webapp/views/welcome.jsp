<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 19/03/2025
  Time: 2:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Customer" %>
<%@ page import="com.util.Utils" %>
<%@ page import="com.bean.Staff" %>

<html>
<%
    String userType = (String) session.getAttribute("userType");
    Customer customer = null;
    Staff staff = null;

    if ("customer".equalsIgnoreCase(userType) || "guest".equalsIgnoreCase(userType)) {
        customer = (Customer) session.getAttribute("loggedInUser");
    } else if ("staff".equalsIgnoreCase(userType)) {
        staff = (Staff) session.getAttribute("loggedInUser");
    }
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

    <!-- Welcome Message dependent on userType -->
    <%
        if (customer != null) {
    %>
    <h1>Welcome, <%= Utils.capitaliseFirst(customer.getFirstName()) %>!</h1>
    <p>Your email: <strong><%= customer.getEmail() %></strong></p>
    <p class="description">
        <%= "guest".equalsIgnoreCase(userType)
                ? "You’re browsing as a guest. Your access is limited."
                : "We're excited to have you join IoTBay <br> Start exploring now!" %>
    </p>
    <!-- Go to Main Page -->
    <a href="<%=request.getContextPath()%>/home"><button class="style1">Go to Main Page</button></a>
    <%
    } else if (staff != null) {
    %>
    <h1>Welcome, <%=staff.getStaffName()%>!</h1>
    <p class="description">Welcome to the IoTBay System <br> Continue to the Product Management Page</p>
    <!-- Go to Product Management Page -->
    <a href="<%=request.getContextPath()%>/ProductManagementServlet"><button class="style1">Go to Product Management</button></a>
    <%
    } else {
    %>
    <h2>Session expired or user not recognized.</h2>
    <a href="<%=request.getContextPath()%>/views/login.jsp"><button class="style1">Go to Login</button></a>
    <%
        }
    %>

</div>
</body>
</html>
