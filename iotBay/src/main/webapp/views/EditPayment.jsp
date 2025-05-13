<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Payment" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    Payment payment = (Payment) request.getAttribute("payment");

    String expiryStr = "";
    String cardNumber = "";
    String cardHolder = "";
    String cvv = "";
    int paymentId = -1;

    if (payment != null) {
        if (payment.getExpiryDate() != null) {
            expiryStr = new SimpleDateFormat("yyyy-MM-dd").format(payment.getExpiryDate());
        }
        cardNumber = (payment.getCardNumber() != null) ? payment.getCardNumber() : "";
        cardHolder = (payment.getCardHolder() != null) ? payment.getCardHolder() : "";
        cvv = (payment.getCvv() != null) ? payment.getCvv() : "";
        paymentId = payment.getPaymentId();
    }

    // 카드번호 분리 (앞은 마스킹, 뒤 4자리는 유지)
    String card1 = "", card2 = "", card3 = "", card4 = "";
    if (cardNumber.length() == 16) {
        card1 = cardNumber.substring(0, 4);
        card2 = cardNumber.substring(4, 8);
        card3 = cardNumber.substring(8, 12);
        card4 = cardNumber.substring(12);
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Payment</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <style>
        body {
            background-color: #FFF3E3;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            font-family: Arial, sans-serif;
        }

        .card {
            background-color: #fff;
            padding: 30px 40px 50px 40px;
            border-radius: 20px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            width: 450px;
        }

        h2 {
            font-size: 28px;
            font-weight: 800;
            color: #B88E2F;
            text-align: center;
            margin-bottom: 25px;
        }

        label {
            font-weight: bold;
            display: block;
            margin: 12px 0 5px 0;
            color: #333;
        }

        input[type="text"],
        input[type="date"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 14px;
        }

        .card-number-group {
            display: flex;
            gap: 10px;
        }

        .card-number-group input {
            width: 22%;
            text-align: center;
            font-size: 18px;
            letter-spacing: 2px;
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
            width: 100%;
            margin-top: 25px;
        }

        .style1:hover {
            background-color: #B88E2F;
            color: #fff;
        }

        .error {
            color: red;
            font-size: 14px;
            margin-bottom: 10px;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="card">
    <h2>Edit Payment</h2>

    <% if (request.getAttribute("error") != null) { %>
    <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>

    <form action="<%= request.getContextPath() %>/UpdatePayment" method="post" onsubmit="return validatePaymentForm()">
        <input type="hidden" name="paymentId" value="<%= paymentId %>"/>
        <input type="hidden" name="cardNumber" id="fullCardNumber" />

        <label for="cardHolder">Card Holder:</label>
        <input type="text" name="cardHolder" id="cardHolder" value="<%= cardHolder %>" required />

        <label>Card Number:</label>
        <div class="card-number-group">
            <input type="password" maxlength="4" class="card-part" id="card1" value="<%= card1 %>" required />
            <input type="password" maxlength="4" class="card-part" id="card2" value="<%= card2 %>" required />
            <input type="password" maxlength="4" class="card-part" id="card3" value="<%= card3 %>" required />
            <input type="password" maxlength="4" class="card-part" id="card4" value="<%= card4 %>" required />
        </div>

        <label for="expiryDate">Expiry Date:</label>
        <input type="date" name="expiryDate" id="expiryDate" value="<%= expiryStr %>" required />

        <label for="cvv">CVC:</label>
        <input type="password" name="cvv" id="cvv" value="<%= cvv %>" required />

        <button type="submit" class="style1">Update</button>
    </form>
</div>

<script>
    const parts = ["card1", "card2", "card3", "card4"];

    parts.forEach((id, index) => {
        const input = document.getElementById(id);
        input.addEventListener("input", (e) => {
            e.target.value = e.target.value.replace(/\D/g, "");
            if (e.target.value.length === 4 && index < parts.length - 1) {
                document.getElementById(parts[index + 1]).focus();
            }
        });

        input.addEventListener("keydown", (e) => {
            if (e.key === "Backspace" && input.value === "" && index > 0) {
                document.getElementById(parts[index - 1]).focus();
            }
        });
    });

    function validatePaymentForm() {
        const cardNumber = parts.map(id => document.getElementById(id).value).join("");
        if (!/^\d{16}$/.test(cardNumber)) {
            alert("Card number must be 16 digits.");
            return false;
        }

        const cvc = document.getElementById("cvv").value;
        if (!/^\d{3,4}$/.test(cvc)) {
            alert("CVC must be 3 or 4 digits.");
            return false;
        }

        document.getElementById("fullCardNumber").value = cardNumber;
        return true;
    }
</script>
</body>
</html>
