<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bean.Order" %>
<%@ page import="com.bean.Customer" %>
<%@ page import="com.enums.Status" %>
<%@ page import="com.util.Utils" %>

<html>
<%
    Customer customer = new Customer();
    if (session.getAttribute("loggedInUser") != null) {
        customer = (Customer) session.getAttribute("loggedInUser");
    } else {
        customer.setUsername(Status.GUEST.getStatus());
    }
%>

<head>
    <title>Order List</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/OrderStyle.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/base.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/HeaderAndFooter.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<!-- header -->
<div class="header">
    <!-- Logo -->
    <a href="<%=request.getContextPath()%>/home">
        <img src="<%=request.getContextPath()%>/assets/img/Logo.png" alt="IotBay Logo">
    </a>
    <!-- menu -->
    <menu>
        <a href="<%= request.getContextPath()%>/home"><span>Home</span></a>
        <a href="<%= request.getContextPath() %>/productServlet"><span>Shop</span></a>
        <a href="<%= request.getContextPath() %>/viewOrder" class="selected"><span>Order</span></a>
        <a href="#"><span>Category</span></a>
    </menu>

    <!-- icon menu -->
    <menu class="icon">
        <a href="<%=request.getContextPath()%>/ViewUserDetailsServlet">
            <i class="fa-solid fa-circle-user fa-2x"></i>
            <span><%= customer.getFirstName() != null ? Utils.capitaliseFirst(customer.getFirstName()) : Status.GUEST.getStatus()%></span>
        </a>
        <a href="<%=request.getContextPath()%>/GetByProductNameToCustomer">
            <i class="fa-solid fa-magnifying-glass fa-2x"></i>
            <span>Search</span>
        </a>
        <a href="#">
            <i class="fa-solid fa-cart-shopping fa-2x"></i>
            <span>Cart</span>
        </a>
        <%
            if (session.getAttribute("loggedInUser") != null) {
        %>
        <a href="<%=request.getContextPath()%>/views/logout.jsp">
            <i class="fa-solid fa-right-from-bracket fa-2x"></i>
            <span>Log Out</span>
        </a>
        <%
            }
        %>
    </menu>
</div>

<!-- main body -->
<h2 class="title">My Orders</h2>

<!-- search by orderID -->
<form action="<%= request.getContextPath() %>/viewOrder" method="get" style="text-align:center;">
    <label for="orderId">Search by Order ID:</label>
    <input type="text" id="orderId" name="orderId" placeholder="Enter Order ID" class="input-field">
    <input type="submit" value="Search by ID" class="btn-gold">
</form>

<br>

<!-- search by orderDate -->
<form action="<%= request.getContextPath() %>/viewOrder" method="get" style="text-align:center;">
    <label for="orderDate">Search by Date:</label>
    <input type="date" id="orderDate" name="orderDate" class="input-field">
    <input type="submit" value="Search by Date" class="btn-gold">
</form>

<br>

<%
    String message = (String) request.getAttribute("message");
    if (message != null) {
%>
<div class="errorMsg"><%= message.replaceAll("\n", "<br>") %>
</div>
<%
    }
%>

<!-- display order List -->
<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    if (orders != null && !orders.isEmpty()) {
%>
<table class="orderTable">
    <tr>
        <th>Order ID</th>
        <th style="width:180px;">Create Date</th>
        <th>Status</th>
        <th>Action</th>
    </tr>
    <%
        for (Order order : orders) {
    %>
    <tr>
        <td><%= order.getOrderId() %>
        </td>
        <td><%= order.getCreateDate() %>
        </td>
        <td><%= order.getOrderStatus() %>
        </td>
        <td>
            <form action="<%= request.getContextPath() %>/viewOrderDetails" method="get">
                <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
                <button type="submit" class="btn-gold">View</button>
            </form>
        </td>
    </tr>

    <%
        }
    %>
</table>
<%
} else {
%>
<p class="center">No matching orders found.</p>
<%
    }
%>

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
            <a href="<%=request.getContextPath()%>/home"><span>Home</span></a>
            <a href="<%=request.getContextPath()%>/productServlet"><span>Shop</span></a>
            <a href="<%=request.getContextPath()%>/viewOrder"><span>Order</span></a>
            <a href="#"><span>Category</span></a>
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
