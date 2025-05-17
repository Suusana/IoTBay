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
<%@ page import="java.util.stream.Stream" %>
<%@ page import="com.bean.Staff" %>
<html>
<%
    List<Product> allProducts = (List<Product>) request.getAttribute("allProducts");
    List<Category> categories = (List<Category>) request.getAttribute("categories");
    Integer CategoryLen = (Integer) request.getAttribute("CategoryLen");
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
    <h4>Current Staff: <%= staff.getStaffName()%></h4>
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
    <h1>Product Management</h1>
    <div class="search-box">
        <h2>Search Products</h2>
        <div class="search-box-name">
            <h3>Search by Name</h3>
            <form action="<%= request.getContextPath() %>/GetByProductName" method="get" target="_blank">
                <label for="productName">
                    <input type="search" id="productName" name="productName"/>
                </label>
                <button>Search</button>
            </form>
        </div>
        <div class="search-box-category">
            <h3>Search by Category</h3>
            <form action="<%= request.getContextPath() %>/GetByCategory" method="get">
                <select name="categoryId">
                    <option disabled>Please select a category</option>
                    <option name="categoryId" value="1">Smart Home</option>
                    <option name="categoryId" value="2">Health & Fitness</option>
                    <option name="categoryId" value="3">Security Devices</option>
                    <option name="categoryId" value="4">Industrial Devices</option>
                    <option name="categoryId" value="5">Wearables</option>
                    <option name="categoryId" value="6">Agriculture & Environment</option>
                    <option name="categoryId" value="7">Automotive & Transport</option>
                    <option name="categoryId" value="8">Smart Appliances</option>
                    <option name="categoryId" value="9">Energy & Utilities</option>
                    <option name="categoryId" value="10">Networking & Hubs</option>
                </select>
                <button type="submit">Search</button>
            </form>

        </div>

    </div>
    <div class="add-product-box">
        <h2>Add New Product</h2>
        <form action="<%= request.getContextPath() %>/AddNewProduct" method="post" enctype="multipart/form-data">
            <label>Name: <input placeholder="Product Name" type="text" name="productName" required></label>
            <label>Price: <input placeholder="Price" type="number" min="0" step="0.01" name="price" required></label>
            <label>Quantity: <input placeholder="Stock" type="number" min="0" name="quantity" required></label>
            <label>Description: <textarea placeholder="Product details" name="description"></textarea></label>
            <label>Category ID:<input placeholder="1 - 10" type="number" name="categoryId" min="1" max="<%=CategoryLen%>" required/></label>
            <label class="img">Image:<input type="file" name="image" value=""/></label><br>
            <button type="submit">Add New Product</button>
        </form>
    </div>
    <h2>Product List</h2>

    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Category</th>
            <th>Description</th>
            <th>Image</th>
            <th>Operation</th>
        </tr>
        </thead>
        <tbody>

        <% if (allProducts != null && !allProducts.isEmpty()) {
            for (Product product : allProducts) { %>
        <tr>
            <td><%= product.getProductId()%>
            </td>
            <td><%= product.getProductName()%>
            </td>
            <td><%= product.getPrice()%>
            </td>
            <td><%= product.getQuantity()%>
            </td>
            <td>
                <%=product.getCategory().getCategory()%>
            </td>
            <td><%= product.getDescription()%>
            </td>
            <td><img src="<%= request.getContextPath() %>/assets/img/<%= product.getImage() %>" alt="Device">
            </td>
            <td class="operation">

                    <form action="<%= request.getContextPath() %>/ProductDetailAdminServlet" method="get">
                        <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                        <button type="submit" name="view" value="view">View</button>
                    </form>

                    <form action="<%= request.getContextPath() %>/UpdateProductServlet" method="get">
                        <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                        <button type="submit" name="update" class="update">Update</button>
                    </form>

                    <form action="<%= request.getContextPath() %>/DeleteProduct" method="post" onsubmit="return confirm('Are you sure you want to delete it permanently remove this product?')">
                        <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                        <button class="delete" type="submit">Delete</button>
                    </form>

            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>
<script>
<%
    Boolean exists = (Boolean) session.getAttribute("exists");
    if(exists != null && exists){
        session.removeAttribute("exists");
%>
    alert("This product already exist!")
<%
    }
    Boolean categoryLenValidation = (Boolean) session.getAttribute("categoryLenValidation");
    if(categoryLenValidation!=null && categoryLenValidation){
        session.removeAttribute("categoryLenValidation");
%>
    alert("Category number out of range it should be 1 - 10");
<%
    }
%></script>
</body>
</html>
