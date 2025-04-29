<%--
  Created by IntelliJ IDEA.
  User: Susana
  Date: 4/25/2025
  Time: 12:27
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
%>

<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="../assets/css/base.css">
    <link rel="stylesheet" href="../assets/css/sideBar.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<div class="sideBar">
    <h2>Admin Panel</h2>
    <a href="./AdminDashboard.jsp">
        <i class="fa-solid fa-house fa-lg"></i>
        <span>Dashboard</span>
    </a>
    <a href="./CustomerManagement.jsp">
        <i class="fa-solid fa-user fa-lg"></i>
        <span>Customer Management</span>
    </a>
    <a href="<%= request.getContextPath() %>/ShowStaffInfo">
        <i class="fa-solid fa-user-tie fa-lg"></i>
        <span>Staff Management</span>
    </a>
    <a href="./ProductManagement.jsp" class="current">
        <i class="fa-solid fa-user-tie fa-lg"></i>
        <span>Product Management</span>
    </a>
    <a href="#">
        <i class="fa-solid fa-right-from-bracket fa-lg"></i>
        <span>Logout</span>
    </a>
</div>

<div class="main-content">
    <h1>Product</h1>
    <div>
        <h2>Add New Product</h2>
        <form action="<%= request.getContextPath() %>/AddNewProduct" method="post">
            Name: <input type="text" name="productName"><br>
            Price: <input type="text" name="price"><br>
            Quantity: <input type="number" name="quantity"><br>
            Description: <textarea name="description"></textarea><br>
            <button type="submit">Add New Product</button>
        </form>
    </div>
    <% if (allProducts != null && !allProducts.isEmpty()) {
        for (Product product : allProducts) { %>
    <a class="shop_product">
        <img src="<%= request.getContextPath() %>/assets/img/<%= product.getImage() %>" alt="Device">
        <h5><%= product.getProductName() %></h5>
        <p><%= product.getDescription() %></p>
        <span>$<%= product.getPrice() %></span>
        <div>
            <h3>Update Product Info</h3>
            <form action="<%= request.getContextPath() %>/UpdateProductServlet" method="post">
                <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                Name: <input type="text" name="productName" value="<%= product.getProductName() %>"><br>
                Price: <input type="text" name="price" value="<%= product.getPrice() %>"><br>
                Quantity: <input type="number" name="quantity" value="<%= product.getQuantity() %>"><br>
                Description: <textarea name="description"><%= product.getDescription() %></textarea><br>
                <button type="submit">Update</button>
            </form>
        </div>

        <div>
            <form action="<%= request.getContextPath() %>/DeleteProduct" method="post" onsubmit="return confirm('Are you sure you want to delete this product?');">
                <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                <button type="submit">Delete</button>
            </form>
        </div>

    </a>
    <% }
    } else { %>
    <p>No products available right now.</p>
    <% } %>


</div>
</body>
</html>


</div>
</body>
</html>
