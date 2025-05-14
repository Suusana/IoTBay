<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int orderId = (Integer) request.getAttribute("orderId");
    String base = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirm Action</title>
    <style>
        body {
            background-color: #FFF3E3;
            font-family: Arial, sans-serif;
            text-align: center;
            padding-top: 100px;
        }
        .box {
            background-color: white;
            border-radius: 10px;
            padding: 40px;
            display: inline-block;
            box-shadow: 0 8px 20px rgba(0,0,0,0.1);
        }
        h2 {
            color: #B88E2F;
        }
        .btn {
            padding: 10px 20px;
            margin: 15px;
            border: 1px solid #B88E2F;
            background: white;
            color: #B88E2F;
            font-weight: bold;
            border-radius: 8px;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #B88E2F;
            color: white;
        }
    </style>
</head>
<body>

<div class="box">
    <h2>Do you want to proceed with this order?</h2>

    <form action="<%= base %>/CheckPendingPayment" method="get">
        <input type="hidden" name="orderId" value="<%= orderId %>"/>
        <input type="hidden" name="step" value="proceed"/>
        <button class="btn">Yes, proceed</button>
    </form>


    <form action="<%= base %>/DeletePayment" method="post" style="display:inline;">
        <input type="hidden" name="orderId" value="<%= orderId %>"/>
        <button class="btn">No, I want to delete it</button>
    </form>
</div>

</body>
</html>
