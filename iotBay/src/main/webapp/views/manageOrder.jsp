<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Order" %>

<html>
<head>
  <title>Manage Order</title>
</head>
<body>

<h2>Manage Your Order</h2>

<%
  Order order = (Order) request.getAttribute("order");
  if (order != null) {
%>

<!-- 🧾 显示订单基本信息 -->
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
</table>

<br>

<%
  // 只允许修改 "Saved" 状态的订单
  if ("Saved".equals(order.getOrderStatus().toString())) {
%>

<!-- 🛠 修改数量 / 提交 / 取消 -->
<form action="orderAction" method="post">
  <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">

  <h3>Update Product Quantities:</h3>

  <table border="1">
    <tr>
      <th>Product ID</th>
      <th>Quantity</th>
    </tr>

    <%
      Integer[] productIds = order.getProductIds();
      if (productIds != null && productIds.length > 0) {
        for (Integer productId : productIds) {
    %>
    <tr>
      <td><%= productId %></td>
      <td>
        <input type="number" name="quantity_<%= productId %>" value="1" min="0" style="width: 60px; text-align: center;">
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

  <br>

  <button type="submit" name="action" value="update">Save Update</button>
  <button type="submit" name="action" value="submit">Final Submission</button>
  <button type="submit" name="action" value="cancel">Cancel Order</button>
</form>

<%
} else {
%>

<p>This order is <strong><%= order.getOrderStatus() %></strong> and cannot be modified.</p>

<%
  }
%>

<br><br>
<a href="viewOrder">Back to Order List</a>

<%
} else {
%>
<p>No order information available.</p>
<%
  }
%>

</body>
</html>
