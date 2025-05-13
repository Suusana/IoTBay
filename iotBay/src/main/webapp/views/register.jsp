<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 19/03/2025
  Time: 2:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.enums.State" %>
<%@ page import="java.util.Map" %>

<%
    // retrieve any errors from servlet upon submission
    Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");

    // retrieve previously submitted input to repopulate fields - users don't have to fill fields in again
    String firstName = (request.getParameter("firstName") != null) ? request.getParameter("firstName") : "";
    String lastName = (request.getParameter("lastName") != null) ? request.getParameter("lastName") : "";
    String email = (request.getParameter("email") != null) ? request.getParameter("email") : "";
    String phoneNumber = (request.getParameter("phoneNumber") != null) ? request.getParameter("phoneNumber") : "";
    String street = (request.getParameter("street") != null) ? request.getParameter("street") : "";
    String unit = (request.getParameter("unit") != null) ? request.getParameter("unit") : "";
    String city = (request.getParameter("city") != null) ? request.getParameter("city") : "";
    String selectedState = (request.getParameter("state") != null) ? request.getParameter("state") : "";
    String postalCode = (request.getParameter("postalCode") != null) ? request.getParameter("postalCode") : "";
    String country = (request.getParameter("country") != null) ? request.getParameter("country") : "";
%>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="../assets/css/base.css">
    <link rel="stylesheet" href="../assets/css/HeaderAndFooter.css">
    <link rel="stylesheet" href="../assets/css/register.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <script src="../assets/js/UIHandler.js"></script>
</head>
<body>
<header class="header">
    <!-- Logo -->
    <a href="../index.jsp">
        <img src="../assets/img/Logo.png" alt="IotBay Logo">
    </a>
</header>

<main>
    <h1>Register</h1>
    <form id="registerForm" action="<%= request.getContextPath()%>/RegisterServlet" method="post">
        <div class="container" style="align-items: center;">
            <div>
                <label for="userType">Choose your Role:</label>
            </div>
            <div>
                <select id="userType" name="userType" required>
                    <option value="customer" <%="customer".equalsIgnoreCase(request.getParameter("userType")) ? "selected" : ""%>>Customer</option>
                    <option value="staff" <%="staff".equalsIgnoreCase(request.getParameter("userType")) ? "selected" : ""%>>Staff</option>
                </select>
            </div>
        </div>
        <h4>Personal Information</h4>
        <div class="container">
            <div>
                <label for="firstName">First Name</label><br>
                <input id="firstName" name="firstName" type="text" required placeholder="First Name" value="<%=firstName%>"/>
                <% if (errors != null && errors.get("firstName") != null) { %>
                <span class="errorText"><%=errors.get("firstName")%></span>
                <% } %>
            </div>
            <div>
                <label for="lastName">Last Name</label><br>
                <input id="lastName" name="lastName" type="text" required placeholder="Last Name" value="<%=lastName%>"/>
                <% if (errors != null && errors.get("lastName") != null) { %>
                <span class="errorText"><%=errors.get("lastName")%></span>
                <% } %>
            </div>
        </div>
        <div class="container">
            <div>
                <label for="email">Email</label><br>
                <input id="email" name="email" type="email" required placeholder="Email@site.com" value="<%=email%>"/>
                <% if (errors != null && errors.get("email") != null) { %>
                <span class="errorText"><%=errors.get("email")%></span>
                <% } %>
            </div>
            <div>
                <label for="phoneNumber">Phone Number</label><br>
                <input id="phoneNumber" name="phoneNumber" type="tel" required placeholder="e.g 0412 345 678" value="<%=phoneNumber%>"/>
                <% if (errors != null && errors.get("phoneNumber") != null) { %>
                <span class="errorText"><%=errors.get("phoneNumber")%></span>
                <% } %>
            </div>
        </div>
        <div class="container">
            <div>
                <label for="password">Password</label><br>
                <input id="password" name="password" type="password" required minlength="6"
                       placeholder="Enter your password"/>
                <% if (errors != null && errors.get("password") != null) { %>
                <span class="errorText"><%=errors.get("password")%></span>
                <% } %>
            </div>
            <div>
                <label for="ConfirmPassword">Confirm Password</label><br>
                <input id="ConfirmPassword" name="ConfirmPassword" type="password" required minlength="6"
                       placeholder="Confirm your password"/>
            </div>
        </div>

        <h4>Address</h4>
        <div class="address">
            <div class="container">
                <div>
                    <label for="Street">Street</label><br>
                    <input id="Street" name="street" type="text" required placeholder="Street address" value="<%=street%>">
                    <% if (errors != null && errors.get("address") != null) { %>
                    <span class="errorText"><%=errors.get("address")%></span>
                    <% } %>
                </div>
                <div>
                    <label for="Unit">Unit</label><br>
                    <input id="Unit" name="unit" type="text" placeholder="Unit, building, floor etc" value="<%=unit%>">
                </div>
                <div>
                    <label for="city">City</label><br>
                    <input id="city" name="city" type="text" required placeholder="City" value="<%=city%>">
                    <% if (errors != null && errors.get("city") != null) { %>
                    <span class="errorText"><%=errors.get("city")%></span>
                    <% } %>
                </div>

            </div>
            <div class="container">
                <div>
                    <label for="State">State</label><br>
                    <select name="state" id="State">
                        <option selected disabled>Choose a state</option>
                        <%
                            for (State s : State.values()) {
                                boolean isSelected = s.getName().equalsIgnoreCase(selectedState);
                        %>
                        <option value="<%=s.getName()%>" <%=isSelected ? "selected" : ""%>>
                            <%=s.getName()%>
                        </option>
                        <%
                            }
                        %>
                    </select>
                    <% if (errors != null && errors.get("state") != null) { %>
                    <span class="errorText"><%=errors.get("state")%></span>
                    <% } %>
                </div>
                <div>
                    <label for="postalCode">Postal Code</label><br>
                    <input id="postalCode" name="postalCode" type="text" required placeholder="Postal Code" value="<%=postalCode%>">
                    <% if (errors != null && errors.get("postcode") != null) { %>
                    <span class="errorText"><%=errors.get("postcode")%></span>
                    <% } %>
                </div>
                <div>
                    <label for="country">Country</label><br>
                    <input id="country" name="country" type="text" required placeholder="Country" value="<%=country%>">
                    <% if (errors != null && errors.get("country") != null) { %>
                    <span class="errorText"><%=errors.get("country")%></span>
                    <% } %>
                </div>
            </div>
        </div>
        <div class="container">
        <%
            // check for final general input errors
            String errorMessage = (String) request.getAttribute("errorMessage");

            if (errorMessage != null) {
        %>
        <span class="errorText"><%=errorMessage%></span>
        <% } %>
        </div>
        <div class="buttons">
            <button type="submit">Register</button><br>
            <a href="/views/login.jsp" class="visibleLink">Already have an account?</a>
        </div>
    </form>
</main>

<footer class="footer">
    <hr>
    <div>
        <div class="section">
            <h6 id="dif">IoTBay</h6><br>
            <span>The most complete range of IoT devices to upgrade your life at the touch of a button.</span>
        </div>
        <div class="section">
            <h6>Links</h6>
            <a href="<%=request.getContextPath()%>/home"><span>Home</span></a>
            <a href="<%=request.getContextPath()%>/productServlet"><span>Shop</span></a>
            <a href="<%=request.getContextPath()%>/viewOrder"><span>Order</span></a>
            <a href=""><span>Category</span></a>
        </div>
        <div class="section">
            <h6>Contact Us</h6>
            <span>Address: 123 IotBay, Sydney</span>
            <span>Phone Number: +61 0499999999</span>
            <span>Email Address: IotBay@example.com</span>
        </div>
        <div class="section">
            <h6>Follow Us</h6>
            <a href="https://www.instagram.com/">
                <i class="fa-brands fa-instagram fa-lg"></i>
                <span>Instagram</span>
            </a>
            <a href="https://www.facebook.com/">
                <i class="fa-brands fa-facebook fa-lg"></i>
                <span>Facebook</span>
            </a>
            <a href="https://discord.com/">
                <i class="fa-brands fa-discord fa-lg"></i>
                <span>Discord</span>
            </a>
            <a href="https://x.com/?lang=en">
                <i class="fa-brands fa-x-twitter fa-lg"></i>
                <span>Twitter</span>
            </a>
        </div>
    </div>
    <hr>
    <p>©2025. IoTBay Group 4 All Right Reserved</p>
</footer>
</body>
</html>
