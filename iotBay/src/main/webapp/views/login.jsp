<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 19/03/2025
  Time: 2:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Login</title>
    <link rel="stylesheet" href="../assets/css/base.css">
    <link rel="stylesheet" href="../assets/css/HeaderAndFooter.css">
<%--    post form-data to the welcome page--%>
</head>

<body>
<div class="header">
    <!-- Logo -->
    <a href="./main.jsp">
        <img src="../assets/img/Logo.png" alt="IotBay Logo">
    </a>
    <!-- Menu -->
    <menu>
        <li><a href=""><span>Home</span></a></li>
        <li><a href=""><span>Shop</span></a></li>
        <li><a href=""><span>Order</span></a></li>
        <li><a href=""><span>Category</span></a></li>
    </menu>
    <!-- icon menu -->
    <menu class="icon">
        <li><a href="">
            <i class="fa-solid fa-magnifying-glass fa-2x"></i>
            <span>Search</span>
        </a></li>
        <li><a href="">
            <i class="fa-solid fa-cart-shopping fa-2x"></i>
            <span>Cart</span>
        </a></li>
    </menu>
</div>

    <%--Login Form--%>
    <h1>IoTBay</h1>
    <h2>Login</h2>

    <form action="welcome.jsp" method="post">
        <label for="email">Email Address</label>
        <input id="email" name="email" type="text" required />

        <label for="password">Password</label>
        <input id="password" name="password" type="password" required />

        <input id="login" type="submit" value="Login" style="cursor: pointer; padding: 10px;" />
    </form>

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
