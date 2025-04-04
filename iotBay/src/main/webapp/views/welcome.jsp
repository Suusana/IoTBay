<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 19/03/2025
  Time: 2:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Customer" %>
<%@ page import="com.enums.Status" %>
<html>
<%
    // Using JavaBeans (Customer)
    Customer customer = (Customer) session.getAttribute("registeredUser");
    Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");

    // In case no session is set (to avoid the email: unknown issue)
    // create a temporary Customer using the submitted form data
    if (customer == null) {
        String firstName = request.getParameter("firstName");
        String email = request.getParameter("email");

        if (firstName != null || email != null) {
            customer = new Customer();
            customer.setFirstName(firstName);
            customer.setEmail(email);
            session.setAttribute("registeredUser", customer);
        }
    }

    // Set default values if null session or fields are missing
    String displayName = Status.GUEST.getStatus();
    String displayEmail = "unknown";

    if (customer != null) {
        if (customer.getEmail() != null && !customer.getEmail().isEmpty()) {
            displayEmail = customer.getEmail();
            // Use email prefix as name if firstName is missing
            if ((customer.getFirstName() == null || customer.getFirstName().isEmpty()) && displayEmail.contains("@")) {
                displayName = displayEmail.substring(0, displayEmail.indexOf("@"));
            }
        }
        if (customer.getFirstName() != null && !customer.getFirstName().isEmpty()) {
            displayName = customer.getFirstName();
        }
    }

    if (!loggedIn) {
        response.sendRedirect("login.jsp");
        return;
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
    <!-- Welcome Message -->
    <h1>Welcome, <%= displayName %>!</h1>
    <p>Your email: <strong><%= displayEmail %></strong></p>
    <p class="description">We're excited to have you join IoTBay. Start exploring now!</p>
<%--    <p>Will jump to the homepage within 3 seconds.</p>--%>
    <!-- Go to Main Page -->
    <a href="main.jsp"><button class="style1">Go to Main Page</button></a>
</div>
<%--<script>--%>
<%--    setTimeout(()=>{--%>
<%--        window.location.href = "main.jsp";--%>
<%--    },3000)--%>
<%--</script>--%>
</body>
</html>
