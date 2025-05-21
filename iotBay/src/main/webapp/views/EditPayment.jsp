<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = (String) request.getAttribute("error");
    int paymentId = request.getParameter("paymentId") != null ? Integer.parseInt(request.getParameter("paymentId")) : -1;
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Payment</title>
    <style>
        body {
            background-color: #FFF3E3;
            font-family: Arial, sans-serif;
            padding: 40px;
            margin: 0;
        }

        h2 {
            text-align: center;
            font-size: 28px;
            font-weight: bold;
            color: #B88E2F;
            margin-bottom: 30px;
        }

        form {
            background-color: #fff;
            padding: 30px;
            border-radius: 12px;
            max-width: 500px;
            margin: auto;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }

        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }

        input[type="text"], input[type="date"], input[type="password"], select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border-radius: 6px;
            border: 1px solid #ccc;
        }

        .card-group {
            display: flex;
            gap: 10px;
            margin-top: 5px;
        }

        .card-group input {
            width: 60px;
            text-align: center;
        }

        .submit-btn {
            margin-top: 25px;
            background-color: #B88E2F;
            color: #fff;
            border: none;
            padding: 10px 25px;
            font-size: 16px;
            font-weight: bold;
            border-radius: 6px;
            cursor: pointer;
        }

        .error {
            color: red;
            text-align: center;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>

<h2>Edit Payment</h2>

<% if (error != null && !error.isEmpty()) { %>
<div class="error"><%= error %></div>
<% } %>

<form id="editPaymentForm"
      action="<%= request.getContextPath() %>/UpdatePayment"
      method="post"
      onsubmit="return validateForm()">

    <input type="hidden" name="paymentId" value="<%= paymentId %>">
    <input type="hidden" name="cardNumber" id="cardNumber">

    <label for="paymentMethod">Payment Method:</label>
    <select name="actualMethod" id="paymentMethod" onchange="toggleMethod()" required>
        <option value="Credit Card">Credit Card</option>
        <option value="Bank Transfer">Bank Transfer</option>
    </select>

    <div id="cardSection">
        <label>Card Holder First Name:</label>
        <input type="text" name="firstName" required />

        <label>Card Holder Last Name:</label>
        <input type="text" name="lastName" required />

        <label>Card Number:</label>
        <div class="card-group">
            <input type="password" name="cardPart1" maxlength="4" required oninput="autoForward(this, 'cardPart2')" />
            <input type="password" name="cardPart2" maxlength="4" required oninput="autoForward(this, 'cardPart3')" />
            <input type="password" name="cardPart3" maxlength="4" required oninput="autoForward(this, 'cardPart4')" />
            <input type="password" name="cardPart4" maxlength="4" required />
        </div>

        <label>Expiry Date:</label>
        <input type="date" name="expiryDate" required />

        <label>CVC:</label>
        <input type="password" name="cvc" maxlength="4" required />
    </div>

    <div id="bankSection" style="display:none;">
        <label>BSB:</label>
        <input type="password" name="bsb" maxlength="6" required />

        <label>Account Name:</label>
        <input type="text" name="accountName" required />

        <label>Account Number:</label>
        <input type="password" name="accountNumber" maxlength="10" required />
    </div>

    <button type="submit" class="submit-btn">Confirm</button>
</form>

<script>
    function toggleMethod() {
        const method = document.getElementById("paymentMethod").value;
        const isCard = method === "Credit Card";

        document.getElementById("cardSection").style.display = isCard ? "block" : "none";
        document.getElementById("bankSection").style.display = isCard ? "none" : "block";

        const cardFields = document.querySelectorAll("#cardSection input");
        const bankFields = document.querySelectorAll("#bankSection input");

        cardFields.forEach(input => {
            input.required = isCard;
        });

        bankFields.forEach(input => {
            input.required = !isCard;
        });
    }

    function autoForward(current, nextId) {
        if (current.value.length === 4) {
            document.getElementsByName(nextId)[0].focus();
        }
    }

    function prepareCardNumber() {
        const parts = ["cardPart1", "cardPart2", "cardPart3", "cardPart4"];
        let cardNum = "";
        for (let i = 0; i < parts.length; i++) {
            const part = document.getElementsByName(parts[i])[0].value;
            if (!/^\d{4}$/.test(part)) {
                alert("Card number must be 16 digits total.");
                return false;
            }
            cardNum += part;
        }
        document.getElementById("cardNumber").value = cardNum;
        return true;
    }

    function validateForm() {
        const method = document.getElementById("paymentMethod").value;
        if (method === "Credit Card") {
            return prepareCardNumber();
        }
        return true;
    }

    window.onload = toggleMethod;
</script>

</body>
</html>
