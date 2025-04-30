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
<form action="viewOrder" method="get">
  <label for="orderId">Search by Order ID:</label>
  <input type="text" id="orderId" name="orderId" placeholder="Enter Order ID">
  <input type="submit" value="Search by ID">
</form>

<br>

<!-- 🔍 搜索框：按日期 -->
<form action="viewOrder" method="get">
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
  List<Order> orderList = (List<Order>) request.getAttribute("orderList");
  if (orderList != null && !orderList.isEmpty()) {
%>
<table border="1">
  <tr>
    <th>Order ID</th>
    <th>Create Date</th>
    <th>Status</th>
    <th>Product IDs</th>
    <th>Actions</th>
  </tr>
  <%
    for (Order order : orderList) {
  %>
  <tr>
    <td>
      <!-- 点击订单号也可进入详情 -->
      <a href="viewOrderDetails?orderId=<%= order.getOrderId() %>">
        <%= order.getOrderId() %>
      </a>
    </td>
    <td><%= order.getCreateDate() %></td>
    <td><%= order.getOrderStatus() %></td>
    <td>
      <%
        Integer[] productIds = order.getProductIds();
        if (productIds != null && productIds.length > 0) {
          for (int i = 0; i < productIds.length; i++) {
      %>
      <%= productIds[i] %><%= (i < productIds.length - 1) ? ", " : "" %>
      <%
        }
      } else {
      %>
      No products.
      <%
        }
      %>
    </td>
    <td>
      <!-- 🔍 只能进入详情页，不能直接 manage -->
      <a href="viewOrderDetails?orderId=<%= order.getOrderId() %>">Details</a>
    </td>
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
