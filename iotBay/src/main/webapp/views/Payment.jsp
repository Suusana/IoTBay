<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Payment</title>
        <link rel="stylesheet" href="../assets/css/base.css">
        <link rel="stylesheet" href="../assets/css/login.css">
        <link rel="stylesheet" href="../assets/css/HeaderAndFooter.css">
        <link rel="stylesheet" href="../assets/css/AddPayment.css">
</head>
<body>
<div class="payment-card">
    <h2>Add Payment</h2>

    <%-- Display error message if present --%>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
    <p class="errorMsg"><%= errorMessage %></p>
    <% } %>

    <%-- Retrieve previous values to preserve input after validation errors --%>
    <%
        String orderIdVal    = request.getParameter("orderId");
        String methodVal     = request.getParameter("method");
        String cardHolderVal = request.getParameter("cardHolder");
        String cardNumberVal = request.getParameter("cardNumber");
        String expiryVal     = request.getParameter("expiryDate");
    %>

    <form action="<%= request.getContextPath() %>/AddPayment" method="post">
        <label for="orderId">Order ID:</label>
        <input type="text" id="orderId" name="orderId"
               value="<%= orderIdVal != null ? orderIdVal : "" %>" required>

        <label for="method">Payment Method:</label>
        <select name="method" id="method" required>
            <option value="Credit Card" <%= (methodVal == null || "Credit Card".equals(methodVal)) ? "selected" : "" %>>Credit Card</option>
            <option value="Debit Card"  <%= (methodVal != null && "Debit Card".equals(methodVal)) ? "selected" : "" %>>Debit Card</option>
            <option value="PayPal"      <%= (methodVal != null && "PayPal".equals(methodVal)) ? "selected" : "" %>>PayPal</option>
        </select>

        <label for="cardHolder">Card Holder Name:</label>
        <input type="text" id="cardHolder" name="cardHolder"
               value="<%= cardHolderVal != null ? cardHolderVal : "" %>" required>

        <label for="cardNumber">Card Number:</label>
        <input type="text" id="cardNumber" name="cardNumber"
               value="<%= cardNumberVal != null ? cardNumberVal : "" %>" required>

        <label for="expiryDate">Expiry Date:</label>
        <input type="date" id="expiryDate" name="expiryDate"
               value="<%= expiryVal != null ? expiryVal : "" %>" required>

        <button type="submit">Submit Payment</button>
    </form>
</div>
</body>
</html>
