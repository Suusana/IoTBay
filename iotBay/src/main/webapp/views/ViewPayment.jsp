<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bean.Payment" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Date" %>

<%
    List<Payment> paymentList = (List<Payment>) request.getAttribute("paymentList");
    int orderId = (request.getAttribute("orderId") != null) ? (Integer) request.getAttribute("orderId") : -1;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Payments</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #FFF3E3;
            display: flex;
            justify-content: center;
            align-items: center;
            animation: fadeIn 1s ease-in-out;
            margin: 0;
            padding: 30px;
        }

        .card {
            background-color: #fff;
            padding: 30px 40px 50px 40px;
            border-radius: 20px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            animation: fadeInCard 1s ease-in-out;
            width: 1000px;
        }

        @keyframes fadeInCard {
            from { opacity: 0; transform: scale(0.95); }
            to { opacity: 1; transform: scale(1); }
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
            font-size: 14px;
        }

        th {
            background-color: #f9f1e3;
            color: #B88E2F;
            font-weight: bold;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        .action-button {
            background-color: transparent;
            border: none;
            color: #B88E2F;
            cursor: pointer;
            font-weight: bold;
            font-size: 14px;
        }

        .action-button:hover {
            text-decoration: underline;
        }

        .center-button {
            margin-top: 40px;
            display: flex;
            justify-content: center;
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
        }

        .style1:hover {
            background-color: #B88E2F;
            color: #fff;
        }

        /* 모달 스타일 */
        #confirmModal {
            display: none;
            position: fixed;
            top: 0; left: 0;
            width: 100%; height: 100%;
            background: rgba(0,0,0,0.5);
            z-index: 999;
        }

        #confirmModal .modal-content {
            background: #fff;
            width: 400px;
            margin: 100px auto;
            padding: 30px;
            border-radius: 15px;
            text-align: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.3);
        }
    </style>
</head>
<body>
<div class="card">
    <h2>Payments for Order ID: <%= orderId %></h2>

    <table>
        <thead>
        <tr>
            <th>Payment ID</th>
            <th>Card Holder</th>
            <th>Card Number</th>
            <th>Expiry Date</th>
            <th>CVC</th>
            <th>Payment Date</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% if (paymentList != null && !paymentList.isEmpty()) {
            for (Payment payment : paymentList) {
                String cardNum = payment.getCardNumber();
                String maskedCard = (cardNum != null && cardNum.length() >= 4)
                        ? "**** **** **** " + cardNum.substring(cardNum.length() - 4)
                        : cardNum;

                String expiryFormatted = (payment.getExpiryDate() != null) ? sdf.format(payment.getExpiryDate()) : "N/A";
                String paymentDateFormatted = (payment.getPaymentDate() != null) ? sdf.format(payment.getPaymentDate()) : "N/A";
        %>
        <tr>
            <td><%= payment.getPaymentId() %></td>
            <td><%= payment.getCardHolder() %></td>
            <td><%= maskedCard %></td>
            <td><%= expiryFormatted %></td>
            <td><%= payment.getCvv() %></td>
            <td><%= paymentDateFormatted %></td>
            <td>
                <a href="<%= request.getContextPath() %>/EditPayment?paymentId=<%= payment.getPaymentId() %>" class="action-button">Edit</a>
                |
                <button type="button" class="action-button" onclick="showConfirmModal(<%= payment.getPaymentId() %>)">Delete</button>
            </td>
        </tr>
        <% } } else { %>
        <tr><td colspan="7">No payments found.</td></tr>
        <% } %>
        </tbody>
    </table>

    <div class="center-button">
        <form action="<%= request.getContextPath() %>/home" method="get">
            <button type="submit" class="style1">Go to Home</button>
        </form>
    </div>
</div>

<!-- 모달 -->
<div id="confirmModal">
    <div class="modal-content">
        <p style="font-size: 18px; margin-bottom: 20px;">Are you sure you want to delete this payment?</p>
        <form id="deleteForm" action="<%= request.getContextPath() %>/DeletePayment" method="post">
            <input type="hidden" name="paymentId" id="modalPaymentId" />
            <button type="submit" class="style1">Yes, Delete</button>
            <button type="button" class="style1" style="margin-left: 15px;" onclick="hideConfirmModal()">Cancel</button>
        </form>
    </div>
</div>

<!-- JS -->
<script>
    function showConfirmModal(paymentId) {
        document.getElementById("modalPaymentId").value = paymentId;
        document.getElementById("confirmModal").style.display = "block";
    }

    function hideConfirmModal() {
        document.getElementById("confirmModal").style.display = "none";
    }
</script>

</body>
</html>
