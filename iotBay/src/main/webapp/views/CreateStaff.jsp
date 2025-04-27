<%@ page import="com.enums.State" %>
<%@ page import="com.enums.Position" %><%--
  Created by IntelliJ IDEA.
  User: Susana
  Date: 4/27/2025
  Time: 23:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Staff Management</title>
    <link rel="stylesheet" href="../assets/css/base.css">
    <link rel="stylesheet" href="../assets/css/sideBar.css">
    <link rel="stylesheet" href="../assets/css/createStaff.css">
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
    <h1>Create a New Staff</h1>
    <form action="<%= request.getContextPath() %>/AddStaff" method="post">
        <div class="group">
            <div>
                <label>Staff Name:</label><br>
                <input type="text" name="staff_name" required>
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
                <label>Position</label><br>
                <select name="position" id="position">
                    <option selected disabled>Choose a position</option>
                    <%
                        for (Position p : Position.values()) {
                    %>
                    <option value="<%=p.getName()%>">
                        <%=p.getName()%>
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
