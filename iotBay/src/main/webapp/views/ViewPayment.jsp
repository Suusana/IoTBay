<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bean.Payment" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.bean.Customer" %>
<%@ page import="com.enums.Status" %>
<%@ page import="com.util.Utils" %>

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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/base.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/HeaderAndFooter.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <style>
        .main-content {
            font-family: Arial, sans-serif;
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
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            animation: fadeInCard 1s ease-in-out;
            width: 1000px;
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
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            z-index: 999;
        }

        #confirmModal .modal-content {
            background: #fff;
            width: 400px;
            margin: 100px auto;
            padding: 30px;
            border-radius: 15px;
            text-align: center;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
        }
    </style>
</head>
<body>
<%
    Customer customer = new Customer();
    if (session.getAttribute("loggedInUser") != null) {
        customer = (Customer) session.getAttribute("loggedInUser");
    } else {
        customer.setUsername(Status.GUEST.getStatus());
    }
%>
<!-- header -->
<div class="header">
    <!-- Logo -->
    <a href="<%=request.getContextPath()%>/home">
        <img src="<%=request.getContextPath()%>/assets/img/Logo.png" alt="IotBay Logo">
    </a>
    <!-- menu -->
    <menu>
        <a href="<%= request.getContextPath()%>/home"><span>Home</span></a>
        <a href="<%= request.getContextPath() %>/productServlet"><span>Shop</span></a>
        <a href="<%= request.getContextPath() %>/viewOrder"><span>Order</span></a>
        <a href="<%= request.getContextPath() %>/ViewPayment" class="selected"><span>Payment</span></a>
    </menu>

    <!-- icon menu -->
    <menu class="icon">
        <a href="<%=request.getContextPath()%>/ViewUserDetailsServlet">
            <i class="fa-solid fa-circle-user fa-2x"></i>
            <span><%= customer.getFirstName() != null ? Utils.capitaliseFirst(customer.getFirstName()) : Status.GUEST.getStatus()%></span>
        </a>
        <a href="<%=request.getContextPath()%>/GetByProductNameToCustomer">
            <i class="fa-solid fa-magnifying-glass fa-2x"></i>
            <span>Search</span>
        </a>
        <a href="#">
            <i class="fa-solid fa-cart-shopping fa-2x"></i>
            <span>Cart</span>
        </a>
        <%
            if (session.getAttribute("loggedInUser") != null) {
        %>
        <a href="<%=request.getContextPath()%>/views/logout.jsp">
            <i class="fa-solid fa-right-from-bracket fa-2x"></i>
            <span>Log Out</span>
        </a>
        <%
            }
        %>
    </menu>
</div>
<div class="main-content">
    <div class="card">
        <h2>Payments for Orders:
        </h2>

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
                <td><%= payment.getPaymentId() %>
                </td>
                <td><%= payment.getCardHolder() %>
                </td>
                <td><%= maskedCard %>
                </td>
                <td><%= expiryFormatted %>
                </td>
                <td><%= payment.getCvv() %>
                </td>
                <td><%= paymentDateFormatted %>
                </td>
                <td>
                    <a href="<%= request.getContextPath() %>/EditPayment?paymentId=<%= payment.getPaymentId() %>"
                       class="action-button">Edit</a>
                    |
                    <button type="button" class="action-button"
                            onclick="showConfirmModal(<%= payment.getPaymentId() %>)">Delete
                    </button>
                </td>
            </tr>
            <% }
            } else { %>
            <tr>
                <td colspan="7">No payments found.</td>
            </tr>
            <% } %>
            </tbody>
        </table>

        <div class="center-button">
            <form action="<%= request.getContextPath() %>/home" method="get">
                <button type="submit" class="style1">Go to Home</button>
            </form>
        </div>
    </div>
</div>
<!-- 모달 -->
<div id="confirmModal">
    <div class="modal-content">
        <p style="font-size: 18px; margin-bottom: 20px;">Are you sure you want to delete this payment?</p>
        <form id="deleteForm" action="<%= request.getContextPath() %>/DeletePayment" method="post">
            <input type="hidden" name="paymentId" id="modalPaymentId"/>
            <button type="submit" class="style1">Yes, Delete</button>
            <button type="button" class="style1" style="margin-left: 15px;" onclick="hideConfirmModal()">Cancel</button>
        </form>
    </div>
</div>
<!-- footer -->
<div class="footer">
    <hr>
    <div>
        <div class="section">
            <h6 id="dif">IoTBay</h6><br>
            <span>The most complete range of IoT devices to upgrade your life at the touch of a button.</span>
        </div>
        <div class="section">
            <h6>Links</h6>
            <a href="<%=request.getContextPath()%>/home"><span>Home</span></a>
            <a href="<%=request.getContextPath()%>/productServlet"><span>Shop</span></a>
            <a href="<%=request.getContextPath()%>/viewOrder"><span>Order</span></a>
            <a href="#"><span>Category</span></a>
        </div>
        <div class="section">
            <h6>Contact Us</h6>
            <span>Address: 123 IotBay, Sydney</span>
            <span>Phone Number: +61 0499999999</span>
            <span>Email Address: IotBay@example.com</span>
        </div>
        <div class="section">
            <h6>Follow Us</h6>
            <a href="https://www.instagram.com/">
                <i class="fa-brands fa-instagram fa-lg"></i>
                <span>Instagram</span>
            </a>
            <a href="https://www.facebook.com/">
                <i class="fa-brands fa-facebook fa-lg"></i>
                <span>Facebook</span>
            </a>
            <a href="https://discord.com/">
                <i class="fa-brands fa-discord fa-lg"></i>
                <span>Discord</span>
            </a>
            <a href="https://x.com/?lang=en">
                <i class="fa-brands fa-x-twitter fa-lg"></i>
                <span>Twitter</span>
            </a>
        </div>
    </div>
    <hr>
    <p>©2025. IoTBay Group 4 All Right Reserved</p>
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
