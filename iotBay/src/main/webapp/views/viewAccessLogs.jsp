<%@ page import="com.bean.UserAccessLog" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="com.bean.Customer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    LinkedList<UserAccessLog> accessLogs = (LinkedList<UserAccessLog>) request.getAttribute("accessLogs");
%>
<html>
<head>
    <title>Account History</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/base.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/deleteAccount.css">
</head>
<body>

<main>
    <div class="logs-box">
        <h1>Account Access Logs</h1>

        <p>Search by date</p>
        <table>
            <thead>
            <tr>
                <th>Login Time</th>
                <th>Logout Time</th>
            </tr>
            </thead>
            <tbody>
            <%
                if (accessLogs != null && !accessLogs.isEmpty()) {
                    for (UserAccessLog accessLog : accessLogs) {
            %>
            <tr>
                <td><%= accessLog.getLoginTimeFormatted() %></td>
                <td><%= accessLog.getLogoutTimeFormatted() != null ? accessLog.getLogoutTimeFormatted() : "Unknown Logout Time" %></td>
            </tr>
            </tbody>
            <%
                }
            %>
            <tfoot>
            <tr>
                <td colspan="2" style="text-align: center;">Total Records: <%=accessLogs.size()%></td>
            </tr>
            </tfoot>
            <%
            } else {
            %>
            <tr>
                <td colspan="2">No associated access logs found</td>
            </tr>
            <%
                }
            %>
        </table>
        <a href="<%=request.getContextPath()%>/ViewUserDetailsServlet">Return to Account Details</a>
    </div>
</main>

</body>
</html>
