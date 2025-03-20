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
</body>

</html>
