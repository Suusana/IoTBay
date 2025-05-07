<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 19/03/2025
  Time: 2:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Customer" %>
<%@ page import="com.enums.Status" %>
<%@ page import="com.bean.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bean.Category" %>
<%@ page import="com.util.Utils" %>
<html>
<%
    Customer customer = new Customer();
    if (session.getAttribute("loggedInUser") != null){
        customer = (Customer)session.getAttribute("loggedInUser");
    } else {
        customer.setUsername(Status.GUEST.getStatus());
    }
%>
<head>
    <title>Main</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/base.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/HeaderAndFooter.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>

<body>
<!-- header -->
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
        <a href="<%=request.getContextPath()%>/views/logout.jsp">
            <i class="fa-solid fa-right-from-bracket fa-2x"></i>
            <span>Log Out</span>
        </a>

    </menu>
</div>
<!-- main body -->
<%
    Product banner = (Product) request.getAttribute("banner");
%>
<div class="mainBody">
    <div class="banner">
        <img src="<%=request.getContextPath()%>/assets/img/<%=banner.getImage()%>" alt="Banner Img">
        <div class="intro">
            <h5>New Arrival</h5>
            <p><%=banner.getProductName()%></p>
            <span><%=banner.getDescription()%></span>
            <br><button class="style1">Buy Now</button>
        </div>
    </div>
    <h5 class="title">Our Products</h5>
        <div class="Products">
    <%
        List<Product> productList = (List<Product>) request.getAttribute("productList");
        for (Product product:productList) {
    %>
                <a href="<%= request.getContextPath()%>/ProductDetailServlet?id=<%=product.getProductId()%>" class="card">
                    <img src="<%=request.getContextPath()%>/assets/img/<%=product.getImage()%>" alt="Device">
                    <h5><%=product.getProductName()%></h5>
                    <p><%=product.getDescription()%></p>
                    <span>$<%=product.getPrice()%></span>
                </a>
    <%
        }
    %>
        </div>
    <a href="<%=request.getContextPath()%>/productServlet">
        <button class="style1">Show More</button>
    </a>

    <div class="orderIntro">
        <div>
            <h5>Want to manage your orders?</h5>
            <p>Manage all of your orders and track your purchases easily.</p>
            <a href="">
                <button>Go to Order</button>
            </a>
        </div>
        <img src="<%=request.getContextPath()%>/assets/img/orders-post.jpg">
    </div>

    <h5 class="title">Categories</h5>
    <div class="categories">
    <%
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        for (Category category :categories) {
    %>
        <a href="" class="category">
            <img src="<%=request.getContextPath()%>/assets/img/category<%=category.getCategoryId()%>.png" alt="Category">
            <br><span><%=category.getCategory()%></span>
        </a>
    <%
        }
    %>

</div>
    <a href=""><button class="style1">Show More</button></a>
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
