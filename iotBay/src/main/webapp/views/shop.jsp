<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 19/04/2025
  Time: 2:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Customer" %>
<%@ page import="com.enums.Status" %>
<html>
<%
    Customer customer = (Customer)session.getAttribute("registeredUser");
%>
<head>
    <title>Shop</title>
    <link rel="stylesheet" href="../assets/css/base.css">
    <link rel="stylesheet" href="../assets/css/HeaderAndFooter.css">
    <link rel="stylesheet" href="../assets/css/main.css">
    <link rel="stylesheet" href="../assets/css/shop.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>

<body>
<!-- header -->
<div class="header">
    <!-- Logo -->
    <a href="./main.jsp">
        <img src="../assets/img/Logo.png" alt="IotBay Logo">
    </a>
    <!-- menu -->
    <menu>
        <a href="main.jsp"><span>Home</span></a>
        <a href="shop.jsp"><span class="selected">Shop</span></a>
        <a href=""><span>Order</span></a>
        <a href=""><span>Category</span></a>
    </menu>

    <!-- icon menu -->
    <menu class="icon">
        <a href="">
            <i class="fa-solid fa-circle-user fa-2x"></i>
            <span><%= customer.getFirstName() != null ? customer.getFirstName() : Status.GUEST.getStatus()%></span>
        </a>
        <a href="">
            <i class="fa-solid fa-magnifying-glass fa-2x"></i>
            <span>Search</span>
        </a>
        <a href="./cart.jsp">
            <i class="fa-solid fa-cart-shopping fa-2x"></i>
            <span>Cart</span>
        </a>
        <a href="logout.jsp">
            <i class="fa-solid fa-right-from-bracket fa-2x"></i>
            <span>Log Out</span>
        </a>

    </menu>
</div>
<!-- main body -->
<main>
    <a class="shop_product">
        <img src="../assets/img/example.jpg" alt="Device">
        <h5>IOT Device</h5>
        <p>This is an Introduction. This is an IntroductionThis is an Introduction. This is an Introduction</p>
        <span>$1500.00</span>
    </a>
    <a class="shop_product">
        <img src="../assets/img/example.jpg" alt="Device">
        <h5>IOT Device</h5>
        <p>This is an Introduction. This is an IntroductionThis is an Introduction. This is an Introduction</p>
        <span>$1500.00</span>
    </a>
    <a class="shop_product">
        <img src="../assets/img/example.jpg" alt="Device">
        <h5>IOT Device</h5>
        <p>This is an Introduction. This is an IntroductionThis is an Introduction. This is an Introduction</p>
        <span>$1500.00</span>
    </a>
</main>

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
