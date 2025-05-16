<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Object rawOrderId = request.getAttribute("orderId");
    int orderId = -1;
    if (rawOrderId != null) {
        try {
            if (rawOrderId instanceof Integer) {
                orderId = (Integer) rawOrderId;
            } else if (rawOrderId instanceof String) {
                orderId = Integer.parseInt((String) rawOrderId);
            }
        } catch (Exception e) {
            orderId = -1;
        }
    }

    String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Select Payment Method</title>
    <style>
        body {
            background-color: #FFF3E3;
            font-family: Arial, sans-serif;
            padding: 20px;
        }

        .field-section {
            margin-top: 20px;
            padding: 15px;
            background-color: #fff;
            border-radius: 8px;
        }

        .card-number-group {
            display: flex;
            gap: 10px;
        }

        .card-number-group input {
            width: 60px;
            text-align: center;
            font-size: 16px;
        }

        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0; top: 0;
            width: 100%; height: 100%;
            background-color: rgba(0,0,0,0.6);
        }

        .modal-content {
            background-color: #fff;
            margin: 15% auto;
            padding: 20px;
            border-radius: 10px;
            width: 50%;
        }

        .modal button {
            margin-right: 10px;
        }
    </style>

    <script>
        function toggleFields() {
            const method = document.getElementById("paymentMethod").value;
            document.getElementById("cardFields").style.display = method === "Credit Card" ? "block" : "none";
            document.getElementById("bankFields").style.display = method === "Bank Transfer" ? "block" : "none";
        }

        function validateFormAndConfirm() {
            const method = document.getElementById("paymentMethod").value;
            if (method === "Credit Card") {
                const parts = ["card1", "card2", "card3", "card4"];
                const cardNumber = parts.map(id => document.getElementById(id).value).join("");
                const cvc = document.getElementById("cvc").value;

                if (!/^\d{16}$/.test(cardNumber)) {
                    alert("Card number must be 16 digits.");
                    return false;
                }

                if (!/^\d{3}$/.test(cvc)) {
                    alert("CVC must be 3 digits.");
                    return false;
                }

                document.getElementById("cardNumber").value = cardNumber;
            }

            openConfirm();
            return false;
        }

        function openConfirm() {
            document.getElementById("confirmModal").style.display = "block";
        }

        function closeConfirm() {
            document.getElementById("confirmModal").style.display = "none";
        }

        function submitForm() {
            document.getElementById("paymentForm").submit();
        }

        window.onload = () => {
            toggleFields();

            // 자동 포커스 이동 설정
            const parts = ["card1", "card2", "card3", "card4"];
            parts.forEach((id, index) => {
                const input = document.getElementById(id);
                input.addEventListener("input", (e) => {
                    e.target.value = e.target.value.replace(/\D/g, ""); // 숫자만 허용
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
        };
    </script>
</head>

<body>
<h2>Select Payment Method</h2>
<% if (message != null) { %>
<p style="color:red;"><%= message %></p>
<% } %>

<form method="post" action="ConfirmPayment" id="paymentForm">
    <input type="hidden" name="orderId" value="<%= orderId %>">
    <input type="hidden" name="cardNumber" id="cardNumber">

    <!-- Payment Method -->
    <label>Payment Method:</label>
    <select name="paymentMethod" id="paymentMethod" onchange="toggleFields()" required>
        <option value="Credit Card">Credit Card</option>
        <option value="Bank Transfer">Bank Transfer</option>
    </select>

    <!-- Credit Card -->
    <div id="cardFields" class="field-section">
        <label>First Name:</label>
        <input type="text" name="firstName" required>

        <label>Last Name:</label>
        <input type="text" name="lastName" required>

        <label>Card Number:</label>
        <div class="card-number-group">
            <input type="password" name="cardPart1" id="card1" maxlength="4" required>
            <input type="password" name="cardPart2" id="card2" maxlength="4" required>
            <input type="password" name="cardPart3" id="card3" maxlength="4" required>
            <input type="password" name="cardPart4" id="card4" maxlength="4" required>
        </div><br/>

        <label>Expiry Date:</label>
        <input type="date" name="expiryDate" required>

        <label>CVC:</label>
        <input type="password" name="cvc" id="cvc" maxlength="3" pattern="\d{3}" required>
    </div>

    <!-- Bank Transfer -->
    <div id="bankFields" class="field-section" style="display:none;">
        <label>BSB:</label>
        <input type="text" name="bsb" maxlength="6" pattern="\d{6}"><br/><br/>
        <label>Account Name:</label>
        <input type="text" name="accountName"><br/><br/>
        <label>Account Number:</label>
        <input type="text" name="accountNumber" maxlength="10" pattern="\d{6,10}"><br/>
    </div>

    <button type="button" onclick="validateFormAndConfirm()">Proceed</button>
</form>

<!-- Confirm Modal -->
<div id="confirmModal" class="modal">
    <div class="modal-content">
        <h3>Confirm Your Payment</h3>
        <p>Please confirm your payment information before proceeding.</p>
        <button onclick="submitForm()">Confirm</button>
        <button onclick="closeConfirm()">Cancel</button>
    </div>
</div>
</body>
</html>
