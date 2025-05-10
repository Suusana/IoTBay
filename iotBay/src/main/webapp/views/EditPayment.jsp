<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Payment" %>
<html>
<head>
    <title>Edit Payment</title>
</head>
<body>
    <h2>Edit Payment</h2>
    <%-- Prepare data for form fields and error message --%>
    <%
        String errorMessage  = (String) request.getAttribute("errorMessage");
        Payment payment      = (Payment) request.getAttribute("payment");
        String paymentIdVal  = request.getParameter("paymentId");
        String orderIdVal    = request.getParameter("orderId");
        String methodVal     = request.getParameter("method");
        String cardHolderVal = request.getParameter("cardHolder");
        String cardNumberVal = request.getParameter("cardNumber");
        String expiryVal     = request.getParameter("expiryDate");
        String paymentDateVal= request.getParameter("paymentDate");
        String amountVal     = request.getParameter("amount");
        String statusVal     = request.getParameter("status");
    %>
    <%-- Display error message if present --%>
    <% if (errorMessage != null) { %>
        <p style="color:red;"><%= errorMessage %></p>
    <% } %>

    <form action="<%= request.getContextPath() %>/UpdatePayment" method="post">
        <%-- Hidden fields for IDs --%>
        <input type="hidden" name="paymentId"
               value="<%= paymentIdVal != null ? paymentIdVal : (payment != null ? payment.getPaymentId() : "") %>" />
        <input type="hidden" name="orderId"
               value="<%= orderIdVal != null ? orderIdVal : (payment != null ? payment.getOrderId() : "") %>" />

        <label>Method:</label>
        <input type="text" name="method"
               value="<%= methodVal != null ? methodVal : (payment != null ? payment.getMethod() : "") %>" required><br><br>

        <label>Card Holder:</label>
        <input type="text" name="cardHolder"
               value="<%= cardHolderVal != null ? cardHolderVal : (payment != null ? payment.getCardHolder() : "") %>" required><br><br>

        <label>Card Number:</label>
        <input type="text" name="cardNumber"
               value="<%= cardNumberVal != null ? cardNumberVal : (payment != null ? payment.getCardNumber() : "") %>" required><br><br>

        <label>Expiry Date:</label>
        <input type="date" name="expiryDate"
               value="<%= expiryVal != null ? expiryVal : (payment != null && payment.getExpiryDate() != null ? payment.getExpiryDate() : "") %>" required><br><br>

        <label>Payment Date:</label>
        <input type="date" name="paymentDate"
               value="<%= paymentDateVal != null ? paymentDateVal : (payment != null && payment.getPaymentDate() != null ? payment.getPaymentDate() : "") %>" required><br><br>

        <label>Amount:</label>
        <input type="text" name="amount"
               value="<%= amountVal != null ? amountVal : (payment != null && payment.getAmount() != null ? payment.getAmount() : "") %>" required><br><br>

        <label>Status:</label>
        <input type="text" name="status"
               value="<%= statusVal != null ? statusVal : (payment != null ? payment.getStatus() : "") %>" required><br><br>

        <button type="submit">Update Payment</button>
    </form>
</body>
</html>
