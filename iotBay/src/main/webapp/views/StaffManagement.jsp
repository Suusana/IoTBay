<%@ page import="java.util.List" %>
<%@ page import="com.bean.Staff" %><%--
  Created by IntelliJ IDEA.
  User: Susana
  Date: 4/25/2025
  Time: 13:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Staff Management</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/sideBar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/staffManagement.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>

<body>
<%
    Staff _staff = (Staff) session.getAttribute("loggedInUser");
%>
<div class="sideBar">
    <h2>Admin Panel</h2>
    <h4>Current Staff: <%= _staff.getStaffName()%></h4>
    <a href="<%= request.getContextPath() %>/ShowCustomerInfo">
        <i class="fa-solid fa-user fa-lg"></i>
        <span>Customer Management</span>
    </a>
    <a href="<%= request.getContextPath() %>/ShowStaffInfo" class="current">
        <i class="fa-solid fa-user-tie fa-lg"></i>
        <span>Staff Management</span>
    </a>
    <a href="<%= request.getContextPath() %>/ProductManagementServlet">
        <i class="fa-solid fa-user-tie fa-lg"></i>
        <span>Product Management</span>
    </a>
    <a href="<%=request.getContextPath()%>/ViewUserDetailsServlet">
        <i class="fa-solid fa-user-tie fa-lg"></i>
        <span>My Details Management</span>
    </a>
    <a href="<%=request.getContextPath()%>/views/logout.jsp">
        <i class="fa-solid fa-right-from-bracket fa-lg"></i>
        <span>Logout</span>
    </a>
</div>

<%
    List<Staff> staffList = (List<Staff>) request.getAttribute("staffList");

//    showing the pagination
    int totalPages = (Integer) request.getAttribute("staffTotalPage");
    int totalRecords = (Integer) request.getAttribute("staffTotalRecords");
    int currentPage = (Integer) request.getAttribute("staffCurrentPage");
    String query = (String) request.getAttribute("query");
    if (query == null) {
        query = "";
    }
%>

<div class="main-content">
    <h1>Staff Management</h1>
    <div class="top-bar">
        <%--        searh for a staff --%>
        <form action="<%= request.getContextPath() %>/ShowStaffInfo" method="get" class="search-form">
            <input type="text" name="query" placeholder="Search by Name or Position" class="search-input"
                   value="<%= (request.getAttribute("query") != null) ? (String)request.getAttribute("query") : "" %>">
            <button type="submit" class="search-button">Search</button>
        </form>
        <form action="<%= request.getContextPath() %>/views/CreateStaff.jsp" method="post">
            <button type="submit" class="create-button">+ Create Staff</button>
        </form>
    </div>

    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Staff Name</th>
            <th>Email</th>
            <th>Position</th>
            <th>Address</th>
            <th>Status</th>
            <th>Operations</th>
        </tr>
        </thead>
        <tbody>
        <%--        Iterate through the data--%>
        <%
            if (staffList != null) {
                for (Staff staff : staffList) {
        %>
        <tr>
            <td><%= staff.getStaffId()%>
            </td>
            <td><%= staff.getStaffName()%>
            </td>
            <td><%= staff.getEmail()%>
            </td>
            <td><%= staff.getPosition()%>
            </td>
            <td><%= staff.getAddress()%>
            </td>

            <td>
                <form action="<%= request.getContextPath() %>/toggleStaffStatus" method="post">
                    <input type="hidden" name="staffId" value="<%= staff.getStaffId() %>">
                    <input type="hidden" name="currentPage" value="<%= currentPage %>">
                    <input type="hidden" name="query" value="<%= query %>">
                    <button type="submit" name="status" value="<%= staff.getStatus() %>"
                            class="<%= "Active".equals(staff.getStatus()) ? "active" : "inactive" %>">
                        <%= staff.getStatus() %>
                    </button>
                </form>
            </td>
            <td>
                <form action="<%=request.getContextPath()%>/showStaff" method="post">
                    <input type="hidden" name="staffId" value="<%=staff.getStaffId()%>">
                    <button type="submit" name="view" value="view">View</button>
                </form>
                <form action="<%=request.getContextPath()%>/showStaff" method="post">
                    <input type="hidden" name="staffId" value="<%=staff.getStaffId()%>">
                    <button type="submit" name="update" class="update" value="update">Update</button>
                </form>
                <form action="<%=request.getContextPath()%>/DeleteStaff" method="get">
                    <input type="hidden" name="staffId" value="<%=staff.getStaffId()%>">
                    <button type="submit" onclick="return confirm('Are you sure you want to delete this staff?');"
                            class="delete" >Delete</button>
                </form>
            </td>
        </tr>

        <%
                }
            }
        %>
        </tbody>
    </table>

    <div class="pagination">
        <span>Total <%= totalRecords %> records & Total <%= totalPages%> Pages</span>

        <form action="<%= request.getContextPath() %>/ShowStaffInfo" method="get">
            <input type="hidden" name="page" value="<%=currentPage - 1 %>">
            <input type="hidden" name="query" value="<%= query %>">
            <button type="submit" <%= (currentPage == 1) ? "disabled" : "" %> >Previous</button>
        </form>

        <form action="<%= request.getContextPath() %>/ShowStaffInfo" method="get">
            <input type="hidden" name="page" value="<%= currentPage + 1 %>">
            <input type="hidden" name="query" value="<%= query %>">
            <button type="submit" <%= (currentPage >= totalPages) ? "disabled" : "" %> >Next</button>
        </form>
    </div>
</div>
</body>
</html>
