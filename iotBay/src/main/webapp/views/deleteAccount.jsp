
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
        <form action="<%=request.getContextPath()%>/DeleteUserAccountServlet" method="post">
            <button class="button yes-button" type="submit">Yes</button>
        </form>
    </div>
    </div>
</main>

</body>
</html>
