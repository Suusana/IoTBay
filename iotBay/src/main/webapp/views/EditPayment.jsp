<%@ page import="com.bean.Payment" %>
<%
    Payment payment = (Payment) request.getAttribute("payment");
%>
<h2>Edit Payment</h2>
<form method="post" action="${pageContext.request.contextPath}/UpdatePayment">
    <input type="hidden" name="paymentId" value="<%= payment.getPaymentId() %>">

    <label>Card Holder:</label>
    <input type="text" name="cardHolder" value="<%= payment.getCardHolder() %>" required><br>

    <label>Card Number:</label>
    <input type="text" name="cardNumber" value="<%= payment.getCardNumber() %>" required><br>

    <label>Expiry Date:</label>
    <input type="date" name="expiryDate" value="<%= payment.getExpiryDate() %>" required><br>

    <label>CVV:</label>
    <input type="text" name="cvv" value="<%= payment.getCvv() %>" required><br>

    <label>Amount:</label>
    <input type="text" name="amount" value="<%= payment.getAmount() %>" required><br>

    <label>Status:</label>
    <input type="text" name="status" value="<%= payment.getStatus() %>" required><br>

    <button type="submit">Update</button>
</form>
