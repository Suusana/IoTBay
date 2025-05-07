<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Order" %>
<%@ page import="com.bean.Product" %>
<%@ page import="java.util.List" %>

<html>
<head>
  <title>Order Details</title>
</head>
<body>

<h2>Order Details</h2>

<%
  String error = (String) session.getAttribute("error");
  if (error != null) {
%>
<p style="color: red;"><%= error.replaceAll("\n", "<br>") %></p>
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

<table border="1">
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

<%
  if ("Saved".equals(order.getOrderStatus().toString())) {
%>
<form action="<%= request.getContextPath() %>/manageOrder" method="get">
  <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">
  <input type="submit" value="Manage This Order">
</form>
<%
  }
%>

<br><br>
<a href="<%= request.getContextPath() %>/viewOrder">Back to Order List</a>

<%
} else {
%>
<p>No order information available.</p>
<%
  }
%>

</body>
</html>
