<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Payment" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    int orderId = (Integer) request.getAttribute("orderId");
    List<Payment> previousPayments = (List<Payment>) request.getAttribute("previousPayments");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    // Check if a pending payment exists
    Payment pendingPayment = null;
    for (Payment p : previousPayments) {
        if ("Pending".equalsIgnoreCase(p.getStatus())) {
            pendingPayment = p;
            break;
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Select Payment Method</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/base.css">
    <style>
        body {
            background-color: #FFF3E3;
            font-family: 'Segoe UI', sans-serif;
        }

        .modal {
            width: 550px;
            margin: 100px auto;
            padding: 40px;
            background: #ffffff;
            border-radius: 15px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.1);
            text-align: center;
        }

        h2 {
            color: #B88E2F;
            margin-bottom: 20px;
        }

        .card-entry {
            margin: 20px 0;
            padding: 15px;
            border: 1px solid #B88E2F;
            border-radius: 10px;
            background-color: #fff9f0;
        }

        .btn {
            padding: 8px 18px;
            margin: 8px 4px;
            border: 1px solid #B88E2F;
            border-radius: 8px;
            font-size: 14px;
            font-weight: 600;
            background-color: white;
            color: #B88E2F;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .btn:hover {
            background-color: #B88E2F;
            color: white;
        }
    </style>
</head>
<body>

<div class="modal">
    <% if (previousPayments != null && !previousPayments.isEmpty()) { %>
    <h2>Select a saved card</h2>

    <% for (Payment p : previousPayments) {
        String masked = "**** **** **** " + p.getCardNumber().substring(p.getCardNumber().length() - 4);
    %>
    <div class="card-entry">
        <div><strong><%= masked %></strong> (exp: <%= sdf.format(p.getExpiryDate()) %>)</div>

        <!-- Submit form to reuse this saved card -->
        <form action="<%= request.getContextPath() %>/ReusePayment" method="post" style="display:inline;">
            <input type="hidden" name="orderId" value="<%= orderId %>"/>
            <input type="hidden" name="paymentId" value="<%= p.getPaymentId() %>"/>
            <button type="submit" class="btn">Use this card</button>
        </form>
    </div>
    <% } %>

    <!-- Button to use a new card (preserve pendingPaymentId if available) -->
    <div style="margin-top: 30px;">
        <form action="<%= request.getContextPath() %>/views/AddPayment.jsp" method="get">
            <input type="hidden" name="orderId" value="<%= orderId %>"/>
            <% if (pendingPayment != null) { %>
            <input type="hidden" name="pendingPaymentId" value="<%= pendingPayment.getPaymentId() %>"/>
            <% } %>
            <button type="submit" class="btn">Use new card</button>
        </form>
    </div>

    <% } else { %>
    <h2>No saved cards found</h2>
    <form action="<%= request.getContextPath() %>/views/AddPayment.jsp" method="get">
        <input type="hidden" name="orderId" value="<%= orderId %>"/>
        <% if (pendingPayment != null) { %>
        <input type="hidden" name="pendingPaymentId" value="<%= pendingPayment.getPaymentId() %>"/>
        <% } %>
        <button type="submit" class="btn">Add New Payment Method</button>
    </form>
    <% } %>
</div>

</body>
</html>
