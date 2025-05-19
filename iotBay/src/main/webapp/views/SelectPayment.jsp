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

    String guestEmail = (String) session.getAttribute("guestEmail");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Select Payment</title>
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
            text-align: center;
        }

        .modal button {
            margin: 10px;
            padding: 10px 20px;
            border-radius: 6px;
            font-weight: bold;
            cursor: pointer;
        }

        .confirm {
            background-color: #B88E2F;
            color: white;
            border: none;
        }

        .cancel {
            background-color: transparent;
            color: #B88E2F;
            border: 1px solid #B88E2F;
        }
    </style>
</head>
<body>

<h2>Select Payment Method</h2>

<form id="paymentForm" action="<%= request.getContextPath() %>/ConfirmPayment" method="post">
    <input type="hidden" name="orderId" value="<%= orderId %>">
    <input type="hidden" name="guestEmail" value="<%= guestEmail %>">

    <label for="paymentMethod">Payment Method:</label>
    <select name="paymentMethod" id="paymentMethod" required onchange="toggleMethod()">
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
        <input type="password" name="cvc" maxlength="3" required />
    </div>

    <div id="bankSection" style="display:none;">
        <label>BSB:</label>
        <input type="text" name="bsb" maxlength="6" />
        <label>Account Name:</label>
        <input type="text" name="accountName" />
        <label>Account Number:</label>
        <input type="text" name="accountNumber" maxlength="10" />
    </div>

    <button type="button" class="submit-btn" onclick="openConfirm()">Proceed</button>
</form>

<div id="confirmModal" class="modal">
    <div class="modal-content">
        <h3>Confirm Your Payment</h3>
        <p>Are you sure you want to proceed with this payment?</p>
        <button class="confirm" onclick="submitForm()">Confirm</button>
        <button class="cancel" onclick="closeConfirm()">Cancel</button>
    </div>
</div>

<script>
    function toggleMethod() {
        const method = document.getElementById("paymentMethod").value;
        document.getElementById("cardSection").style.display = method === "Credit Card" ? "block" : "none";
        document.getElementById("bankSection").style.display = method === "Bank Transfer" ? "block" : "none";
    }

    function autoForward(current, nextId) {
        if (current.value.length === 4) {
            document.getElementsByName(nextId)[0].focus();
        }
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

    window.onload = toggleMethod;
</script>
</body>
</html>
