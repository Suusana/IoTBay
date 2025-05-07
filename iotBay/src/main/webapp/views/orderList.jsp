<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bean.Order" %>

<html>
<head>
  <title>Order List</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/OrderStyle.css">
</head>
<body>

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
<div class="errorMsg"><%= message.replaceAll("\n", "<br>") %></div>
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
  <td><%= order.getOrderId() %></td>
  <td><%= order.getCreateDate() %></td>
  <td><%= order.getOrderStatus() %></td>
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

</body>
</html>
