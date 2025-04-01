<%@ page import="com.bean.Customer" %><%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 19/03/2025
  Time: 2:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Customer customer = (Customer) session.getAttribute("registeredUser");

    String email = request.getParameter("email");
    String password = request.getParameter("password");

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        if (customer != null && email.equalsIgnoreCase(customer.getEmail()) && password.equals(customer.getPassword())) {
            session.setAttribute("loggedIn", Boolean.TRUE);
            response.sendRedirect("./welcome.jsp");
        } else {
            request.setAttribute("error", "Incorrect Username or Password!");
        }
    }
%>

<html lang="en">
<head>
    <title>Login</title>
    <link rel="stylesheet" href="../assets/css/base.css">
    <link rel="stylesheet" href="../assets/css/login.css">
    <link rel="stylesheet" href="../assets/css/HeaderAndFooter.css">
    <script src="../assets/js/UIHandler.js"></script>
</head>

<body>
<div class="header" style="border-bottom: 1px solid #a7a7a7">
    <!-- Logo -->
    <a href="./main.jsp">
        <img src="../assets/img/Logo.png" alt="IotBay Logo">
    </a>
</div>

<div class="content">
    <div class="title"><h2>Login</h2></div>

    <%--Login Form--%>
    <form action="login.jsp" method="post">
        <label for="email">Email Address</label>
        <input id="email" name="email" type="text" placeholder="Email@site.com" onclick="hideMsg()" required/>

        <label for="password">Password</label>
        <input id="password" name="password" type="password" placeholder="Your password" onclick="hideMsg()" required/>

        <%
            String errorMessage = (String) request.getAttribute("error");
            boolean hasError = errorMessage != null;
        %>
        <p id="errorMsg"
           style="color: red; visibility: <%= hasError ? "visible" : "hidden" %>;">
            <%=errorMessage%>
        </p>

        <input id="loginBtn" type="submit" value="Login" style="cursor: pointer;"/>
    </form>

    <a href="register.jsp" class="visibleLink">Create an account?</a>
</div>

<!-- footer -->
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
