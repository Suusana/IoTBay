<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="com.bean.Payment" %>
<%@ page import="com.bean.Customer" %>
<%@ page import="com.enums.Status" %>
<%@ page import="com.util.Utils" %>

<%
    List<Payment> paymentList = (List<Payment>) request.getAttribute("paymentList");
    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    List<Payment> savedPayments = new java.util.ArrayList<>();
    List<Payment> completedPayments = new java.util.ArrayList<>();

    if (paymentList != null) {
        for (Payment p : paymentList) {
            if ("Paid".equalsIgnoreCase(p.getStatus())) {
                completedPayments.add(p);
            } else {
                savedPayments.add(p);
            }
        }
    }

    Customer customer = new Customer();
    if (session.getAttribute("loggedInUser") != null) {
        customer = (Customer) session.getAttribute("loggedInUser");
    } else {
        customer.setUsername(Status.GUEST.getStatus());
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Payments</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/base.css?<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/HeaderAndFooter.css?<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/PaymentStyle.css?<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<div class="header">
    <a href="<%=request.getContextPath()%>/home">
        <img src="<%=request.getContextPath()%>/assets/img/Logo.png" alt="IotBay Logo">
    </a>
    <menu>
        <a href="<%= request.getContextPath()%>/home"><span>Home</span></a>
        <a href="<%= request.getContextPath()%>/productServlet"><span>Shop</span></a>
        <a href="<%= request.getContextPath()%>/viewOrder"><span>Order</span></a>
        <a href="<%= request.getContextPath()%>/ViewPayment"><span class="selected">Payment</span></a>
    </menu>
    <menu class="icon">
        <a href="<%=request.getContextPath()%>/ViewUserDetailsServlet">
            <i class="fa-solid fa-circle-user fa-2x"></i>
            <span><%= customer.getFirstName() != null ? Utils.capitaliseFirst(customer.getFirstName()) : Status.GUEST.getStatus() %></span>
        </a>
        <a href="<%=request.getContextPath()%>/GetByProductNameToCustomer">
            <i class="fa-solid fa-magnifying-glass fa-2x"></i><span>Search</span>
        </a>
        <a href="#"><i class="fa-solid fa-cart-shopping fa-2x"></i><span>Cart</span></a>
        <% if (session.getAttribute("loggedInUser") != null) { %>
        <a href="<%=request.getContextPath()%>/views/logout.jsp">
            <i class="fa-solid fa-right-from-bracket fa-2x"></i><span>Log Out</span>
        </a>
        <% } %>
    </menu>
</div>

<div class="mainContainer">
    <h2 class="title">Payments Overview</h2>

    <form method="get" action="ViewPayment" class="searchBar">
        <label>Payment ID:</label>
        <input type="text" name="searchPaymentId" placeholder="e.g. 101" />
        <label>Payment Date:</label>
        <input type="text" name="searchDate" placeholder="yyyy-mm-dd" />
        <button type="submit">Search</button>
    </form>

    <% String message = (String) request.getAttribute("message");
        if (message != null) { %>
    <p style="color: red;"><%= message %></p>
    <% } %>

    <!-- Saved Payments -->
    <h3 class="subtitle">Saved Payments</h3>
    <table class="infoTable">
        <thead>
        <tr>
            <th>Payment ID</th>
            <th>Card Holder / Account Name</th>
            <th>Card Number / Bank Info</th>
            <th>Amount</th>
            <th>Payment Date</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <% if (!savedPayments.isEmpty()) {
            for (Payment payment : savedPayments) {
                String method = payment.getMethod();
                String holder = (payment.getCardHolder() != null) ? payment.getCardHolder() : payment.getAccountName();
                String displayInfo = "-";

                if ("Credit Card".equalsIgnoreCase(method)) {
                    String cardNum = payment.getCardNumber();
                    displayInfo = (cardNum != null && cardNum.length() >= 4) ?
                            "**** **** **** " + cardNum.substring(cardNum.length() - 4) : "N/A";
                } else if ("Bank Transfer".equalsIgnoreCase(method)) {
                    String bsb = payment.getBsb();
                    String acc = payment.getAccountNumber();
                    String maskedBsb = (bsb != null && bsb.length() >= 3) ? "***" + bsb.substring(bsb.length() - 3) : "***";
                    String maskedAcc = (acc != null && acc.length() >= 3) ? "***" + acc.substring(acc.length() - 3) : "***";
                    displayInfo = "BSB: " + maskedBsb + ", Acc: " + maskedAcc;
                }

                String amountFormatted = (payment.getAmount() != null) ? currencyFormat.format(payment.getAmount()) : "N/A";
                String paymentDateFormatted = (payment.getPaymentDate() != null) ? dateTimeFormat.format(payment.getPaymentDate()) : "N/A";
        %>
        <tr>
            <td><%= payment.getPaymentId() %></td>
            <td><%= holder %></td>
            <td><%= displayInfo %></td>
            <td><%= amountFormatted %></td>
            <td><%= paymentDateFormatted %></td>
            <td>
                <form action="<%= request.getContextPath() %>/EditPayment" method="get" style="display:inline;" onsubmit="return confirmEdit();">
                    <input type="hidden" name="paymentId" value="<%= payment.getPaymentId() %>"/>
                    <button type="submit">Edit</button>
                </form>
                <form action="<%= request.getContextPath() %>/DeletePayment" method="post" style="display:inline;" onsubmit="return confirmCancel();">
                    <input type="hidden" name="paymentId" value="<%= payment.getPaymentId() %>"/>
                    <button type="submit">Cancel</button>
                </form>
            </td>
        </tr>
        <% } } else { %>
        <tr><td colspan="6">No saved payments.</td></tr>
        <% } %>
        </tbody>
    </table>

    <!-- Completed Payments -->
    <h3 class="subtitle">Completed Payments</h3>
    <table class="infoTable">
        <thead>
        <tr>
            <th>Payment ID</th>
            <th>Card Holder / Account Name</th>
            <th>Card Number / Bank Info</th>
            <th>Amount</th>
            <th>Payment Date</th>
        </tr>
        </thead>
        <tbody>
        <% if (!completedPayments.isEmpty()) {
            for (Payment payment : completedPayments) {
                String method = payment.getMethod();
                String holder = (payment.getCardHolder() != null) ? payment.getCardHolder() : payment.getAccountName();
                String displayInfo = "-";

                if ("Credit Card".equalsIgnoreCase(method)) {
                    String cardNum = payment.getCardNumber();
                    displayInfo = (cardNum != null && cardNum.length() >= 4) ?
                            "**** **** **** " + cardNum.substring(cardNum.length() - 4) : "N/A";
                } else if ("Bank Transfer".equalsIgnoreCase(method)) {
                    String bsb = payment.getBsb();
                    String acc = payment.getAccountNumber();
                    String maskedBsb = (bsb != null && bsb.length() >= 3) ? "***" + bsb.substring(bsb.length() - 3) : "***";
                    String maskedAcc = (acc != null && acc.length() >= 3) ? "***" + acc.substring(acc.length() - 3) : "***";
                    displayInfo = "BSB: " + maskedBsb + ", Acc: " + maskedAcc;
                }

                String amountFormatted = (payment.getAmount() != null) ? currencyFormat.format(payment.getAmount()) : "N/A";
                String paymentDateFormatted = (payment.getPaymentDate() != null) ? dateTimeFormat.format(payment.getPaymentDate()) : "N/A";
        %>
        <tr>
            <td><%= payment.getPaymentId() %></td>
            <td><%= holder %></td>
            <td><%= displayInfo %></td>
            <td><%= amountFormatted %></td>
            <td><%= paymentDateFormatted %></td>
        </tr>
        <% } } else { %>
        <tr><td colspan="5">No completed payments.</td></tr>
        <% } %>
        </tbody>
    </table>

    <div style="text-align: center; margin: 30px 0;">
        <form action="<%= request.getContextPath() %>/home" method="get">
            <button class="actionButton" type="submit">Back to Home</button>
        </form>
    </div>
</div>

<div class="footer">
    <hr>
    <div>
        <div class="section">
            <h6 id="dif">IoTBay</h6><br>
            <span>The most complete range of IoT devices to upgrade your life at the touch of a button.</span>
        </div>
        <div class="section">
            <h6>Links</h6>
            <a href="<%=request.getContextPath()%>/home">Home</a>
            <a href="<%=request.getContextPath()%>/productServlet">Shop</a>
            <a href="<%=request.getContextPath()%>/viewOrder">Order</a>
            <a href="<%=request.getContextPath()%>/ViewPayment">Payment</a>
        </div>
        <div class="section">
            <h6>Contact Us</h6>
            <span>Address: 123 IotBay, Sydney</span>
            <span>Phone Number: +61 0499999999</span>
            <span>Email: IotBay@example.com</span>
        </div>
        <div class="section">
            <h6>Follow Us</h6>
            <a href="https://www.instagram.com/"><i class="fa-brands fa-instagram fa-lg"></i> Instagram</a>
            <a href="https://www.facebook.com/"><i class="fa-brands fa-facebook fa-lg"></i> Facebook</a>
            <a href="https://discord.com/"><i class="fa-brands fa-discord fa-lg"></i> Discord</a>
            <a href="https://x.com/?lang=en"><i class="fa-brands fa-x-twitter fa-lg"></i> Twitter</a>
        </div>
    </div>
    <hr>
    <p>©2025 IoTBay Group 4. All Rights Reserved.</p>
</div>

<script>
    function confirmEdit() {
        return confirm("Are you sure you want to edit this payment?");
    }

    function confirmCancel() {
        return confirm("Are you sure you want to cancel this payment?");
    }
</script>

</body>
</html>
