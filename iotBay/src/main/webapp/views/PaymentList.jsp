<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bean.Payment" %>
<html>
<head>
    <title>Payment List</title>
</head>
<body>
    <h2>Payment List</h2>

    <%
        List<Payment> payments = (List<Payment>) request.getAttribute("payments");
        if (payments != null && !payments.isEmpty()) {
    %>
    <table border="1">
        <tr>
            <th>Payment ID</th>
            <th>Order ID</th>
            <th>Method</th>
            <th>Card Holder</th>
            <th>Card Number</th>
            <th>Amount</th>
            <th>Payment Date</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <%
            for (Payment p : payments) {
        %>
        <tr>
            <td><%= p.getPaymentId() %></td>
            <td><%= p.getOrderId() %></td>
            <td><%= p.getMethod() %></td>
            <td><%= p.getCardHolder() %></td>
            <td><%= p.getCardNumber() %></td>  <!-- Masked card number -->
            <td><%= p.getAmount() %></td>
            <td><%= p.getPaymentDate() %></td>
            <td><%= p.getStatus() %></td>
            <td>
                <a href="<%= request.getContextPath() %>/EditPayment?paymentId=<%= p.getPaymentId() %>">Edit</a> |
                <form action="<%= request.getContextPath() %>/DeletePayment" method="post" style="display:inline;">
                    <input type="hidden" name="paymentId" value="<%= p.getPaymentId() %>">
                    <input type="hidden" name="orderId" value="<%= p.getOrderId() %>">
                    <button type="submit" onclick="return confirm('Are you sure you want to delete this payment?');">
                        Delete
                    </button>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
        <p>No payments found for this order.</p>
    <% } %>

    <br>
    <a href="<%= request.getContextPath() %>/views/Payment.jsp">Back to Payment Form</a>
</body>
</html>
