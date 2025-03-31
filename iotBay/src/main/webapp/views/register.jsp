<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 19/03/2025
  Time: 2:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Customer" %>
<%
    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");
    String email = request.getParameter("email");
    String pw= request.getParameter("password");
    String phoneNumber = request.getParameter("phoneNumber");
    String street= request.getParameter("street");
    String unit= request.getParameter("unit");
    String state= request.getParameter("state");
    String city= request.getParameter("city");
    String postalCode= request.getParameter("postalCode");
    String country= request.getParameter("country");
    String username = "no username";

    if(email != null){
        username=email.split("@")[0]; //Can be used as a unique username
    }

    Customer customer = new Customer(username,firstName,lastName,pw,email);
    session.setAttribute("loggedIn",customer);
    Customer loggedIn = (Customer) session.getAttribute("loggedIn");
    if("POST".equalsIgnoreCase(request.getMethod())){
        response.sendRedirect("welcome.jsp");
        return;
    }

%>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="../assets/css/base.css">
    <link rel="stylesheet" href="../assets/css/HeaderAndFooter.css">
    <link rel="stylesheet" href="../assets/css/register.css">
</head>
<body>
<header class="header">
    <!-- Logo -->
    <a href="./main.jsp">
        <img src="../assets/img/Logo.png" alt="IotBay Logo">
    </a>
</header>

<main>
    <h2 id="welcome">Register</h2>
    <form class="form" action="register.jsp" method="post">
        <div>
            <label for="firstName">First Name</label>
            <input id="firstName" name="firstName" type="text" required placeholder="First Name"/>
        </div>
        <div>
            <label for="lastName">Last Name</label>
            <input id="lastName" name="lastName" type="text" required placeholder="Last Name"/>
        </div>
        <div>
            <label for="email">Email</label>
            <input id="email" name="email" type="email" required placeholder="Email@site.com"/>
        </div>

        <div>
            <label for="password">Password</label>
            <input id="password" name="password" type="password" required minlength="6" placeholder="password"/>
        </div>
        <div>
            <label for="phoneNumber">Phone Number</label>
            <input id="phoneNumber" name="phoneNumber" type="tel" placeholder="phoneNumber"/>
        </div>
        <div class="address">
            <h4>Address</h4>
            <label for="Street">Street</label>
            <input id="Street" name="street" type="text" placeholder="Street address">
            <label for="Unit">Unit</label>
            <input id="Unit" name="unit" type="text" placeholder="Unit, building, floor etc">
            <label for="city">City</label>
            <input id="city" name="city" type="text" placeholder="City">
            <label for="State">State</label>
            <input id="State" name="state" type="text" placeholder="State">
            <label for="postalCode">Postal Code</label>
            <input id="postalCode" name="postalCode" type="text" placeholder="Postal Code">
            <label for="country">Country</label>
            <input id="country" name="country" type="text" placeholder="Country">
        </div>
            <button id="registerBtn" type="submit">Submit</button>


    </form>
</main>

<footer class="footer">
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
</footer>
</body>
</html>
