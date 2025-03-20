<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 19/03/2025
  Time: 2:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Login</title>
    <link rel="stylesheet" href="../assets/css/base.css">
<%--    post form-data to the welcome page--%>
</head>

<body>
    <h1>IoTBay</h1>
    <h2>Login</h2>

    <form action="welcome.jsp" method="post">
        <label for="email">Email Address</label>
        <input id="email" name="email" type="text" required />

        <label for="password">Password</label>
        <input id="password" name="password" type="password" required />

        <input id="login" type="submit" value="Login" style="cursor: pointer; padding: 10px;" />
    </form>
</body>

</html>
