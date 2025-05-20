<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="com.bean.Payment" %>
<%@ page import="com.bean.Customer" %>
<%@ page import="com.enums.Status" %>

<%
  List<Payment> guestPayments = (List<Payment>) request.getAttribute("guestPayments");
  Integer orderId = (Integer) request.getAttribute("orderId");
  String message = (String) request.getAttribute("message");
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

  // Display name for header
  String displayName = "Guest";
  Object loggedInUser = session.getAttribute("loggedInUser");
  if (loggedInUser instanceof Customer) {
    Customer c = (Customer) loggedInUser;
    if (c.getUsername() != null && c.getUsername().toLowerCase().startsWith("guest")) {
      displayName = c.getUsername().replaceFirst("(?i)guest", ""); // remove 'guest'
    } else if (c.getFirstName() != null) {
      displayName = c.getFirstName();
    }
  }
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Find Your Payment</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/base.css?<%= System.currentTimeMillis() %>">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/HeaderAndFooter.css?<%= System.currentTimeMillis() %>">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/PaymentStyle.css?<%= System.currentTimeMillis() %>">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
  <style>
    .flex-form {
      display: flex;
      flex-direction: column;
      gap: 20px;
      align-items: center;
      margin-top: 30px;
    }
    .flex-form-row {
      display: flex;
      align-items: center;
      gap: 12px;
      width: 100%;
      max-width: 500px;
      justify-content: space-between;
    }
    .flex-form-row label {
      flex: 1;
      text-align: right;
      font-weight: bold;
    }
    .flex-form-row input {
      flex: 2;
      height: 36px;
      padding: 0 10px;
      font-size: 15px;
      border: 1px solid #ccc;
      border-radius: 6px;
    }
    .flex-form button {
      height: 40px;
      padding: 0 25px;
      font-size: 15px;
      font-weight: bold;
      background-color: #DAA520;
      color: white;
      border: none;
      border-radius: 8px;
      cursor: pointer;
      transition: 0.3s;
    }
    .flex-form button:hover {
      background-color: #c6921a;
    }
    .infoCard {
      max-width: 800px;
      margin: 0 auto;
      background-color: #fff;
      padding: 35px 40px;
      border-radius: 12px;
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.08);
      text-align: center;
    }
    .infoCard h2 {
      margin-bottom: 20px;
    }
    .formError {
      color: red;
      font-weight: bold;
      margin-top: 10px;
    }
  </style>
</head>
<body>

<!-- Header -->
<div class="header">
  <a href="<%=request.getContextPath()%>/home">
    <img src="<%=request.getContextPath()%>/assets/img/Logo.png" alt="IotBay Logo">
  </a>
  <menu>
    <a href="<%= request.getContextPath()%>/home"><span>Home</span></a>
    <a href="<%= request.getContextPath()%>/productServlet"><span>Shop</span></a>
    <a href="<%= request.getContextPath()%>/viewOrder"><span>Order</span></a>
    <a href="<%= request.getContextPath()%>/ViewPayment"><span class="selected">Payment</span></a>
  </menu>
  <menu class="icon">
    <a href="#">
      <i class="fa-solid fa-circle-user fa-2x"></i>
      <span><%= displayName %></span>
    </a>
    <a href="<%=request.getContextPath()%>/GetByProductNameToCustomer">
      <i class="fa-solid fa-magnifying-glass fa-2x"></i><span>Search</span>
    </a>
    <a href="#"><i class="fa-solid fa-cart-shopping fa-2x"></i><span>Cart</span></a>
  </menu>
</div>

<!-- Main Content -->
<div class="mainContainer">
  <div class="infoCard">
    <h2>Find Your Payment</h2>

    <% if (message != null) { %>
    <p class="formError"><%= message %></p>
    <% } %>

    <form method="get" action="GuestViewPayment" class="flex-form">
      <div class="flex-form-row">
        <label for="orderId">Order ID:</label>
        <input type="text" name="orderId" id="orderId" required />
      </div>
      <div class="flex-form-row">
        <label for="guestEmail">Email:</label>
        <input type="email" name="guestEmail" id="guestEmail" required />
      </div>
      <button type="submit">View Payment</button>
    </form>

    <% if (guestPayments != null && !guestPayments.isEmpty()) { %>
    <h3 style="margin-top: 30px;">Payments for Order ID: <%= orderId %></h3>
    <table class="infoTable">
      <thead>
      <tr>
        <th>Payment Method</th>
        <th>Amount</th>
        <th>Status</th>
        <th>Payment Date</th>
      </tr>
      </thead>
      <tbody>
      <% for (Payment payment : guestPayments) { %>
      <tr>
        <td><%= payment.getMethod() %></td>
        <td><%= currencyFormat.format(payment.getAmount()) %></td>
        <td><%= payment.getStatus() %></td>
        <td><%= payment.getPaymentDate() != null ? sdf.format(payment.getPaymentDate()) : "N/A" %></td>
      </tr>
      <% } %>
      </tbody>
    </table>
    <% } else if (orderId != null) { %>
    <p style="margin-top: 20px;">No payment records found for Order ID: <%= orderId %></p>
    <% } %>
  </div>
</div>

<!-- Footer -->
<div class="footer">
  <hr>
  <div>
    <div class="section">
      <h6 id="dif">IoTBay</h6><br>
      <span>The most complete range of IoT devices to upgrade your life at the touch of a button.</span>
    </div>
    <div class="section">
      <h6>Links</h6>
      <a href="<%=request.getContextPath()%>/home">Home</a>
      <a href="<%=request.getContextPath()%>/productServlet">Shop</a>
      <a href="<%=request.getContextPath()%>/viewOrder">Order</a>
      <a href="<%=request.getContextPath()%>/ViewPayment">Payment</a>
    </div>
    <div class="section">
      <h6>Contact Us</h6>
      <span>Address: 123 IotBay, Sydney</span>
      <span>Phone Number: +61 0499999999</span>
      <span>Email: IotBay@example.com</span>
    </div>
    <div class="section">
      <h6>Follow Us</h6>
      <a href="https://www.instagram.com/"><i class="fa-brands fa-instagram fa-lg"></i> Instagram</a>
      <a href="https://www.facebook.com/"><i class="fa-brands fa-facebook fa-lg"></i> Facebook</a>
      <a href="https://discord.com/"><i class="fa-brands fa-discord fa-lg"></i> Discord</a>
      <a href="https://x.com/?lang=en"><i class="fa-brands fa-x-twitter fa-lg"></i> Twitter</a>
    </div>
  </div>
  <hr>
  <p>©2025 IoTBay Group 4. All Rights Reserved.</p>
</div>

</body>
</html>
