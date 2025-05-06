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
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/sideBar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/ProductManagement.css">
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
    <a href="<%= request.getContextPath() %>/ProductManagementServlet" class="current">
        <i class="fa-solid fa-user-tie fa-lg"></i>
        <span>Product Management</span>
    </a>
    <a href="#">
        <i class="fa-solid fa-right-from-bracket fa-lg"></i>
        <span>Logout</span>
    </a>
</div>

<div class="main-content">
    <h1>Product Management</h1>
    <div class="search-box">
        <h2>Search Products</h2>
        <div class="search-box-name">
            <h3>Search by Name</h3>
            <form action="<%= request.getContextPath() %>/GetByProductName" method="get" target="_blank">
                <label for="productName">
                    <input type="search" id="productName" name="productName" />
                </label>
                <button>Search</button>
            </form>
        </div>
        <div class="search-box-category">
            <h3>Search by Category</h3>
            <form action="<%= request.getContextPath() %>/GetByCategory" method="get" target="_blank">
                <button type="submit" name="categoryId" value="1">Smart Home</button>
                <button type="submit" name="categoryId" value="2">Health & Fitness</button>
                <button type="submit" name="categoryId" value="3">Security Devices</button>
                <button type="submit" name="categoryId" value="4">Industrial Devices</button>
                <button type="submit" name="categoryId" value="5">Wearables</button>
                <button type="submit" name="categoryId" value="6">Agriculture & Environment</button>
                <button type="submit" name="categoryId" value="7">Automotive & Transport</button>
                <button type="submit" name="categoryId" value="8">Smart Appliances</button>
                <button type="submit" name="categoryId" value="9">Energy & Utilities</button>
                <button type="submit" name="categoryId" value="10">Networking & Hubs</button>
            </form>
        </div>

    </div>
    <div class="add-product-box">
        <h2>Add New Product</h2>
        <form action="<%= request.getContextPath() %>/AddNewProduct" method="post">
            <label for="productName">
                Name: <input type="text" name="productName">
            </label>
            <label for="price">
                Price: <input type="text" name="price">
            </label>
            <label for="quantity">
                Quantity: <input type="number" name="quantity">
            </label>
            <label for="description">
                Description: <textarea name="description"></textarea>
            </label>
            <button type="submit">Add New Product</button>
        </form>
    </div>
    <h2>Product List</h2>
    <% if (allProducts != null && !allProducts.isEmpty()) {
        for (Product product : allProducts) { %>
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
                <br>
                Description: <textarea name="description"><%= product.getDescription() %></textarea>
                <br>
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
            <form action="<%= request.getContextPath() %>/DeleteProduct" method="post" onsubmit="return confirm('Are you sure you want to delete this product?');">
                <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                <button type="submit">Delete</button>
            </form>
        </div>
    </a>
    <% }
    } else { %>
    <p>No products available right now.</p> <!--this will appear when there is an servlet connection error or nothing to show --->
    <% } %>
</div> <!--This is end of the .main-content div -->


</body>
</html>


</div>
</body>
</html>
