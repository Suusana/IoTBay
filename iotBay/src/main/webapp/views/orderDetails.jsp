<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Order" %>

<html>
<head>
  <title>Order Details</title>
</head>
<body>

<h2>Order Details</h2>

<%
  Order order = (Order) request.getAttribute("order");
  if (order != null) {
%>

<table border="1">
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
  <tr>
    <th>Buyer</th>
    <td><%= order.getBuyer() != null ? order.getBuyer().getFirstName() : "N/A" %></td>
  </tr>
  <tr>
    <th>Products</th>
    <td>
      <ul>
        <%
          Integer[] productIds = order.getProductIds();
          if (productIds != null && productIds.length > 0) {
            for (Integer productId : productIds) {
        %>
        <li>Product ID: <%= productId %></li>
        <%
          }
        } else {
        %>
        <li>No products found.</li>
        <%
          }
        %>
      </ul>
    </td>
  </tr>
</table>

<br>

<!-- ✅ 仅当状态为 Saved 时显示 Manage 按钮 -->
<%
  if ("Saved".equals(order.getOrderStatus().toString())) {
%>
<form action="<%= request.getContextPath() %>/manageOrder" method="get" style="display:inline;">
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
