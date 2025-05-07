<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Order" %>
<%@ page import="com.bean.Product" %>
<%@ page import="java.util.List" %>

<html>
<head>
  <title>Manage Order</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/OrderStyle.css">
</head>
<body>

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
        <!--quantity must > 0-->
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

</body>
</html>
