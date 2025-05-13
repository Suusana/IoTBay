<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Order" %>
<%@ page import="com.bean.Product" %>
<%
    Order order = (Order) session.getAttribute("order");
    Product product = (Product) session.getAttribute("product");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Payment</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <style>
        body {
            background-color: #FFF3E3;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            animation: fadeIn 1s ease-in-out;
            margin: 0;
            font-family: Arial, sans-serif;
        }

        .card {
            background-color: #fff;
            padding: 30px 40px 50px 40px;
            border-radius: 20px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            width: 450px;
            animation: fadeInCard 1s ease-in-out;
        }

        @keyframes fadeInCard {
            from { opacity: 0; transform: scale(0.95); }
            to { opacity: 1; transform: scale(1); }
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
            font-size: 13px;
            margin-top: 5px;
        }
    </style>
</head>
<body>
<div class="card">
    <h2>Add Payment</h2>
    <form action="<%= request.getContextPath() %>/AddPayment" method="post" onsubmit="return validatePaymentForm()">
        <label for="firstName">First Name:</label>
        <input type="text" name="firstName" id="firstName" required />

        <label for="lastName">Last Name:</label>
        <input type="text" name="lastName" id="lastName" required />

        <label>Card Number:</label>
        <div class="card-number-group">
            <input type="password" maxlength="4" class="card-part" id="card1" required />
            <input type="password" maxlength="4" class="card-part" id="card2" required />
            <input type="password" maxlength="4" class="card-part" id="card3" required />
            <input type="password" maxlength="4" class="card-part" id="card4" required />
        </div>
        <input type="hidden" name="cardNumber" id="fullCardNumber" />

        <label for="expiryDate">Expiry Date:</label>
        <input type="date" name="expiryDate" id="expiryDate" required />

        <label for="cvv">CVC:</label>
        <input type="password" name="cvv" id="cvv" required />

        <button type="submit" class="style1">Add payment</button>
    </form>
</div>

<script>
    const parts = ["card1", "card2", "card3", "card4"];

    parts.forEach((id, index) => {
        const input = document.getElementById(id);
        input.addEventListener("input", (e) => {
            e.target.value = e.target.value.replace(/\D/g, ""); // allow only digits
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
