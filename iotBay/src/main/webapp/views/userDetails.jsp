<%@ page import="com.bean.Customer" %>
<%@ page import="com.enums.Status" %>
<%@ page import="com.util.Utils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Details</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/base.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/HeaderAndFooter.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/userDetails.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<%
    Customer customer = new Customer();
    if (session.getAttribute("loggedInUser") != null){
        customer = (Customer)session.getAttribute("loggedInUser");
    } else {
        session.setAttribute("errorMessage", "Please login to view your profile");
        response.sendRedirect("/views/login.jsp");
    }
%>
<body>
<%--Header--%>
<div class="header" style="border-bottom: 1px solid #a7a7a7">
    <!-- Logo -->
    <a href="<%=request.getContextPath()%>/home">
        <img src="<%=request.getContextPath()%>/assets/img/Logo.png" alt="IotBay Logo">
    </a>
    <!-- menu -->
    <menu>
        <a href="<%=request.getContextPath()%>/home"><span class="selected">Home</span></a>
        <a href="<%= request.getContextPath() %>/productServlet"><span>Shop</span></a>
        <a href=""><span>Order</span></a>
        <a href=""><span>Category</span></a>
    </menu>

    <!-- icon menu -->
    <menu class="icon">
        <a href="/ViewUserDetailsServlet" style="color: #ff8400;">
            <i class="fa-solid fa-circle-user fa-2x"></i>
            <span><%= customer.getFirstName() != null ? customer.getFirstName() : Status.GUEST.getStatus()%></span>
        </a>
        <a href="/GetByProductNameToCustomer">
            <i class="fa-solid fa-magnifying-glass fa-2x"></i>
            <span>Search</span>
        </a>
        <a href="<%=request.getContextPath()%>/views/cart.jsp">
            <i class="fa-solid fa-cart-shopping fa-2x"></i>
            <span>Cart</span>
        </a>
        <a href="<%=request.getContextPath()%>/views/logout.jsp">
            <i class="fa-solid fa-right-from-bracket fa-2x"></i>
            <span>Log Out</span>
        </a>

    </menu>
</div>

<main>
    <h1>Your Details</h1>
    <div class="details-display">
        <div class="form-row">
            <div class="field">
                <label>Username</label>
                <span><%=customer.getUsername()%></span>
            </div>
        </div>
        <div class="form-row">
            <div class="field">
                <label>First Name</label>
                <span><%=customer.getFirstName()%></span>
            </div>
            <div class="field">
                <label>Last Name</label>
                <span><%=customer.getLastName()%></span>
            </div>
        </div>
        <div class="form-row">
            <div class="field">
                <label>Email Address</label>
                <span><%=customer.getEmail()%></span>
            </div>
        </div>
        <div class="form-row">
            <div class="field">
                <label>Phone Number</label>
                <span><%=Utils.formatPhoneNumber(customer.getPhone())%></span>
            </div>
        </div>
        <div class="form-row">
            <div class="field">
                <label>Address</label>
                <span><%=customer.getAddress()%></span>
            </div>
            <div class="field">
                <label>City</label>
                <span><%=customer.getCity()%></span>
            </div>
        </div>
        <div class="form-row">
            <div class="field">
                <label>State</label>
                <span><%=customer.getState()%></span>
            </div>
            <div class="field">
                <label>Postcode</label>
                <span><%=customer.getPostcode()%></span>
            </div>
            <div class="field">
                <label>Country</label>
                <span><%=customer.getCountry()%></span>
            </div>
        </div>
    </div>

    <div class="account-buttons">
        <a href="/EditDetailsServlet" class="button edit-button">Edit Details</a>
        <a href="/ViewAccountHistoryServlet" class="button view-button">View Account History</a>
        <a href="/views/deleteAccount.jsp" class="button delete-button">Delete Account</a>
    </div>
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
