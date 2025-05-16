<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="com.bean.Payment" %>

<%
    String method = (String) request.getAttribute("paymentMethod");
    Integer orderId = (Integer) request.getAttribute("orderId");
    Payment selectedPayment = (Payment) request.getAttribute("selectedPayment"); // if reusing
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirm Payment</title>
</head>
<body>

<h2>Confirm Payment</h2>

<form action="ConfirmPayment" method="post">
    <input type="hidden" name="orderId" value="<%= orderId %>" />
    <input type="hidden" name="paymentMethod" value="<%= method %>" />

    <% if ("Credit Card".equalsIgnoreCase(method)) { %>
    <% if (selectedPayment != null) { %>
    <p>Using saved card:</p>
    <p>Card Holder: <%= selectedPayment.getCardHolder() %></p>
    <p>Card Number: **** **** **** <%= selectedPayment.getCardNumber().substring(selectedPayment.getCardNumber().length() - 4) %></p>
    <p>Expiry Date: <%= selectedPayment.getExpiryDate() %></p>
    <input type="hidden" name="cardHolder" value="<%= selectedPayment.getCardHolder() %>" />
    <input type="hidden" name="cardNumber" value="<%= selectedPayment.getCardNumber() %>" />
    <input type="hidden" name="expiryDate" value="<%= selectedPayment.getExpiryDate() %>" />
    <input type="hidden" name="cvc" value="<%= selectedPayment.getCvc() %>" />
    <% } else { %>
    <p>New Credit Card:</p>
    <p>Card Holder: <%= request.getAttribute("cardHolder") %></p>
    <p>Card Number: **** **** **** <%= ((String) request.getAttribute("cardNumber")).substring(((String) request.getAttribute("cardNumber")).length() - 4) %></p>
    <p>Expiry Date: <%= request.getAttribute("expiryDate") %></p>
    <input type="hidden" name="cardHolder" value="<%= request.getAttribute("cardHolder") %>" />
    <input type="hidden" name="cardNumber" value="<%= request.getAttribute("cardNumber") %>" />
    <input type="hidden" name="expiryDate" value="<%= request.getAttribute("expiryDate") %>" />
    <input type="hidden" name="cvc" value="<%= request.getAttribute("cvc") %>" />
    <% } %>
    <% } else if ("Bank Transfer".equalsIgnoreCase(method)) { %>
    <p>Bank Transfer Details:</p>
    <p>BSB: <%= request.getAttribute("bsb") %></p>
    <p>Account Name: <%= request.getAttribute("accountName") %></p>
    <p>Account Number: <%= request.getAttribute("accountNumber") %></p>

    <input type="hidden" name="bsb" value="<%= request.getAttribute("bsb") %>" />
    <input type="hidden" name="accountName" value="<%= request.getAttribute("accountName") %>" />
    <input type="hidden" name="accountNumber" value="<%= request.getAttribute("accountNumber") %>" />
    <% } %>

    <button type="submit">Confirm Payment</button>
</form>

<form action="SelectPayment.jsp" method="get">
    <input type="hidden" name="orderId" value="<%= orderId %>" />
    <button type="submit">Go Back</button>
</form>

</body>
</html>
