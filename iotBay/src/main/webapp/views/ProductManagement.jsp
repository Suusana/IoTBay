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
            <form action="<%= request.getContextPath() %>/GetByCategory" method="get" target="_blank">
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
        <form action="<%= request.getContextPath() %>/AddNewProduct" method="post">
            <label>Name: <input type="text" name="productName" required></label>
            <label>Price: <input type="text" name="price" required></label>
            <label>Quantity: <input type="number" name="quantity" required></label>
            <label>Description: <textarea name="description"></textarea></label>
            <label>Category ID:<input type="number" name="categoryId" required/></label>
            <label>Image:<input type="text" name="image" value=""/></label>
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


    <%--    <a class="shop_product">--%>

    <%--        <h5><%= product.getProductName() %></h5>--%>
    <%--        <p><%= product.getDescription() %></p>--%>
    <%--        <span>$<%= product.getPrice() %></span>--%>
    <%--        <h5>Category: <%= product.getCategory().getCategory()%></h5>--%>
    <%--        <div class="update-product-info">--%>
    <%--            <h4>Update Product Info</h4>--%>
    <%--            <form action="<%= request.getContextPath() %>/UpdateProductServlet" method="post">--%>
    <%--                <input type="hidden" name="productId" value="<%= product.getProductId() %>">--%>
    <%--                Name: <input type="text" name="productName" value="<%= product.getProductName() %>">--%>
    <%--                Price: <input type="text" name="price" value="<%= product.getPrice() %>">--%>
    <%--                Quantity: <input type="number" name="quantity" value="<%= product.getQuantity() %>">--%>
    <%--                <br>--%>
    <%--                Description: <textarea name="description"><%= product.getDescription() %></textarea>--%>
    <%--                <br>--%>
    <%--                <%--%>
    <%--                    //product.getCategory -> receives category obj--%>
    <%--        /*--%>
    <%--          product.getCategory() -> returns the obj addr not the string (Category name)--%>
    <%--          product db stores categoryID / product class stores category obj--%>
    <%--        * */--%>
    <%--                    Category category = new Category();--%>
    <%--                    category = product.getCategory();--%>
    <%--                    int categoryID = category.getCategoryId();--%>
    <%--                %>--%>
    <%--                Category ID:<input type="number" name="categoryId" value="<%=categoryID%>"/>--%>
    <%--                <p>Category :<%=product.getCategory().getCategory()%></p>--%>
    <%--                Image:<input type="text" name="image" value="<%=product.getImage()%>"/>--%>
    <%--                <button type="submit">Update</button>--%>
    <%--            </form>--%>
    <%--        </div>--%>
    <%--        <!-- delete test--%>
    <%--     ('Capacitive Touch Sensor Module v2.0', 30, 18.90, 'Capacitive touch sensor module used for detecting touch input in Arduino, Raspberry Pi, and interactive projects.', 'Touch Sensor.png', 1),--%>
    <%--        -->--%>
    <%--        <div class="delete-product">--%>
    <%--            <form action="<%= request.getContextPath() %>/DeleteProduct" method="post" onsubmit="return confirm('Are you sure you want to delete it permanently remove this product?')">--%>
    <%--                <input type="hidden" name="productId" value="<%= product.getProductId() %>">--%>
    <%--                <button type="submit">Delete</button>--%>
    <%--            </form>--%>
    <%--        </div>--%>
    <%--        <%--%>
    <%--        String deletedMssg = (String) session.getAttribute("deletedSuccess");--%>
    <%--        if(deletedMssg !=null){%>--%>
    <%--        <div class="deletedMssg">--%>
    <%--            <%=deletedMssg%>--%>
    <%--        </div>--%>
    <%--        <%--%>
    <%--            session.removeAttribute("deletedSuccess");--%>
    <%--        }%>--%>
    <%--    </a>--%>
    <%--    <% }--%>
    <%--    } else { %>--%>
    <%--    <p>No products available right now.</p> <!--this will appear when there is an servlet connection error or nothing to show --->--%>
    <%--    <% } %>--%>
</div> <!--This is end of the .main-content div -->

</body>
</html>
