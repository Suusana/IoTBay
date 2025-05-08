<%@ page import="com.bean.Customer" %>
<%@ page import="com.enums.State" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    Customer customer = new Customer();
    if (session.getAttribute("loggedInUser") != null){
        customer = (Customer)session.getAttribute("loggedInUser");
    } else {
        response.sendRedirect("request.getContextPath()/views/login.jsp");
    }
%>
<head>
    <title>Edit Account</title>
</head>
<body>
<h1>Edit Account Details</h1>
<h2>Please don't leave any fields blank</h2>
<form action="<%=request.getContextPath()%>/UpdateUserDetailsServlet" method="post">
    <label for="username">Username</label>
    <input id="username" name="username" type="text" required value="<%=customer.getUsername()%>">
<%--    have masked with ability to show when toggle? --%>
    <label for="password">Password</label>
    <input id="password" name="password" type="text" required minlength="6" value="<%=customer.getPassword()%>">
    <label for="confirmPassword">Password</label>
    <input id="confirmPassword" name="confirmPassword" type="text" required minlength="6" value="<%=customer.getPassword()%>">
    <label for="firstName">First Name</label>
    <input id="firstName" name="firstName" type="text" required value="<%=customer.getFirstName()%>">
    <label for="lastName">Last Name</label>
    <input id="lastName" name="lastName" type="text" required value="<%=customer.getLastName()%>">
    <label for="email">Email Address</label>
    <input id="email" name="email" type="email" required value="<%=customer.getEmail()%>">
    <label for="phone">Phone Number</label>
    <input id="phone" name="phone" type="tel" required value="<%=customer.getPhone()%>">
    <label for="address">Street</label>
    <input id="address" name="address" type="text" required value="<%=customer.getAddress()%>">
    <label for="city">City</label>
    <input id="city" name="city" type="text" required value="<%=customer.getCity()%>">
    <label for="state">State</label>
    <select id="state" name="state">
        <option><%=customer.getState()%></option>
        <%
            for (State state : State.values()) {
        %>
        <option value="<%=state.getName()%>">
            <%=state.getName()%>
        </option>
        <%
            }
        %>
    </select>
    <label for="postcode">Postcode</label>
    <input id="postcode" name="postcode" type="text" required value="<%=customer.getPostcode()%>">
    <label for="country">Country</label>
    <input id="country" name="country" type="text" required value="<%=customer.getCountry()%>">

    <button type="submit">Update</button>

</form>
</body>
</html>
