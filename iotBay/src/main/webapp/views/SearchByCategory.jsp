<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 07/05/2025
  Time: 3:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Customer" %>
<%@ page import="com.enums.Status" %>
<%@ page import="com.bean.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.util.Utils" %>
<html>
<%
    Customer customer = new Customer();
    if (session.getAttribute("registeredUser") != null) {
        customer = (Customer) session.getAttribute("registeredUser");
    } else {
        customer.setUsername(Status.GUEST.getStatus());
    }

    //from Servlet GetByProductNameTOCustomer
    List<Product> allProducts = (List<Product>) request.getAttribute("products");
    String message = (String) request.getAttribute("message");
    String category = (String) request.getAttribute("category");
%>
<head>
    <title>Shop</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/HeaderAndFooter.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/ProductManagement.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/searchByCategory.css">

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
        <a href="<%= request.getContextPath() %>/viewOrder"><span>Order</span></a>
        <a href="<%= request.getContextPath() %>/ViewPayment"><span>Payment</span></a>
    </menu>

    <!-- icon menu -->
    <menu class="icon">
        <a href="<%=request.getContextPath()%>/ViewUserDetailsServlet">
            <i class="fa-solid fa-circle-user fa-2x"></i>
            <span><%= customer.getFirstName() != null ? Utils.capitaliseFirst(customer.getFirstName()) : Status.GUEST.getStatus()%></span>
        </a>
        <a href="<%=request.getContextPath()%>/GetByProductNameToCustomer" class="selected">
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
<main>
    <h2><%=category%> Product List</h2>
    <div>
        <% if (allProducts != null && !allProducts.isEmpty()) {
            for (Product product : allProducts) { %>
        <a class="shop_product">
            <img src="<%= request.getContextPath() %>/assets/img/<%= product.getImage() %>" alt="Device">
            <h5><%= product.getProductName() %>
            </h5>
            <p><%= product.getDescription() %>
            </p>
            <span>$<%= product.getPrice() %></span>
            <h5>Category: <%= product.getCategory().getCategory()%>
            </h5>
        </a>
        <% }
        } else { %>
        <p>No products available right now.</p>
        <!--this will appear when there is an servlet connection error or nothing to show --->
        <% } %>
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
            <a href="<%=request.getContextPath()%>/home"><span>Home</span></a>
            <a href="<%=request.getContextPath()%>/productServlet"><span>Shop</span></a>
            <a href="<%=request.getContextPath()%>/viewOrder"><span>Order</span></a>
            <a href="<%= request.getContextPath() %>/ViewPayment"><span>Payment</span></a>
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
