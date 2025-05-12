<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Logout</title>
    <link rel="stylesheet" href="../assets/css/base.css">
    <link rel="stylesheet" href="../assets/css/HeaderAndFooter.css">
    <link rel="stylesheet" href="../assets/css/logout.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>

<%
    String userType = (String) session.getAttribute("userType");
%>

<body>
    <main class="main-container">
        <div class="logo-container">
            <img src="../assets/img/Logo.png" alt="Logo" class="logo">
        </div>

        <div class="content-container">
            <div class="highlight-box">
                <h1 class="logout-title">Logout</h1>
            </div>

            <h2 class="logout-subtitle">Are you sure you want to log out?</h2>

            <div class="logout-options">
                <form action="<%=request.getContextPath()%>/LogoutServlet" method="post">
                    <button class="button" type="submit">Yes</button>
                </form>
                <%
                    if (userType.equalsIgnoreCase("customer")) {
                %>
                <button class="button" onclick="window.location.href='/home'">No</button>
                <%
                    } else if (userType.equalsIgnoreCase("staff")) {
                %>
                <button class="button" onclick="window.location.href='/ProductManagementServlet'">No</button>
                <%
                    }
                %>
            </div>
        </div>
    </main>

</body>
</html>


