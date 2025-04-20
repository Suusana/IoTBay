<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 19/03/2025
  Time: 2:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Customer" %>
<%@ page import="com.enums.State" %>
<%@ page import="com.enums.Status" %>
<%
    // generate a unique id
    long currentTime = System.currentTimeMillis();
    int randomPart = (int) (Math.random() * 1000);
    long id = currentTime * 1000 + randomPart;

    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String phoneNumber = request.getParameter("phoneNumber");
    String state = request.getParameter("state");
    String city = request.getParameter("city");
    String postalCode = request.getParameter("postalCode");
    String country = request.getParameter("country");
    String username = "";
    String address = request.getParameter("unit") + request.getParameter("street");
    String status = Status.REGISTERED.getStatus();

    if (email != null) {
        username = email.split("@")[0]; //Can be used as a unique username
    }

    //    create a user
    Customer customer = new Customer();

    if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
        Long phone = Long.parseLong(phoneNumber);
        customer.setPhone(phone);
    }
    if (postalCode != null && !postalCode.trim().isEmpty()) {
        Integer postal = Integer.parseInt(postalCode);
        customer.setPostcode(postal);
    }

    customer.setUserId(id);
    customer.setUsername(username);
    customer.setPassword(password);
    customer.setFirstName(firstName);
    customer.setLastName(lastName);
    customer.setEmail(email);
    customer.setStatus(status);
    customer.setAddress(address);
    customer.setCity(city);
    customer.setState(state);
    customer.setCountry(country);

    session.setAttribute("registeredUser", customer);
    session.setAttribute("loggedIn", Boolean.FALSE);
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        response.sendRedirect("./welcome.jsp");
        return;
    }
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
    <a href="./main.jsp">
        <img src="../assets/img/Logo.png" alt="IotBay Logo">
    </a>
</header>

<main
    <h1>Register</h1>
    <form action="register.jsp" method="post">
        <h4>Personal Information</h4>
        <div class="container">
            <div>
                <label for="firstName">First Name</label><br>
                <input id="firstName" name="firstName" type="text" required placeholder="First Name"/>
            </div>
            <div>
                <label for="lastName">Last Name</label><br>
                <input id="lastName" name="lastName" type="text" required placeholder="Last Name"/>
            </div>
        </div>
        <div class="container">
            <div>
                <label for="email">Email</label><br>
                <input id="email" name="email" type="email" required placeholder="Email@site.com"/>
            </div>
            <div>
                <label for="phoneNumber">Phone Number</label><br>
                <input id="phoneNumber" name="phoneNumber" type="tel" required placeholder="e.g 0412 345 678"/>
            </div>
        </div>
        <div class="container">
            <div>
                <label for="password">Password</label><br>
                <input id="password" name="password" type="password" required minlength="6"
                       placeholder="Enter your password"/>
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
                    <input id="Street" name="street" type="text" required placeholder="Street address">
                </div>
                <div>
                    <label for="Unit">Unit</label><br>
                    <input id="Unit" name="unit" type="text" required placeholder="Unit, building, floor etc">
                </div>
                <div>
                    <label for="city">City</label><br>
                    <input id="city" name="city" type="text" required placeholder="City">
                </div>

            </div>
            <div class="container">
                <div>
                    <label for="State">State</label><br>
                    <select name="State" id="State">
                        <option selected disabled>Choose a state</option>
                        <%
                            for (State s : State.values()) {
                        %>
                        <option value= <%=s.getState()%>>
                            <%=s.getState()%>
                        </option>
                        <%
                            }
                        %>
                    </select>
                </div>
                <div>
                    <label for="postalCode">Postal Code</label><br>
                    <input id="postalCode" name="postalCode" type="text" required placeholder="Postal Code">
                </div>
                <div>
                    <label for="country">Country</label><br>
                    <input id="country" name="country" type="text" required placeholder="Country">
                </div>
            </div>
        </div>
        <div class="buttons">
            <button type="submit">Register</button><br>
            <a href="./login.jsp" class="visibleLink">Already have an account?</a>
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
            <a href=""><span>Home</span></a>
            <a href=""><span>Shop</span></a>
            <a href=""><span>Order</span></a>
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
