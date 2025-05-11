<%@ page import="com.util.Utils" %>
<%@ page import="com.bean.Customer" %>
<%@ page import="com.enums.Status" %>
<%@ page import="com.bean.Product" %>
<%@ page import="com.bean.Order" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Payment Details</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/base.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/HeaderAndFooter.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/paymentDetail.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<%
    Customer customer = new Customer();
    if (session.getAttribute("loggedInUser") != null) {
        customer = (Customer) session.getAttribute("loggedInUser");
    } else {
        customer.setUsername(Status.GUEST.getStatus());
    }

    Product product = (Product) request.getAttribute("product");
    int quantity = Integer.parseInt(request.getParameter("quantity"));
    Order order = (Order) request.getAttribute("order");
%>

<body>
<div class="header">
    <!-- Logo -->
    <a href="<%=request.getContextPath()%>/home">
        <img src="<%=request.getContextPath()%>/assets/img/Logo.png" alt="IotBay Logo">
    </a>
    <!-- menu -->
    <menu>
        <a href="<%=request.getContextPath()%>/home"><span class="selected">Home</span></a>
        <a href="<%= request.getContextPath() %>/productServlet"><span>Shop</span></a>
        <a href="<%= request.getContextPath() %>/viewOrder"><span>Order</span></a>
        <a href=""><span>Category</span></a>
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
        <a href="<%=request.getContextPath()%>/views/cart.jsp">
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

<div class="container">
    <h2>Payment Information</h2>
    <form method="post" action="<%=request.getContextPath()%>/AddPayment">
        <table>
            <tr>
                <th>Payment Method:</th>
                <td>
                    <select name="paymentMethod">
                        <option value="Apple Pay">Apple Pay</option>
                        <option value="Credit Card">Credit Card</option>
                        <option value="Google Pay">Google Pay</option>
                        <option value="Afterpay">Afterpay</option>
                    </select>
                </td>
            </tr>
            <tr>
                <th>Account Number:</th>
                <td><input type="text" name="accountNumber" required></td>
            </tr>
            <tr>
                <th>Account Name:</th>
                <td><input type="text" name="accountName" required></td>
            </tr>
            <tr>
                <th>BSB:</th>
                <td><input type="text" name="bsb" required></td>
            </tr>
        </table>

        <h2>Product Information</h2>
        <table>
            <tr>
                <th>Product ID:</th>
                <td><%=product.getProductId()%>
                </td>
            </tr>
            <tr>
                <th>Name:</th>
                <td><%=product.getProductName()%>
                </td>
            </tr>
            <tr>
                <th>Quantity:</th>
                <td><%=quantity%>
                </td>
            </tr>
            <tr>
                <th>Price:</th>
                <td><%=quantity * product.getPrice()%>
                </td>
            </tr>
        </table>

        <div class="btn-container">
            <input type="hidden" name="productId" value="<%= product.getProductId()%>">
            <input type="hidden" name="quantity" value="<%= quantity%>">
<%--            <input type="hidden" name="orderId" value="<%= order.getOrderId()%>">--%>

            <button class="btn-back" onclick="history.back()">Back</button>
            <button class="btn-back" type="submit">Submit</button>
        </div>
    </form>
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
            <a href="<%=request.getContextPath()%>/home"><span>Home</span></a>
            <a href="<%=request.getContextPath()%>/productServlet"><span>Shop</span></a>
            <a href="<%=request.getContextPath()%>/viewOrder"><span>Order</span></a>
            <a href=""><span>Category</span></a>
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

</body>
</html>
