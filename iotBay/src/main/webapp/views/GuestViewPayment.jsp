<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bean.Payment" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.NumberFormat" %>

<%
  List<Payment> guestPayments = (List<Payment>) request.getAttribute("guestPayments");
  Integer orderId = (Integer) request.getAttribute("orderId");
  String message = (String) request.getAttribute("message");
  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Guest Payment View</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/base.css">
</head>
<body style="font-family: Arial, sans-serif; background-color: #FFF3E3; padding: 20px;">

<h2>View Your Payment</h2>

<% if (message != null) { %>
<p style="color: red;"><%= message %></p>
<% } %>

<form method="get" action="GuestViewPayment" style="margin-bottom: 20px;">
  <div style="margin-bottom: 10px;">
    <label for="orderId">Order ID:</label><br>
    <input type="text" name="orderId" id="orderId" required />
  </div>
  <div style="margin-bottom: 10px;">
    <label for="guestEmail">Email:</label><br>
    <input type="email" name="guestEmail" id="guestEmail" required />
  </div>
  <button type="submit">View Payment</button>
</form>

<% if (guestPayments != null && !guestPayments.isEmpty()) { %>
<h3>Payments for Order ID: <%= orderId %></h3>
<table border="1" cellpadding="8" cellspacing="0" style="border-collapse: collapse; background-color: #fff;">
  <thead style="background-color: #f0f0f0;">
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
<p>No payment records found for Order ID: <%= orderId %></p>
<% } %>

</body>
</html>
