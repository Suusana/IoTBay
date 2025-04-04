<%@page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Landing page</title>
    <link rel="stylesheet" href="assets/css/base.css">
    <link rel="stylesheet" href="assets/css/HeaderAndFooter.css">
    <link rel="stylesheet" href="assets/css/landing.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
    <main class="main-container">
        <div class="logo-container">
            <img src="assets/img/Logo.png" alt="Logo" class="logo">
        </div>
        
        <h1>Welcome to IoTBay</h1>
        <div>
            <a href="./views/login.jsp">
                <button class="button button-login">Login</button>
            </a>
            <a href="./views/register.jsp">
                <button class="button button-register">Register</button>
            </a>
        </div>
    </main>  

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
