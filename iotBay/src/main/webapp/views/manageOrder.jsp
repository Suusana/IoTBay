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
  if (session.getAttribute("loggedInUser") != null){
    customer = (Customer)session.getAttribute("loggedInUser");
  } else {
    customer.setUsername(Status.GUEST.getStatus());
  }
%>
<head>
  <title>Manage Order</title>
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
    <a href="<%= request.getContextPath() %>/viewOrder" ><span class="selected">Order</span></a>
    <a href="<%= request.getContextPath() %>/ViewPayment"><span>Payment</span></a>
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
<h2 class="title">Manage Your Order</h2>
<%
  Order order = (Order) request.getAttribute("order");
  if (order != null) {
%>

<!-- display order information -->
<table class="orderTable">
  <tr>
    <th>Order ID</th>
    <td><%= order.getOrderId() %></td>
  </tr>
  <tr>
    <th>Create Date</th>
    <td><%= order.getCreateDate() %></td>
  </tr>
  <tr>
    <th>Status</th>
    <td><%= order.getOrderStatus() %></td>
  </tr>
</table>

<br>

<%
  if ("Saved".equals(order.getOrderStatus().toString())) {
%>

<form action="<%= request.getContextPath() %>/orderAction" method="post">
  <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">

  <h3 style="text-align: center; color: #B88E2F;">Update Product Quantities:</h3>
  <table class="orderTable">
    <tr>
      <th>Product</th>
      <th>Quantity</th>
    </tr>

    <%
      List<Product> productList = order.getProducts();
      if (productList != null && !productList.isEmpty()) {
        for (Product product : productList) {
          int productId = product.getProductId();
    %>
    <tr>
      <td><%= product.getProductName() %> (ID: <%= productId %>)</td>
      <td>
        <!--quantity must more than 0-->
        <input type="number" name="quantity_<%= productId %>" value="<%= product.getQuantity() %>" min="1"
               style="width: 60px; text-align: center;">
      </td>
    </tr>
    <%
      }
    } else {
    %>
    <tr><td colspan="2">No products found.</td></tr>
    <%
      }
    %>
  </table>

  <div style="text-align: center; margin-top: 20px;">
    <button type="submit" name="action" value="update" class="btn-gold" style="margin: 5px;">Save Update</button>
    <button type="submit" name="action" value="submit" class="btn-gold" style="margin: 5px;">Final Submission</button>
    <button type="submit" name="action" value="cancel" class="btn-gold" style="margin: 5px;">Cancel Order</button>
  </div>
</form>

<%
} else {
%>
<p class="center">This order is <strong><%= order.getOrderStatus() %></strong> and cannot be modified.</p>
<%
  }
%>

<br>
<div style="text-align: center;">
  <a href="<%= request.getContextPath() %>/viewOrder" class="btn-gold">Back to Order List</a>
</div>

<%
} else {
%>
<p class="center">No order information available.</p>
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
