
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Account</title>
</head>
<body>

<h1>Delete Account</h1>

<h2><span style="color: darkred;">WARNING:</span> Once account is deleted it cannot be restored</h2>
<h2>Are you sure you want to permanently delete your account?</h2>

<div class="logout-options">
    <form action="<%=request.getContextPath()%>/DeleteUserAccountServlet" method="post">
        <button class="button" type="submit">Yes</button>
    </form>
    <button class="button" onclick="window.location.href='<%=request.getContextPath()%>/ViewUserDetailsServlet'">No</button>
</div>

</body>
</html>
