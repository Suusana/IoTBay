<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bean.Customer" %>
<html>
<%
    Customer user = (Customer) session.getAttribute("loggedInUser");
    boolean isGuest = "guest".equalsIgnoreCase(user.getType());
%>
<head>
    <title>Delete Account</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/base.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/deleteAccount.css">
</head>
<body>

<main>
    <div class="delete-box">
        <h1>Delete Account</h1>

        <h2><span style="color: darkred;">WARNING:</span> Once account is deleted it cannot be restored</h2>
        <h2>Are you sure you want to permanently delete your account?</h2>

        <div class="button-options">
            <button class="button no-button" onclick="window.location.href='<%=request.getContextPath()%>/ViewUserDetailsServlet'">No</button>

            <% if (!isGuest) { %>
            <form action="<%=request.getContextPath()%>/DeleteUserAccountServlet" method="post">
                <button class="button yes-button" type="submit">Yes</button>
            </form>
            <% } else { %>
            <p style="color: gray; font-style: italic; margin-top: 10px;">
                Guest users cannot delete their account manually.
            </p>
            <% } %>
        </div>
    </div>
</main>

</body>
</html>
