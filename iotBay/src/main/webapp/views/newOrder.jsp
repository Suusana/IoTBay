<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bean.Product" %>

<html>
<head>
  <title>Create Order</title>
</head>
<body>

<h2>Create a New Order</h2>

<!-- ✅ 表单提交路径统一加 contextPath -->
<form action="<%= request.getContextPath() %>/createOrder" method="post">
  <input type="hidden" name="action" value="create">

  <table border="1">
    <tr>
      <th>Product Name</th>
      <th>Quantity</th>
    </tr>

    <%
      List<Product> productList = (List<Product>) request.getAttribute("productList");
      if (productList != null && !productList.isEmpty()) {
        for (Product product : productList) {
    %>
    <tr>
      <td><%= product.getProductName() %></td>
      <td>
        <input type="number" name="quantity_<%= product.getProductId() %>" value="0" min="0" style="width: 60px;">
      </td>
    </tr>
    <%
      }
    } else {
    %>
    <tr><td colspan="2">No products available.</td></tr>
    <%
      }
    %>
  </table>

  <br>
  <input type="submit" value="Submit Order">
</form>

</body>
</html>
