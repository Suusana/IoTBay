<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 19/03/2025
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Customer" %>
<html>
<%
    // Get Customer from session
    Customer customer = (Customer) session.getAttribute("loggedIn");

    // In case no session is set (to avoid the email: unknown issue)
    // create a temporary Customer using the submitted data
    if (customer == null) {
        String firstName = request.getParameter("firstName");
        String email = request.getParameter("email");

        if (firstName != null || email != null) {
            customer = new Customer();
            customer.setFirstName(firstName);
            customer.setEmail(email);
            session.setAttribute("loggedIn", customer);
        }
    }

    // Use the part before @ in the email as a temporary name since there is no DB yet
    String displayName = "Guest";
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
%>
<head>
    <title>Welcome</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font: 16px/1.5 "Helvetica Neue", Helvetica, Arial, sans-serif;
            color: #333;
            background-color: #FFF3E3;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            animation: fadeIn 1s ease-in-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .welcome-card {
            background-color: #fff;
            padding: 30px 40px 50px 40px;
            border-radius: 20px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            text-align: center;
            width: 450px;
            animation: fadeInCard 1s ease-in-out;
        }

        @keyframes fadeInCard {
            from { opacity: 0; transform: scale(0.95); }
            to { opacity: 1; transform: scale(1); }
        }

        .banner-img {
            width: 100%;
            border-radius: 15px;
            margin-bottom: 20px;
        }

        .welcome-card h1 {
            font-size: 28px;
            font-weight: 800;
            color: #B88E2F;
            margin-bottom: 20px;
        }

        .welcome-card p {
            font-size: 16px;
            color: #333;
            margin-bottom: 10px;
        }

        .description {
            margin-bottom: 30px;
            font-style: italic;
            font-size: 14px;
        }

        .style1 {
            border: 1px solid #B88E2F;
            padding: 10px 25px;
            font-size: 16px;
            font-weight: 700;
            color: #B88E2F;
            background-color: transparent;
            border-radius: 8px;
            cursor: pointer;
        }

        .style1:hover {
            background-color: #B88E2F;
            color: #fff;
        }
    </style>
</head>
<body>
<div class="welcome-card">
    <!-- Banner Image -->
    <img src="../assets/img/Logo.png" alt="Welcome Banner" class="banner-img">

    <!-- Welcome Message -->
    <h1>Welcome, <%= displayName %>!</h1>
    <p>Your email: <strong><%= displayEmail %></strong></p>
    <p class="description">We're excited to have you join IoTBay. Start exploring now!</p>

    <!-- Go to Main Page -->
    <a href="main.jsp"><button class="style1">Go to Main Page</button></a>
</div>
</body>
</html>
