<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 06/05/2025
  Time: 8:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Customer" %>
<%@ page import="com.enums.Status" %>
<%@ page import="com.bean.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.util.Utils" %>
<html>
<%
    Customer customer = new Customer();
    if (session.getAttribute("loggedInUser") != null) {
        customer = (Customer) session.getAttribute("loggedInUser");
    } else {
        customer.setUsername(Status.GUEST.getStatus());
    }

    List<Product> searchAllProducts = (List<Product>) request.getAttribute("searchAllProducts");
    String message = (String) request.getAttribute("message");
%>
<head>
    <title>Search</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/HeaderAndFooter.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/search.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>

<body>
<!-- header -->
<div class="header">
    <!-- Logo -->
    <a href="<%=request.getContextPath()%>/home">
        <img src="<%=request.getContextPath()%>/assets/img/Logo.png" alt="IotBay Logo">
    </a>
    <!-- menu -->
    <menu>
        <a href="<%= request.getContextPath()%>/home"><span>Home</span></a>
        <a href="<%= request.getContextPath() %>/productServlet"><span>Shop</span></a>
        <a href="<%= request.getContextPath() %>/viewOrder"><span>Order</span></a>
        <a href="#"><span>Category</span></a>
    </menu>

    <!-- icon menu -->
    <menu class="icon">
        <a href="<%=request.getContextPath()%>/ViewUserDetailsServlet">
            <i class="fa-solid fa-circle-user fa-2x"></i>
            <span><%= customer.getFirstName() != null ? Utils.capitaliseFirst(customer.getFirstName()) : Status.GUEST.getStatus()%></span>
        </a>
        <a href="<%=request.getContextPath()%>/GetByProductNameToCustomer" class="selected">
            <i class="fa-solid fa-magnifying-glass fa-2x"></i>
            <span>Search</span>
        </a>
        <a href="#">
            <i class="fa-solid fa-cart-shopping fa-2x"></i>
            <span>Cart</span>
        </a>
        <%
            if (session.getAttribute("loggedInUser") != null) {
        %>
        <a href="<%=request.getContextPath()%>/views/logout.jsp">
            <i class="fa-solid fa-right-from-bracket fa-2x"></i>
            <span>Log Out</span>
        </a>
        <%
            }
        %>
    </menu>
</div>

<!-- main body -->
<main>
    <div class="search-box">
        <h1>Search Products</h1>
        <div class="search-box-name">
            <h3>Search by Name</h3>
            <form action="<%= request.getContextPath() %>/GetByProductNameToCustomer" method="get">
                <label for="productName">
                    <input type="search" id="productName" name="productName" required/>
                </label>
                <button>Search</button>
            </form>
        </div>
        <div class="search-box-category">
            <h3>Search by Category</h3>
            <form action="<%= request.getContextPath() %>/GetByCategoryToCustomer" method="get" target="_blank">
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
    <br>
    <div class="search-name-result">
        <h1>Product</h1>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Category</th>
                <th>Image</th>
                <th>Description</th>
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
                <td><%= product.getCategory().getCategory()%>
                </td>
                <td><img src="<%= request.getContextPath() %>/assets/img/<%= product.getImage() %>" alt="Device">
                </td>
                <td>
                    <%= product.getDescription()%>

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

</main>

<!-- footer -->
<div class="footer">
    <hr>
    <div>
        <div class="section">
            <h6 id="dif">IoTBay</h6><br>
            <span>The most complete range of IoT devices to upgrade your life at the touch of a button.</span>
        </div>
        <div class="section">
            <h6>Links</h6>
            <a href="<%=request.getContextPath()%>/home"><span>Home</span></a>
            <a href="<%=request.getContextPath()%>/productServlet"><span>Shop</span></a>
            <a href="<%=request.getContextPath()%>/viewOrder"><span>Order</span></a>
            <a href="#"><span>Category</span></a>
        </div>
        <div class="section">
            <h6>Contact Us</h6>
            <span>Address: 123 IotBay, Sydney</span>
            <span>Phone Number: +61 0499999999</span>
            <span>Email Address: IotBay@example.com</span>
        </div>
        <div class="section">
            <h6>Follow Us</h6>
            <a href="https://www.instagram.com/">
                <i class="fa-brands fa-instagram fa-lg"></i>
                <span>Instagram</span>
            </a>
            <a href="https://www.facebook.com/">
                <i class="fa-brands fa-facebook fa-lg"></i>
                <span>Facebook</span>
            </a>
            <a href="https://discord.com/">
                <i class="fa-brands fa-discord fa-lg"></i>
                <span>Discord</span>
            </a>
            <a href="https://x.com/?lang=en">
                <i class="fa-brands fa-x-twitter fa-lg"></i>
                <span>Twitter</span>
            </a>
        </div>
    </div>
    <hr>
    <p>©2025. IoTBay Group 4 All Right Reserved</p>
</div>
</body>
</html>
