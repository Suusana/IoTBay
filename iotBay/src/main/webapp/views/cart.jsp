<%@ page import="com.bean.Cart" %>
<%@ page import="com.bean.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Cart cart = (Cart)session.getAttribute("cart");

    if (cart == null) {
        cart = new Cart();  // create new cart
        session.setAttribute("cart", cart);
    }

    String productName = request.getParameter("productName");
    double productPrice = Double.parseDouble(request.getParameter("productPrice"));

    if (productName != null && !productName.isEmpty()) {
        cart.addItem(productName, productPrice);  // add product to cart
    }

    // delete from cart
    String removeItem = request.getParameter("remove");
    if (removeItem != null) {
        cart.removeItem(removeItem, productPrice);  // remove the product
    }
%>

<html>
<head>
    <title>Your Cart</title>
    <link rel="stylesheet" href="../assets/css/base.css">
    <link rel="stylesheet" href="../assets/css/cart.css">
    <link rel="stylesheet" href="../assets/css/HeaderAndFooter.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<h2>Your Cart</h2>
<%
    if (cart.getItems().isEmpty()) {
%>
<p>Your cart is empty.</p>
<%
} else {
%>
<ul>
    <%
        for (String item : cart.getItems()) {
    %>
    <li>
        <%= item %>
        <a href="cart.jsp?remove=<%= item %>">Remove</a> <!-- delete from cart -->
    </li>
    <%
        }
    %>
</ul>
<p>Total Price: $<%= cart.getTotalPrice() %></p>
<a href="checkout.jsp">Proceed to Checkout</a>
<%
    }
%>

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
            <a href=""><span>Home</span></a>
            <a href=""><span>Shop</span></a>
            <a href=""><span>Order</span></a>
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
