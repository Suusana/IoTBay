<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Payment" %>
<%
    Payment payment = (Payment) request.getAttribute("payment");

    Integer orderId = null;
    Object orderIdObj = request.getAttribute("orderId");
    if (orderIdObj != null) {
        orderId = (Integer) orderIdObj;
    }

    String guestEmail = (String) session.getAttribute("guestEmail");

    if (payment == null || orderId == null || guestEmail == null) {
%>
<p style="color: red; font-family: Arial, sans-serif; padding: 40px;">
    Missing guest payment information. Please go back and try again.
</p>
<a href="<%= request.getContextPath() %>/home"
   style="font-family: Arial; text-decoration: none; color: #B88E2F;">Back to Home</a>
<%
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Guest Payment Confirmation</title>
    <style>
        body {
            background-color: #FFF3E3;
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 50px;
        }
        .card {
            background-color: #fff;
            padding: 30px 40px;
            border-radius: 20px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            display: inline-block;
        }
        .info {
            font-size: 18px;
            margin: 15px 0;
        }
        .highlight {
            font-weight: bold;
            color: #B88E2F;
        }
        .btn {
            padding: 10px 25px;
            font-size: 16px;
            font-weight: bold;
            margin-top: 30px;
            border-radius: 8px;
            background-color: #B88E2F;
            color: white;
            border: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="card">
    <h2>Thank You for Your Purchase!</h2>

    <p style="margin-top: 10px;">
        Please keep your <strong>Order ID</strong> and <strong>Guest Email</strong> safe to view your payment later.
    </p>

    <div class="info">Order ID: <span class="highlight"><%= orderId %></span></div>
    <div class="info">Guest Email: <span class="highlight"><%= guestEmail %></span></div>

    <div class="info">Payment Method: <span class="highlight"><%= payment.getMethod() %></span></div>
    <div class="info">Status: <span class="highlight"><%= payment.getStatus() %></span></div>
    <div class="info">Amount: <span class="highlight"><%= payment.getAmount() %></span></div>

    <% if ("Credit Card".equalsIgnoreCase(payment.getMethod())) {
        String maskedCard = "Invalid";
        if (payment.getCardNumber() != null && payment.getCardNumber().length() >= 4) {
            maskedCard = "**** **** **** " + payment.getCardNumber().substring(payment.getCardNumber().length() - 4);
        }
    %>
    <div class="info">Card Holder: <span class="highlight"><%= payment.getCardHolder() %></span></div>
    <div class="info">Card Number: <span class="highlight"><%= maskedCard %></span></div>
    <div class="info">Expiry Date: <span class="highlight"><%= payment.getExpiryDate() %></span></div>
    <% } else if ("Bank Transfer".equalsIgnoreCase(payment.getMethod())) { %>
    <div class="info">BSB: <span class="highlight"><%= payment.getBsb() %></span></div>
    <div class="info">Account Name: <span class="highlight"><%= payment.getAccountName() %></span></div>
    <div class="info">Account Number: <span class="highlight"><%= payment.getAccountNumber() %></span></div>
    <% } %>

    <form method="get" action="<%= request.getContextPath() %>/GuestViewPayment">
        <input type="hidden" name="orderId" value="<%= orderId %>">
        <input type="hidden" name="guestEmail" value="<%= guestEmail %>">
        <button class="btn">View Guest Payments</button>
    </form>
</div>
</body>
</html>
