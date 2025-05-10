<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Payment</title>
</head>
<body>
    <h2>Add Payment</h2>
    <%-- Display error message if present --%>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null) { %>
        <p style="color:red;"><%= errorMessage %></p>
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
        <label for="orderId">Order ID:</label><br>
        <input type="text" id="orderId" name="orderId"
               value="<%= orderIdVal != null ? orderIdVal : "" %>" required><br><br>

        <label for="method">Payment Method:</label><br>
        <%-- Preserve selected method on error --%>
        <select name="method" id="method" required>
            <option value="Credit Card" <%= (methodVal == null || "Credit Card".equals(methodVal)) ? "selected" : "" %>>Credit Card</option>
            <option value="Debit Card"  <%= (methodVal != null && "Debit Card".equals(methodVal)) ? "selected" : "" %>>Debit Card</option>
            <option value="PayPal"      <%= (methodVal != null && "PayPal".equals(methodVal)) ? "selected" : "" %>>PayPal</option>
        </select><br><br>

        <label for="cardHolder">Card Holder Name:</label><br>
        <input type="text" id="cardHolder" name="cardHolder"
               value="<%= cardHolderVal != null ? cardHolderVal : "" %>" required><br><br>

        <label for="cardNumber">Card Number:</label><br>
        <input type="text" id="cardNumber" name="cardNumber"
               value="<%= cardNumberVal != null ? cardNumberVal : "" %>" required><br><br>

        <label for="expiryDate">Expiry Date:</label><br>
        <input type="date" id="expiryDate" name="expiryDate"
               value="<%= expiryVal != null ? expiryVal : "" %>" required><br><br>

        <button type="submit">Submit Payment</button>
    </form>
</body>
</html>
