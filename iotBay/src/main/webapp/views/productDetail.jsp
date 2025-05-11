<%@ page import="com.bean.Customer" %>
<%@ page import="com.enums.Status" %>
<%@ page import="com.bean.Product" %>
<%@ page import="com.util.Utils" %><%--
  Created by IntelliJ IDEA.
  User: Susana
  Date: 5/7/2025
  Time: 16:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>Product Details</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/base.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/HeaderAndFooter.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/productDetail.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<div class="header">
    <!-- Logo -->
    <a href="<%=request.getContextPath()%>/home">
        <img src="<%=request.getContextPath()%>/assets/img/Logo.png" alt="IotBay Logo">
    </a>
    <!-- menu -->
    <menu>
        <a href="<%=request.getContextPath()%>/home"><span class="selected">Home</span></a>
        <a href="<%= request.getContextPath() %>/productServlet"><span>Shop</span></a>
        <a href="<%= request.getContextPath() %>/viewOrder"><span>Order</span></a>
        <a href=""><span>Category</span></a>
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

<%
    Product product = (Product) request.getAttribute("product");
%>
<div class="product-container">
    <img src="<%=request.getContextPath()%>/assets/img/<%= product.getImage()%>" alt="Product Image" class="product-image">

    <div class="product-info">
        <h1><%= product.getProductName()%></h1>
        <p class="price">$ <%=product.getPrice()%></p>
        <p><%= product.getDescription()%></p>

        <!--display err msg-->
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
        <div style="color: red; font-weight: bold;"><%= error %></div>
        <%
            }
            int quantityVal = 1;
            if (request.getAttribute("quantity") != null) {
                quantityVal = Integer.parseInt(request.getAttribute("quantity").toString());
            }
        %>

        <div class="actions">
        <form action="<%= request.getContextPath() %>/createOrder" method="post">
            <input type="hidden" name="productId" value="<%= product.getProductId() %>" />

            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" value="1" min="1" style="width: 60px;">

            <button type="submit" name="action" value="Submit" class="buy-btn">Buy Now</button>
            <button type="submit" name="action" value="Save" class="cart-btn">Add to Cart</button>
        </form>
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
            <a href="<%=request.getContextPath()%>/home"><span>Home</span></a>
            <a href="<%=request.getContextPath()%>/productServlet"><span>Shop</span></a>
            <a href="<%= request.getContextPath() %>/viewOrder"><span>Order</span></a>
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
