<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bean.Payment" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.NumberFormat" %>

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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/base.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/HeaderAndFooter.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <style>
        .main-content {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 30px;
        }
        .card {
            background-color: #fff;
            padding: 30px 40px;
            border-radius: 20px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            width: 1000px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
            font-size: 14px;
        }
        th {
            background-color: #f9f1e3;
            color: #B88E2F;
        }
        tr:hover {
            background-color: #f9f9f9;
        }
        .style1 {
            border: 1px solid #B88E2F;
            padding: 10px 25px;
            font-size: 16px;
            font-weight: 700;
            color: #B88E2F;
            background-color: transparent;
            border-radius: 8px;
            cursor: pointer;
        }
        .style1:hover {
            background-color: #B88E2F;
            color: #fff;
        }
        .action-button {
            color: #B88E2F;
            text-decoration: none;
            font-weight: bold;
            margin: 0 6px;
        }
        .section-title {
            font-size: 20px;
            font-weight: bold;
            margin-top: 30px;
            color: #B88E2F;
        }
    </style>
</head>
<body>

<div class="main-content">
    <div class="card">
        <h2>Payments Overview</h2>

        <%-- Search function --%>
        <form method="get" action="ViewPayment" style="margin-bottom: 20px;">
            <label><strong>Payment ID:</strong></label>
            <input type="text" name="searchPaymentId" placeholder="e.g. 101"
                   style="padding: 6px; border: 1px solid #ccc; border-radius: 5px;"/>
            <label style="margin-left: 10px;"><strong>Payment Date:</strong></label>
            <input type="text" name="searchDate" placeholder="yyyy-mm-dd"
                   style="padding: 6px; border: 1px solid #ccc; border-radius: 5px;"/>
            <button type="submit" class="style1" style="margin-left: 10px;">Search</button>
        </form>

        <% String message = (String) request.getAttribute("message");
            if (message != null) { %>
        <p style="color: red;"><%= message %></p>
        <% } %>

        <%-- Saved Payments Section --%>
        <div class="section-title"> Saved Payments</div>
        <table>
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
                    <a href="<%= request.getContextPath() %>/CheckPendingPayment?orderId=<%= payment.getOrderId() %>" class="action-button">Proceed</a>
                </td>
            </tr>
            <% } } else { %>
            <tr><td colspan="7">No saved payments.</td></tr>
            <% } %>
            </tbody>
        </table>

        <%-- Completed Payments Section --%>
        <div class="section-title"> Completed Payments</div>
        <table>
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

        <div class="center-button" style="margin-top: 40px;">
            <form action="<%= request.getContextPath() %>/index.jsp" method="get">
                <button type="submit" class="style1">Back to Home</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
