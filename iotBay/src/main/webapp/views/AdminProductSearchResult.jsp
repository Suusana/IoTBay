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
<%@ page import="javax.management.remote.JMXServerErrorException" %>
<%@ page import="com.bean.Staff" %>

<html>
<%
    List<Product> searchAllProducts = (List<Product>) request.getAttribute("searchAllProducts");
    //List<Category> categories = (List<Category>) request.getAttribute("categories");
    String message = (String) request.getAttribute("error");
%>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/sideBar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/ProductManagement.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<%
    Staff staff = (Staff) session.getAttribute("loggedInUser");
%>
<div class="sideBar">
    <h2>Admin Panel</h2>
    <h4>Current Staff: <%= staff.getStaffName()%>
    </h4>
    <a href="<%= request.getContextPath() %>/ShowCustomerInfo">
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
    <a href="<%=request.getContextPath()%>/ViewUserDetailsServlet">
        <i class="fa-solid fa-user-tie fa-lg"></i>
        <span>My Details Management</span>
    </a>
    <a href="<%=request.getContextPath()%>/views/logout.jsp">
        <i class="fa-solid fa-right-from-bracket fa-lg"></i>
        <span>Logout</span>
    </a>
</div>
<div class="main-content">
    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Description</th>
            <th>Image</th>
            <th>Operation</th>
        </tr>
        </thead>
        <tbody>

        <% if (searchAllProducts != null && !searchAllProducts.isEmpty()) {
            for (Product product : searchAllProducts) { %>
        <tr>
            <td><%= product.getProductId()%>
            </td>
            <td><%= product.getProductName()%>
            </td>
            <td><%= product.getPrice()%>
            </td>
            <td><%= product.getQuantity()%>
            </td>
            <td><%= product.getDescription()%>
            </td>
            <td><img src="<%= request.getContextPath() %>/assets/img/<%= product.getImage() %>" alt="Device">
            </td>
            <td class="operation">

                <form action="<%= request.getContextPath() %>/ProductDetailAdminServlet" method="get" target="_blank">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <button type="submit" name="view" value="view">View</button>
                </form>

                <form action="<%= request.getContextPath() %>/UpdateProductServlet" method="get" target="_blank">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <button type="submit" name="update" class="update">Update</button>
                </form>

                <form action="<%= request.getContextPath() %>/DeleteProduct" method="post"
                      onsubmit="return confirm('Are you sure you want to delete it permanently remove this product?')">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <button class="delete" type="submit">Delete</button>
                </form>

            </td>
        </tr>
        <%
            }
        } else if (message != null) {
        %> <h3>Error: <%=message%>
        </h3>
        <%} else {%>
        <h3>No result</h3>
        <%}%>
        </tbody>
    </table>
</div>


<%--<h1>Product</h1>--%>
<%--<% if (product != null) { %>--%>
<%--  <a class="shop_product">--%>
<%--    <img src="<%= request.getContextPath() %>/assets/img/<%= product.getImage() %>" alt="Device">--%>
<%--    <h5><%= product.getProductName() %></h5>--%>
<%--    <p><%= product.getDescription() %></p>--%>
<%--    <span>$<%= product.getPrice() %></span>--%>
<%--    <h5>Category: <%= product.getCategory().getCategory()%></h5>--%>

<%--  <div class="update-product-info">--%>
<%--    <h4>Update Product Info</h4>--%>
<%--    <form action="<%= request.getContextPath() %>/UpdateProductServlet" method="post">--%>
<%--      <input type="hidden" name="productId" value="<%= product.getProductId() %>">--%>
<%--      Name: <input type="text" name="productName" value="<%= product.getProductName() %>">--%>
<%--      Price: <input type="text" name="price" value="<%= product.getPrice() %>">--%>
<%--      Quantity: <input type="number" name="quantity" value="<%= product.getQuantity() %>">--%>
<%--      Description: <textarea name="description"><%= product.getDescription() %></textarea>--%>
<%--      Image: <input type="text" name="image" value="<%=product.getImage()%>" />--%>
<%--      <%--%>
<%--        //product.getCategory -> receives category obj--%>
<%--        /*--%>
<%--          product.getCategory() -> returns the obj addr not the string (Category name)--%>
<%--          product db stores categoryID / product class stores category obj--%>
<%--        * */--%>
<%--        Category category = new Category();--%>
<%--        category = product.getCategory();--%>
<%--        int categoryID = category.getCategoryId();--%>
<%--      %>--%>
<%--      Category ID:<input type="number" name="categoryId" value="<%=categoryID%>"/>--%>
<%--      <p>Category :<%=product.getCategory().getCategory()%></p>--%>
<%--      <button type="submit">Update</button>--%>
<%--    </form>--%>
<%--  </div>--%>

<%--    <div class="delete-product">--%>
<%--      <form action="<%= request.getContextPath() %>/DeleteProduct" method="post" onsubmit="return confirm('Are you sure you want to delete it permanently remove this product?')">--%>
<%--        <input type="hidden" name="productId" value="<%= product.getProductId() %>">--%>
<%--        <button type="submit">Delete</button>--%>
<%--      </form>--%>
<%--    </div>--%>
<%--  </a>--%>
<%--<%} else {%>--%>
<%--  <h2><%=message%></h2>--%>
<%--<%}%>--%>

</body>
</html>
