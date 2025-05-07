<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Order" %>
<%@ page import="com.bean.Product" %>
<%@ page import="java.util.List" %>

<html>
<head>
  <title>Order Details</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/OrderStyle.css">
</head>
<body>

<h2 class="title">Order Details</h2>

<%
  String error = (String) session.getAttribute("error");
  if (error != null) {
%>
<div class="errorMsg"><%= error.replaceAll("\n", "<br>") %></div>
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
    <td><%= order.getCreateDate() %></td>
  </tr>
  <tr>
    <th>Status</th>
    <td><%= order.getOrderStatus() %></td>
  </tr>
  <tr>
    <th>Buyer</th>
    <td><%= order.getBuyer() != null ? order.getBuyer().getFirstName() : "Guest" %></td>
  </tr>
  <tr>
    <th>Product</th>
    <td>
      <%
        if (product != null) {
      %>
      <p>Name: <%= product.getProductName() %></p>
      <p>ID: <%= product.getProductId() %></p>
      <p>Price (each): $<%= product.getPrice() %></p>
      <p>Quantity: <%= product.getQuantity() %></p>
      <p><strong>Total: $<%= String.format("%.2f", product.getPrice() * product.getQuantity()) %></strong></p>
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
  <form action="<%= request.getContextPath() %>/manageOrder" method="get" style="display: inline-block; margin-right: 10px;">
    <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
    <input type="submit" value="Manage This Order" class="btn-gold">
  </form>
  <%
    }
  %>

  <a href="<%= request.getContextPath() %>/viewOrder" class="btn-gold" style="display: inline-block;">Back to Order List</a>
</div>

<%
} else {
%>
<p>No order information available.</p>
<%
  }
%>

</body>
</html>
