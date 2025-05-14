<%@ page import="com.enums.UserType" %>
<%@ page import="com.enums.State" %>
<%@ page import="com.bean.Staff" %><%--
  Created by IntelliJ IDEA.
  User: Susana
  Date: 5/10/2025
  Time: 1:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Details</title>
    <link rel="stylesheet" href="../assets/css/base.css">
    <link rel="stylesheet" href="../assets/css/sideBar.css">
    <link rel="stylesheet" href="../assets/css/createStaff.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<%
    Staff staff = (Staff) session.getAttribute("loggedInUser");
%>
<div class="sideBar">
    <h2>Admin Panel</h2>
    <h4>Current Staff: <%= staff.getStaffName()%></h4>
    <a href="<%= request.getContextPath() %>/ShowCustomerInfo" class="current">
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
    <h1>Create a New Customer</h1>
    <form action="<%= request.getContextPath() %>/AddCustomer" method="post">
        <div class="group">
            <div>
                <label>First Name:</label><br>
                <input type="text" name="firstName" required>
            </div>
            <div>
                <label>Last Name:</label><br>
                <input type="text" name="lastName" required>
            </div>
        </div>

        <div class="group">
            <div>
                <label>Username:</label><br>
                <input type="text" name="username" required>
            </div>
            <div>
                <label>Password:</label><br>
                <input type="password" name="password" required>
            </div>
        </div>

        <div class="group">
            <div>
                <label>Phone Number:</label><br>
                <input type="text" name="phone_num" required>
            </div>
            <div>
                <label>Email:</label><br>
                <input type="email" name="email" required>
            </div>
        </div>

        <div class="group">
            <div>
                <label>Type</label><br>
                <select name="type" id="type">
                    <option selected disabled>Choose a Type</option>
                    <%
                        for (UserType u : UserType.values()) {
                    %>
                    <option value="<%=u.getName()%>">
                        <%=u.getName()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div>
                <label>Address:</label><br>
                <input type="text" name="address" required>
            </div>
        </div>

        <div class="group">
            <div>
                <label>City:</label><br>
                <input type="text" name="city" required>
            </div>
            <div>
                <label>Postcode:</label><br>
                <input type="text" name="postcode" required>
            </div>
        </div>

        <div class="group">
            <div>
                <label>State</label><br>
                <select name="state" id="state">
                    <option selected disabled>Choose a state</option>
                    <%
                        for (State s : State.values()) {
                    %>
                    <option value="<%=s.getName()%>">
                        <%=s.getName()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div>
                <label>Country:</label><br>
                <input type="text" name="country" required>
            </div>
        </div>

        <div class="buttonGroup">
            <button type="button" onclick="if(confirm('Are you sure you want to leave without saving?')) history.back();">Cancel</button>
            <button type="submit">Save</button>
        </div>
    </form>
</div>

</body>
</html>
