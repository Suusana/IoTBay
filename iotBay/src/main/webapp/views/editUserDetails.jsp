<%@ page import="com.bean.Customer" %>
<%@ page import="com.enums.State" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    Customer customer = new Customer();
    if (session.getAttribute("loggedInUser") != null){
        customer = (Customer)session.getAttribute("loggedInUser");
    } else {
        response.sendRedirect(request.getContextPath()+"/views/login.jsp");
    }

    boolean isGuest = "guest".equalsIgnoreCase((String) session.getAttribute("userType"));
%>
<head>
    <title>Edit Account</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/base.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/editDetails.css">
</head>
<body>
<main>
    <div class="details-display">
        <h1>Edit Account Details</h1>
        <h2>Any changed fields will be updated!</h2>
        <form action="<%=request.getContextPath()%>/UpdateUserDetailsServlet" method="post">
            <div class="form-row">
                <div class="field">
                    <label for="username">Username</label>
                    <input id="username" name="username" type="text" required value="<%=customer.getUsername() != null ? customer.getUsername() : "" %>" <%= isGuest ? "readonly" : "" %>>
                </div>
            </div>
            <div class="form-row">
                <%--password fields only filled out if user wants to change password - not required--%>
                <div class="field">
                    <label for="password">Password</label>
                    <input id="password" name="password" type="password" minlength="6" <%= isGuest ? "readonly" : "" %>>
                </div>
                <div class="field">
                    <label for="confirmPassword">Confirm Password</label>
                    <input id="confirmPassword" name="confirmPassword" type="password" minlength="6" <%= isGuest ? "readonly" : "" %>>
                </div>
            </div>
            <div class="form-row">
                <p style="font-size: 14px;">To update your password fill in both password fields. Otherwise, leave blank.</p>
            </div>
            <div class="form-row">
                <div class="field">
                    <label for="firstName">First Name</label>
                    <input id="firstName" name="firstName" type="text" required value="<%=customer.getFirstName() != null ? customer.getFirstName() : "" %>" <%= isGuest ? "readonly" : "" %>>
                </div>
                <div class="field">
                    <label for="lastName">Last Name</label>
                    <input id="lastName" name="lastName" type="text" required value="<%=customer.getLastName() != null ? customer.getLastName() : "" %>" <%= isGuest ? "readonly" : "" %>>
                </div>
            </div>
            <div class="form-row">
                <div class="field">
                    <label for="email">Email Address</label>
                    <input id="email" name="email" type="email" required value="<%=customer.getEmail() != null ? customer.getEmail() : "" %>" <%= isGuest ? "readonly" : "" %>>
                </div>
                <div class="field">
                    <label for="phone">Phone Number</label>
                    <input id="phone" name="phone" type="tel" required value="<%=customer.getPhone() != null ? customer.getPhone() : "" %>" <%= isGuest ? "readonly" : "" %>>
                </div>
            </div>
            <div class="form-row">
                <div class="field">
                    <label for="address">Street</label>
                    <input id="address" name="address" type="text" required value="<%=customer.getAddress() != null ? customer.getAddress() : "" %>" <%= isGuest ? "readonly" : "" %>>
                </div>
                <div class="field">
                    <label for="city">City</label>
                    <input id="city" name="city" type="text" required value="<%=customer.getCity() != null ? customer.getCity() : "" %>" <%= isGuest ? "readonly" : "" %>>
                </div>
            </div>
            <div class="form-row">
                <div class="field">
                    <label for="postcode">Postcode</label>
                    <input id="postcode" name="postcode" type="number" required value="<%=customer.getPostcode() != null ? customer.getPostcode() : "" %>" <%= isGuest ? "readonly" : "" %>>
                </div>
                <div class="field">
                    <label for="country">Country</label>
                    <input id="country" name="country" type="text" required value="<%=customer.getCountry() != null ? customer.getCountry() : "" %>" <%= isGuest ? "readonly" : "" %>>
                </div>
            </div>
            <div class="form-row">
                <div class="field state-label">
                    <label for="state">State</label>
                    <select id="state" name="state" <%= isGuest ? "disabled" : "" %>>
                        <option><%=customer.getState() != null ? customer.getState() : "Choose a state" %></option>
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
                </div>
            </div>
            <%
                // display list of all errors after update details attempt
                Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");

                if (errors != null) {
            %>
            <span class="errors">Could not update details:</span><br>
            <%
                for (Map.Entry<String, String> entry : errors.entrySet()) {
            %>
            <span class="errors"><%=entry.getValue()%>!</span><br>
            <%
                    }
                }
            %>
            <% if (!isGuest) { %>
            <div class="update-options">
                <button class="button" type="submit">Update</button>
                <a href="<%=request.getContextPath()%>/ViewUserDetailsServlet" class="visibleLink">Return without editing?</a>
            </div>
            <% } else { %>
            <p style="color: gray; font-style: italic;">Guest users cannot update their account details.</p>
            <a href="<%=request.getContextPath()%>/ViewUserDetailsServlet" class="visibleLink">Back</a>
            <% } %>
        </form>
    </div>
</main>
</body>
</html>
