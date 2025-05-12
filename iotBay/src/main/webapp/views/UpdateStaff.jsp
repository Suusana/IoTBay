<%--
  Created by IntelliJ IDEA.
  User: Susana
  Date: 4/28/2025
  Time: 12:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.enums.State" %>
<%@ page import="com.enums.Position" %>
<%@ page import="com.bean.Staff" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Staff Details</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/sideBar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/createStaff.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<%
    Staff _staff = (Staff) session.getAttribute("loggedInUser");
%>
<div class="sideBar">
    <h2>Admin Panel</h2>
    <h4>Current Staff: <%= _staff.getStaffName()%></h4>
    <a href="<%= request.getContextPath() %>/ShowCustomerInfo">
        <i class="fa-solid fa-user fa-lg"></i>
        <span>Customer Management</span>
    </a>
    <a href="<%= request.getContextPath() %>/ShowStaffInfo" class="current">
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
    <h1>Edit Staff Details</h1>
    <%
        Staff staff = (Staff) request.getAttribute("staff");
    %>
    <form action="<%= request.getContextPath() %>/UpdateStaff" method="post">
        <div class="group">
            <div>
                <label>Staff Name:</label><br>
                <input type="text" name="staff_name" value="<%= staff.getStaffName()%>" required>
            </div>
            <div>
                <label>Password:</label><br>
                <input type="password" name="password" value="<%= staff.getPassword()%>" required>
            </div>
        </div>

        <div class="group">
            <div>
                <label>Phone Number:</label><br>
                <input type="text" name="phone_num" value="<%= staff.getPhoneNum()%>" required>
            </div>
            <div>
                <label>Email:</label><br>
                <input type="email" name="email" value="<%= staff.getEmail()%>" required>
            </div>
        </div>

        <div class="group">
            <div>
                <label>Position</label><br>
                <select name="position" id="position">
                    <option disabled>Choose a position</option>
                    <%
                        for (Position p : Position.values()) {
                    %>
                    <option value="<%=p.getName()%>" <%= p.getName().equals(staff.getPosition()) ? "selected" : "" %>>
                        <%=p.getName()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div>
                <label>Address:</label><br>
                <input type="text" name="address" value="<%= staff.getAddress()%>" required>
            </div>
        </div>

        <div class="group">
            <div>
                <label>City:</label><br>
                <input type="text" name="city" value="<%= staff.getCity()%>" required>
            </div>
            <div>
                <label>Postcode:</label><br>
                <input type="text" name="postcode" value="<%= staff.getPostcode()%>" required>
            </div>
        </div>

        <div class="group">
            <div>
                <label>State</label><br>
                <select name="state" id="state">
                    <option disabled>Choose a state</option>
                    <%
                        for (State s : State.values()) {
                    %>
                    <option value="<%=s.getName()%>" <%= s.getName().equals(staff.getState()) ? "selected" : "" %> >
                        <%=s.getName()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div>
                <label>Country:</label><br>
                <input type="text" name="country" value="<%= staff.getCountry()%>" required>
            </div>
        </div>

        <div class="buttonGroup">
            <button type="button" onclick="if(confirm('Are you sure you want to leave without saving?')) history.back();">Cancel</button>
            <input type="hidden" name="staffId" value="<%= staff.getStaffId()%>">
            <button type="submit">Save</button>
        </div>
    </form>
</div>
</body>
</html>

