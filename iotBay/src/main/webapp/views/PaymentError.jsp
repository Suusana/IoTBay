<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Payment Failed</title>
</head>
<body>
<h2>Oops! Payment Failed</h2>
<p>Something went wrong during payment. Please try again or contact support.</p>
<a href="<%= request.getContextPath() %>/home">Go Back Home</a>
</body>
</html>