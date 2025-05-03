<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bean.Order" %>

<html>
<head>
  <title>Order List</title>
</head>
<body>

<h2>My Orders</h2>

<!-- 🔍 搜索框：按订单号 -->
<form action="<%= request.getContextPath() %>/viewOrder" method="get">
  <label for="orderId">Search by Order ID:</label>
  <input type="text" id="orderId" name="orderId" placeholder="Enter Order ID">
  <input type="submit" value="Search by ID">
</form>

<br>

<!-- 🔍 搜索框：按日期 -->
<form action="<%= request.getContextPath() %>/viewOrder" method="get">
  <label for="orderDate">Search by Date:</label>
  <input type="date" id="orderDate" name="orderDate">
  <input type="submit" value="Search by Date">
</form>

<br>

<!-- 🛑 显示提示消息（如果有） -->
<%
  String message = (String) request.getAttribute("message");
  if (message != null) {
%>
<p style="color:red;"><%= message %></p>
<%
  }
%>

<!-- 📋 展示订单列表 -->
<%
  List<Order> orders = (List<Order>) request.getAttribute("orders"); // ✅ 改这里
  if (orders != null && !orders.isEmpty()) {
%>
<table border="1">
  <tr>
    <th>Order ID</th>
    <th style="width:180px;">Create Date</th>
    <th>Status</th>
  </tr>
  <%
    for (Order order : orders) {
  %>
  <tr>
    <td>
      <a href="<%= request.getContextPath() %>/viewOrderDetails?orderId=<%= order.getOrderId() %>">
        <%= order.getOrderId() %>
      </a>
    </td>
    <td><%= order.getCreateDate() %></td>
    <td><%= order.getOrderStatus() %></td>
  </tr>
  <%
    }
  %>
</table>
<%
} else {
%>
<p>No matching orders found.</p>
<%
  }
%>

</body>
</html>
