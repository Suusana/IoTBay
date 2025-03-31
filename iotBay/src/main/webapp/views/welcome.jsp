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
    // Using JavaBeans (Customer)
    Customer customer = (Customer) session.getAttribute("loggedIn");

    // In case no session is set (to avoid the email: unknown issue)
    // create a temporary Customer using the submitted form data
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

    // Set default values if null session or fields are missing
    String displayName = "Guest";
    String displayEmail = "unknown";

    if (customer != null) {
        if (customer.getFirstName() != null && !customer.getFirstName().isEmpty()) {
            displayName = customer.getFirstName();
        }
        if (customer.getEmail() != null && !customer.getEmail().isEmpty()) {
            displayEmail = customer.getEmail();
        }
    }
%>
<head>
    <title>Welcome</title>
    <meta http-equiv="refresh" content="3;url=./main.jsp">
    <link rel="stylesheet" href="../assets/css/base.css">
    <link rel="stylesheet" href="../assets/css/HeaderAndFooter.css">
    <link rel="stylesheet" href="../assets/css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<div class="header">
    <a href="./main.jsp">
        <img src="../assets/img/Logo.png" alt="IotBay Logo">
    </a>
    <menu>
        <a href=""><span>Home</span></a>
        <a href=""><span>Shop</span></a>
        <a href=""><span>Order</span></a>
        <a href=""><span>Category</span></a>
    </menu>
    <menu class="icon">
        <a href="">
            <i class="fa-solid fa-circle-user fa-2x"></i>
            <span><%= displayName %></span>
        </a>
        <a href="">
            <i class="fa-solid fa-magnifying-glass fa-2x"></i>
            <span>Search</span>
        </a>
        <a href="">
            <i class="fa-solid fa-cart-shopping fa-2x"></i>
            <span>Cart</span>
        </a>
        <a href="logout.jsp">
            <i class="fa-solid fa-right-from-bracket fa-2x"></i>
            <span>Log Out</span>
        </a>
    </menu>
</div>

<div class="mainBody">
    <div class="banner">
        <img src="../assets/img/example.jpg" alt="Welcome Banner">
        <div class="intro">
            <h5>Welcome, <%= displayName %>!</h5>
            <p>Your email: <%= displayEmail %></p>
            <span>We're excited to have you join IoTBay. Start exploring now!</span>
            <br>
            <a href="main.jsp"><button class="style1">Go to Main Page</button></a>
        </div>
    </div>
</div>

<div class="footer">
    <hr>
    <div>
        <div class="section">
            <h6 id="dif">IoTBay</h6><br>
            <span>The most complete range of IoT devices to upgrade your life at the touch of a button.</span>
        </div>
        <div class="section">
            <h6>Links</h6>
            <a href=""><span>Home</span></a>
            <a href=""><span>Shop</span></a>
            <a href=""><span>Order</span></a>
            <a href=""><span>Category</span></a>
        </div>
        <div class="section">
            <h6>Contact Us</h6>
            <span>Address: 123 IotBay, Sydney</span>
            <span>Phone Number: +61 0499999999</span>
            <span>Email Address: IotBay@example.com</span>
        </div>
        <div class="section">
            <h6>Follow Us</h6>
            <a href="https://www.instagram.com/">
                <i class="fa-brands fa-instagram fa-lg"></i>
                <span>Instagram</span>
            </a>
            <a href="https://www.facebook.com/">
                <i class="fa-brands fa-facebook fa-lg"></i>
                <span>Facebook</span>
            </a>
            <a href="https://discord.com/">
                <i class="fa-brands fa-discord fa-lg"></i>
                <span>Discord</span>
            </a>
            <a href="https://x.com/?lang=en">
                <i class="fa-brands fa-x-twitter fa-lg"></i>
                <span>Twitter</span>
            </a>
        </div>
    </div>
    <hr>
    <p>©2025. IoTBay Group 4 All Right Reserved</p>
</div>
</body>
</html>
