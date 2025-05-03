<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Order" %>
<%@ page import="com.bean.Product" %>
<%@ page import="java.util.List" %>

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

<!-- 🛠 修改商品数量、提交订单、取消订单 -->
<form action="<%= request.getContextPath() %>/orderAction" method="post">
  <input type="hidden" name="orderId" value="<%= order.getOrderId() %>">

  <h3>Update Product Quantities:</h3>
  <table border="1">
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
        <input type="number" name="quantity_<%= productId %>" value="<%= product.getQuantity() %>" min="0" style="width: 60px; text-align: center;">
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
