<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Payment</title>
</head>
<body>
    <h2>Add Payment</h2>

    <form action="<%= request.getContextPath() %>/AddPayment" method="post">
        <label for="orderId">Order ID:</label><br>
        <input type="text" id="orderId" name="orderId" required><br><br>

        <label for="method">Payment Method:</label><br>
        <select name="method" id="method" required>
            <option value="Credit Card">Credit Card</option>
            <option value="Debit Card">Debit Card</option>
            <option value="PayPal">PayPal</option>
        </select><br><br>

        <label for="cardHolder">Card Holder Name:</label><br>
        <input type="text" id="cardHolder" name="cardHolder" required><br><br>

        <label for="cardNumber">Card Number:</label><br>
        <input type="text" id="cardNumber" name="cardNumber" required><br><br>

        <label for="expiryDate">Expiry Date:</label><br>
        <input type="date" id="expiryDate" name="expiryDate" required><br><br>

        <button type="submit">Submit Payment</button>
    </form>
</body>
</html>
