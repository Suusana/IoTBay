<%@ page import="com.bean.Staff" %><%--
  Created by IntelliJ IDEA.
  User: Susana
  Date: 4/28/2025
  Time: 1:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Staff Details</title>
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
        Staff staff = (Staff) request.getAttribute("staff");
    %>
    <div class="card">
<%--        profile icon--%>
        <i class="fa-solid fa-circle-user avatar"></i>

        <div class="info-section">
            <h2><%=staff.getStaffName()%></h2>

            <div class="info-row">
                <div>Staff ID:</div>
                <div class="info-value"><%=staff.getStaffId()%></div>
            </div>

            <div class="info-row">
                <div>Phone Number:</div>
                <div class="info-value"><%=staff.getPhoneNum()%></div>
            </div>

            <div class="info-row">
                <div>Email:</div>
                <div class="info-value"><%=staff.getEmail()%></div>
            </div>

            <div class="info-row">
                <div>Position:</div>
                <div class="info-value"><%=staff.getPosition()%></div>
            </div>

            <div class="info-row">
                <div class="info-label">Status:</div>
                <div class="info-value"><%=staff.getStatus()%></div>
            </div>

            <div class="info-row">
                <div>Address:</div>
                <div class="info-value"><%=staff.getAddress()%></div>
            </div>

            <div class="info-row">
                <div>City:</div>
                <div class="info-value"><%=staff.getCity()%></div>
            </div>

            <div class="info-row">
                <div>Postcode:</div>
                <div class="info-value"><%=staff.getPostcode()%></div>
            </div>

            <div class="info-row">
                <div>State:</div>
                <div class="info-value"><%=staff.getState()%></div>
            </div>

            <div class="info-row">
                <div>Country:</div>
                <div class="info-value"><%=staff.getCountry()%></div>
            </div>
        </div>
    </div>

    <button class="back-button" onclick="history.back()">Back to Staff Management</button>
    </div>

</div>
</body>
</html>
