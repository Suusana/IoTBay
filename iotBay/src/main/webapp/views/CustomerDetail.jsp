<%@ page import="com.bean.Customer" %><%--
  Created by IntelliJ IDEA.
  User: Susana
  Date: 5/10/2025
  Time: 0:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Details</title>
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
    <a href="<%= request.getContextPath() %>/ShowStaffInfo" class="current">
        <i class="fa-solid fa-user-tie fa-lg"></i>
        <span>Staff Management</span>
    </a>
    <a href="#">
        <i class="fa-solid fa-right-from-bracket fa-lg"></i>
        <span>Logout</span>
    </a>
</div>

<div class="main-content">
    <h1>Staff Detail</h1>
    <%
        Customer customer = (Customer) request.getAttribute("customer");
    %>
    <div class="card">
        <%--        profile icon--%>
        <i class="fa-solid fa-circle-user avatar"></i>

        <div class="info-section">
            <h2><%=customer.getFirstName()%> <%=customer.getLastName()%></h2>

            <div class="info-row">
                <div>Customer ID:</div>
                <div class="info-value"><%=customer.getUserId()%></div>
            </div>

            <div class="info-row">
                <div>Username:</div>
                <div class="info-value"><%=customer.getUsername()%></div>
            </div>

            <div class="info-row">
                <div>Phone Number:</div>
                <div class="info-value"><%=customer.getPhone()%></div>
            </div>

            <div class="info-row">
                <div>Email:</div>
                <div class="info-value"><%=customer.getEmail()%></div>
            </div>

            <div class="info-row">
                <div class="info-label">Type:</div>
                <div class="info-value"><%=customer.getType()%></div>
            </div>

            <div class="info-row">
                <div class="info-label">Status:</div>
                <div class="info-value"><%=customer.getStatus()%></div>
            </div>

            <div class="info-row">
                <div>Address:</div>
                <div class="info-value"><%=customer.getAddress()%></div>
            </div>

            <div class="info-row">
                <div>City:</div>
                <div class="info-value"><%=customer.getCity()%></div>
            </div>

            <div class="info-row">
                <div>Postcode:</div>
                <div class="info-value"><%=customer.getPostcode()%></div>
            </div>

            <div class="info-row">
                <div>State:</div>
                <div class="info-value"><%=customer.getState()%></div>
            </div>

            <div class="info-row">
                <div>Country:</div>
                <div class="info-value"><%=customer.getCountry()%></div>
            </div>
        </div>
    </div>

    <button class="back-button" onclick="history.back()">Back to Customer Management</button>
</div>

</div>
</body>
</html>
