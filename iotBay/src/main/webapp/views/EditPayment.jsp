<form action="UpdatePayment" method="post">
    <input type="hidden" name="paymentId" value="<%= payment.getPaymentId() %>"/>

    <label>Method:</label>
    <input type="text" name="method" value="<%= payment.getMethod() %>" required><br>

    <label>Card Holder:</label>
    <input type="text" name="cardHolder" value="<%= payment.getCardHolder() %>" required><br>

    <label>Amount:</label>
    <input type="text" name="amount" value="<%= payment.getAmount() %>" required><br>

    <label>Status:</label>
    <input type="text" name="status" value="<%= payment.getStatus() %>" required><br>

    <button type="submit">Update Payment</button>
</form>
