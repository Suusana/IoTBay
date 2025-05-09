<%@ page import="com.bean.Customer" %>
<%@ page import="com.enums.State" %>
<%@ page import="com.enums.UserType" %><%--
  Created by IntelliJ IDEA.
  User: Susana
  Date: 5/10/2025
  Time: 0:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Details</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/sideBar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/createStaff.css">
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
    <h1>Edit Customer Details</h1>
    <%
        Customer customer = (Customer) request.getAttribute("customer");
    %>
    <form action="<%= request.getContextPath() %>/UpdateCustomer" method="post">
        <div class="group">
            <div>
                <label>First Name:</label><br>
                <input type="text" name="firstName" value="<%= customer.getFirstName()%>" required>
            </div>
            <div>
                <label>Last Name:</label><br>
                <input type="text" name="lastName" value="<%= customer.getLastName()%>" required>
            </div>
        </div>

        <div class="group">
            <div>
                <label>Username:</label><br>
                <input type="text" name="username" value="<%= customer.getUsername()%>" required>
            </div>
            <div>
                <label>Password:</label><br>
                <input type="password" name="password" value="<%= customer.getPassword()%>" required>
            </div>
        </div>

        <div class="group">
            <div>
                <label>Phone Number:</label><br>
                <input type="text" name="phone_num" value="<%= customer.getPhone()%>" required>
            </div>
            <div>
                <label>Email:</label><br>
                <input type="email" name="email" value="<%= customer.getEmail()%>" required>
            </div>
        </div>

        <div class="group">
            <div>
                <label>User Type</label><br>
                <select name="type" id="type">
                    <option disabled>Choose a Type</option>
                    <%
                        for (UserType userType : UserType.values()) {
                    %>
                    <option value="<%=userType.getName()%>" <%= userType.getName().equals(customer.getType()) ? "selected" : "" %> >
                        <%=userType.getName()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div>
                <label>Address:</label><br>
                <input type="text" name="address" value="<%= customer.getAddress()%>" required>
            </div>
        </div>

        <div class="group">
            <div>
                <label>City:</label><br>
                <input type="text" name="city" value="<%= customer.getCity()%>" required>
            </div>
            <div>
                <label>Postcode:</label><br>
                <input type="text" name="postcode" value="<%= customer.getPostcode()%>" required>
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
                    <option value="<%=s.getName()%>" <%= s.getName().equals(customer.getState()) ? "selected" : "" %> >
                        <%=s.getName()%>
                    </option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div>
                <label>Country:</label><br>
                <input type="text" name="country" value="<%= customer.getCountry()%>" required>
            </div>
        </div>

        <div class="buttonGroup">
            <button type="button" onclick="if(confirm('Are you sure you want to leave without saving?')) history.back();">Cancel</button>
            <input type="hidden" name="customerId" value="<%= customer.getUserId()%>">
            <button type="submit">Save</button>
        </div>
    </form>
</div>
</body>
</html>
