<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Order" %>
<%@ page import="com.bean.Product" %>
<%@ page import="java.util.List" %>
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
    <title>Order Details</title>
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
        <a href="<%= request.getContextPath() %>/viewOrder"><span class="selected">Order</span></a>
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
<h2 class="title">Order Details</h2>

<%
    String error = (String) session.getAttribute("error");
    if (error != null) {
%>
<div class="errorMsg"><%= error.replaceAll("\n", "<br>") %>
</div>
<%
        session.removeAttribute("error");
    }
%>


<%
    Order order = (Order) request.getAttribute("order");
    if (order != null) {
        List<Product> products = order.getProducts();
        Product product = (products != null && !products.isEmpty()) ? products.get(0) : null;
%>

<table class="orderTable">
    <tr>
        <th>Create Date</th>
        <td><%= order.getCreateDate() %>
        </td>
    </tr>
    <tr>
        <th>Status</th>
        <td><%= order.getOrderStatus() %>
        </td>
    </tr>
    <tr>
        <th>Buyer</th>
        <td><%= order.getBuyer() != null ? order.getBuyer().getFirstName() : "Guest" %>
        </td>
    </tr>
    <tr>
        <th>Product</th>
        <td>
            <%
                if (product != null) {
            %>
            <p>Name: <%= product.getProductName() %>
            </p>
            <p>ID: <%= product.getProductId() %>
            </p>
            <p>Price (Each): $<%= product.getPrice() %>
            </p>
            <p>Quantity: <%= order.getQuantity() %>
            </p>
            <p><strong>Total: $<%= String.format("%.2f", product.getPrice() * order.getQuantity()) %>
            </strong></p>
            <%
            } else {
            %>
            No product found.
            <%
                }
            %>
        </td>
    </tr>
</table>

<div style="text-align: center; margin-top: 30px;">
    <%
        if ("Saved".equals(order.getOrderStatus().toString())) {
    %>
    <form action="<%= request.getContextPath() %>/manageOrder" method="get"
          style="display: inline-block; margin-right: 10px;">
        <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
        <input type="submit" value="Manage This Order"
               style="display:inline-block; width:220px; height:45px; line-height:45px;
              background-color:#f6b24c; border:none; color:white; font-weight:bold;
              font-size:16px; text-align:center; border-radius:5px; margin:5px;
              cursor:pointer;"/>

    </form>
    <%
        }
    %>
    <!--just wanna make sure these two buttons looks same size for beauty-->
    <a href="<%= request.getContextPath() %>/viewOrder"
       style="display:inline-block; width:220px; height:45px; line-height:45px;
          background-color:#f6b24c; border:none; color:white; font-weight:bold;
          font-size:16px; text-align:center; border-radius:5px; margin:5px;
          text-decoration:none;">
        Back to Order List
    </a>
</div>

<%
} else {
%>
<p>No order information available.</p>
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
