<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 06/05/2025
  Time: 12:53 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="com.bean.Product" %>
<%@ page import="com.bean.Category" %>

<html>
  <%
    List<Product> allProducts = (List<Product>) request.getAttribute("allProducts");
    List<Category> categories = (List<Category>) request.getAttribute("categories");

    //from Servlet GetByProductName
    Product product = (Product) request.getAttribute("product");
    String message = (String) request.getAttribute("message");
%>
<head>
  <title>Admin Dashboard</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/base.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/sideBar.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/ProductManagement.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<h1>Product</h1>
<% if (product != null) { %>
  <a class="shop_product">
    <img src="<%= request.getContextPath() %>/assets/img/<%= product.getImage() %>" alt="Device">
    <h5><%= product.getProductName() %></h5>
    <p><%= product.getDescription() %></p>
    <span>$<%= product.getPrice() %></span>
    <h5>Category: <%= product.getCategory().getCategory()%></h5>

  <div class="update-product-info">
    <h4>Update Product Info</h4>
    <form action="<%= request.getContextPath() %>/UpdateProductServlet" method="post">
      <input type="hidden" name="productId" value="<%= product.getProductId() %>">
      Name: <input type="text" name="productName" value="<%= product.getProductName() %>">
      Price: <input type="text" name="price" value="<%= product.getPrice() %>">
      Quantity: <input type="number" name="quantity" value="<%= product.getQuantity() %>">
      Description: <textarea name="description"><%= product.getDescription() %></textarea>
      Image: <input type="text" name="image" value="<%=product.getImage()%>" />
      <%
        //product.getCategory -> receives category obj
        /*
          product.getCategory() -> returns the obj addr not the string (Category name)
          product db stores categoryID / product class stores category obj
        * */
        Category category = new Category();
        category = product.getCategory();
        int categoryID = category.getCategoryId();
      %>
      Category ID:<input type="number" name="categoryId" value="<%=categoryID%>"/>
      <p>Category :<%=product.getCategory().getCategory()%></p>
      <button type="submit">Update</button>
    </form>
  </div>

    <div class="delete-product">
      <form action="<%= request.getContextPath() %>/DeleteProduct" method="post" onsubmit="return confirm('Are you sure you want to delete it permanently remove this product?')">
        <input type="hidden" name="productId" value="<%= product.getProductId() %>">
        <button type="submit">Delete</button>
      </form>
    </div>
  </a>
<%} else {%>
  <h2><%=message%></h2>
<%}%>

</body>
</html>
