<%@ page import="com.bean.Staff" %>
<%@ page import="com.util.Utils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Personal Details</title>
    <link rel="stylesheet" href="../assets/css/base.css">
    <link rel="stylesheet" href="../assets/css/sideBar.css">
    <link rel="stylesheet" href="../assets/css/userDetails.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<%
    Staff staff = new Staff();
    if (session.getAttribute("loggedInUser") != null){
        staff = (Staff)session.getAttribute("loggedInUser");
    } else {
        session.setAttribute("errorMessage", "Please login to view your profile");
        response.sendRedirect(request.getContextPath()+"/views/login.jsp");
    }
%>
<body>
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
    <a href="<%= request.getContextPath() %>/ProductManagementServlet">
        <i class="fa-solid fa-user-tie fa-lg"></i>
        <span>Product Management</span>
    </a>
    <a href="<%=request.getContextPath()%>/ViewUserDetailsServlet" class="current">
        <i class="fa-solid fa-user-tie fa-lg"></i>
        <span>My Details Management</span>
    </a>
    <a href="<%=request.getContextPath()%>/views/logout.jsp">
        <i class="fa-solid fa-right-from-bracket fa-lg"></i>
        <span>Logout</span>
    </a>
</div>

<div class="main-content">
    <h1 style="justify-content: left;">My Personal Details</h1>

    <div class="card">
        <div class="details-display">
            <div class="form-row">
                <%
                    String[] nameParts = staff.getStaffName().trim().split(" ");
                    String staffFName = nameParts[0];
                    String staffLName = nameParts[1];
                %>
                <div class="field">
                    <label>First Name</label>
                    <span><%=staffFName%></span>
                </div>
                <div class="field">
                    <label>Last Name</label>
                    <span><%=staffLName%></span>
                </div>
            </div>
            <div class="form-row">
                <div class="field">
                    <label>Email Address</label>
                    <span><%=staff.getEmail()%></span>
                </div>
            </div>
            <div class="form-row">
                <div class="field">
                    <label>Phone Number</label>
                    <span><%=Utils.formatPhoneNumber(Long.valueOf(staff.getPhoneNum()))%></span>
                </div>
            </div>
            <div class="form-row">
                <div class="field">
                    <label>Address</label>
                    <span><%=staff.getAddress()%></span>
                </div>
                <div class="field">
                    <label>City</label>
                    <span><%=staff.getCity()%></span>
                </div>
            </div>
            <div class="form-row">
                <div class="field">
                    <label>State</label>
                    <span><%=staff.getState()%></span>
                </div>
                <div class="field">
                    <label>Postcode</label>
                    <span><%=staff.getPostcode()%></span>
                </div>
                <div class="field">
                    <label>Country</label>
                    <span><%=staff.getCountry()%></span>
                </div>
            </div>
        </div>

        <div class="account-buttons">
            <a href="<%=request.getContextPath()%>/EditUserDetailsServlet" class="button edit-button">Edit Details</a>
            <a href="<%=request.getContextPath()%>/ViewAccessLogsServlet" class="button view-button">View Account History</a>
            <a href="<%=request.getContextPath()%>/views/deleteAccount.jsp" class="button delete-button">Delete Account</a>
        </div>
    </div>
</div>
</body>
</html>
