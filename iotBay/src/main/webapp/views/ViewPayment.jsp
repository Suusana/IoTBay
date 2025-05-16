<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bean.Payment" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="com.bean.Customer" %>

<%
    List<Payment> paymentList = (List<Payment>) request.getAttribute("paymentList");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    List<Payment> savedPayments = new java.util.ArrayList<>();
    List<Payment> completedPayments = new java.util.ArrayList<>();

    if (paymentList != null) {
        for (Payment p : paymentList) {
            if ("Paid".equalsIgnoreCase(p.getStatus())) {
                completedPayments.add(p);
            } else {
                savedPayments.add(p);
            }
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Payments</title>
</head>
<body>

<h2>Payments Overview</h2>

<%
    Customer customer = new Customer();
    if (session.getAttribute("loggedInUser") != null){
        customer = (Customer)session.getAttribute("loggedInUser");
    } else {
        customer.setUsername("Guest");
    }
%>

<!-- Search Form -->
<form method="get" action="ViewPayment">
    <label>Payment ID:</label>
    <input type="text" name="searchPaymentId" placeholder="e.g. 101" />
    <label>Payment Date:</label>
    <input type="text" name="searchDate" placeholder="yyyy-mm-dd" />
    <button type="submit">Search</button>
</form>

<% String message = (String) request.getAttribute("message");
    if (message != null) { %>
<p style="color: red;"><%= message %></p>
<% } %>

<!-- Saved Payments Section -->
<h3>Saved Payments</h3>
<table border="1" cellpadding="5" cellspacing="0">
    <thead>
    <tr>
        <th>Payment ID</th>
        <th>Card Holder</th>
        <th>Card Number</th>
        <th>Expiry Date</th>
        <th>Amount</th>
        <th>Payment Date</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <% if (!savedPayments.isEmpty()) {
        for (Payment payment : savedPayments) {
            String maskedCard = (payment.getCardNumber() != null && payment.getCardNumber().length() >= 4)
                    ? "**** **** **** " + payment.getCardNumber().substring(payment.getCardNumber().length() - 4)
                    : payment.getCardNumber();
            String expiryFormatted = (payment.getExpiryDate() != null) ? sdf.format(payment.getExpiryDate()) : "N/A";
            String paymentDateFormatted = (payment.getPaymentDate() != null) ? sdf.format(payment.getPaymentDate()) : "N/A";
            String amountFormatted = (payment.getAmount() != null) ? currencyFormat.format(payment.getAmount()) : "N/A";
    %>
    <tr>
        <td><%= payment.getPaymentId() %></td>
        <td><%= payment.getCardHolder() %></td>
        <td><%= maskedCard %></td>
        <td><%= expiryFormatted %></td>
        <td><%= amountFormatted %></td>
        <td><%= paymentDateFormatted %></td>
        <td>
            <!-- Proceed -->
            <form action="<%= request.getContextPath() %>/ProceedPayment" method="post" style="display:inline;">
                <input type="hidden" name="paymentId" value="<%= payment.getPaymentId() %>"/>
                <button type="submit">Proceed</button>
            </form>

            <!-- Delete -->
            <form action="<%= request.getContextPath() %>/DeletePayment" method="post" style="display:inline;">
                <input type="hidden" name="paymentId" value="<%= payment.getPaymentId() %>"/>
                <button type="submit">Delete</button>
            </form>
        </td>
    </tr>
    <% } } else { %>
    <tr><td colspan="7">No saved payments.</td></tr>
    <% } %>
    </tbody>
</table>

<!-- Completed Payments Section -->
<h3>Completed Payments</h3>
<table border="1" cellpadding="5" cellspacing="0">
    <thead>
    <tr>
        <th>Payment ID</th>
        <th>Card Holder</th>
        <th>Card Number</th>
        <th>Expiry Date</th>
        <th>Amount</th>
        <th>Payment Date</th>
    </tr>
    </thead>
    <tbody>
    <% if (!completedPayments.isEmpty()) {
        for (Payment payment : completedPayments) {
            String maskedCard = (payment.getCardNumber() != null && payment.getCardNumber().length() >= 4)
                    ? "**** **** **** " + payment.getCardNumber().substring(payment.getCardNumber().length() - 4)
                    : payment.getCardNumber();
            String expiryFormatted = (payment.getExpiryDate() != null) ? sdf.format(payment.getExpiryDate()) : "N/A";
            String paymentDateFormatted = (payment.getPaymentDate() != null) ? sdf.format(payment.getPaymentDate()) : "N/A";
            String amountFormatted = (payment.getAmount() != null) ? currencyFormat.format(payment.getAmount()) : "N/A";
    %>
    <tr>
        <td><%= payment.getPaymentId() %></td>
        <td><%= payment.getCardHolder() %></td>
        <td><%= maskedCard %></td>
        <td><%= expiryFormatted %></td>
        <td><%= amountFormatted %></td>
        <td><%= paymentDateFormatted %></td>
    </tr>
    <% } } else { %>
    <tr><td colspan="6">No completed payments.</td></tr>
    <% } %>
    </tbody>
</table>

<!-- Back button -->
<form action="<%= request.getContextPath() %>/home" method="get">
    <button type="submit">Back to Home</button>
</form>

</body>
</html>
