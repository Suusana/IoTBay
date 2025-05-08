<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 08/05/2025
  Time: 2:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.bean.Product" %>
<%@ page import="com.bean.Category" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product Detail Update</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/sideBar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/StaffDetail.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<div class="sideBar">
    <h2>Admin Panel</h2>
    <a href="<%= request.getContextPath() %>/views/AdminDashboard.jsp">
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
    <h1>Product Detail</h1>
    <%
        Product product = (Product) request.getAttribute("product");
    %>
    <div class="card">
        <%--        profile icon--%>
        <img src="<%= request.getContextPath() %>/assets/img/<%= product.getImage() %>" alt="Device">

        <div class="info-section">
            <h2><%=product.getProductName()%></h2>

            <div class="info-row">
                <div>Price</div>
                <div class="info-value"><%=product.getPrice()%></div>
            </div>

            <div class="info-row">
                <div>Description</div>
                <div class="info-value"><%=product.getDescription()%></div>
            </div>

            <div class="info-row">
                <div>Quantity</div>
                <div class="info-value"><%=product.getQuantity()%></div>
            </div>

            <div class="info-row">
                <div>Image</div>
                <div class="info-value"><%=product.getImage()%></div>
            </div>

            <div class="info-row">
                <div class="info-label">Category</div>
                <div class="info-value"><%=product.getCategory().getCategory()%></div>
            </div>

        </div>
    </div>

    <a href="<%= request.getContextPath() %>/ProductManagementServlet">
        <button class="back-button">Back to Product Management</button>
    </a>
</div>


</body>
</html>
