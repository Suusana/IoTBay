<%@ page import="com.bean.UserAccessLog" %>
<%@ page import="java.util.LinkedList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    LinkedList<UserAccessLog> accessLogs = (LinkedList<UserAccessLog>) request.getAttribute("accessLogs");

    String startDate = "";
    String endDate = "";
    if (request.getAttribute("startDate") != null && request.getAttribute("endDate") != null) {
        startDate = (String) request.getAttribute("startDate");
        endDate = (String) request.getAttribute("endDate");
    }
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

        <h3>Search by date</h3>
        <form class="date-search" method="get" action="<%=request.getContextPath()%>/SearchAccessLogsServlet">
            <label for="startDate">From: </label>
            <input type="date" id="startDate" name="startDate" value="<%=startDate%>" max="<%=java.time.LocalDate.now()%>">

            <label for="endDate">To: </label>
            <input type="date" id="endDate" name="endDate" value="<%=endDate%>" max="<%=java.time.LocalDate.now()%>">

            <button type="submit" style="cursor: pointer;">Search</button>
        </form>
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
        <p style="color: red;"><%=errorMessage%></p>
        <%
            }
        %>
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
        <a class="visibleLink" href="<%=request.getContextPath()%>/ViewUserDetailsServlet">Return to Account Details</a>
    </div>
</main>

</body>
</html>
