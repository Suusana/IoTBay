<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bean.Customer" %>
<%@ page import="com.bean.Staff" %>
<html>
<head>
    <title>Customer Management</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/sideBar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/staffManagement.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>

<%
    Staff staff = (Staff) session.getAttribute("loggedInUser");
%>
<div class="sideBar">
    <h2>Admin Panel</h2>
    <h4>Current Staff: <%= staff.getStaffName()%></h4>
    <a href="<%= request.getContextPath() %>/ShowCustomerInfo" class="current">
        <i class="fa-solid fa-user fa-lg"></i>
        <span>Customer Management</span>
    </a>
    <a href="<%= request.getContextPath() %>/ShowStaffInfo">
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
    List<Customer> customerList = (List<Customer>) request.getAttribute("customerList");

//    showing the pagination
    int totalPages = (Integer) request.getAttribute("customerTotalPage");
    int totalRecords = (Integer) request.getAttribute("customerTotalRecords");
    int currentPage = (Integer) request.getAttribute("customerCurrentPage");
    String query = (String) request.getAttribute("query");
    if (query == null) {
        query = "";
    }
%>

<div class="main-content">
    <h1>Customer Management</h1>

<%--    <!-- Crate customer -->--%>
<%--    <h2>Create New Customer</h2>--%>
<%--    <form class="customer-form" action="<%=request.getContextPath()%>/AddCustomer" method="post">--%>

<%--        <input type="text" name="name" placeholder="Full Name" required>--%>
<%--        <input type="email" name="email" placeholder="Email" required>--%>

<%--        <select name="type" required>--%>
<%--            <option value="">Select Type</option>--%>
<%--            <option value="Company">Company</option>--%>
<%--            <option value="Individual">Individual</option>--%>
<%--        </select>--%>

<%--        <input type="text" name="address" placeholder="Address" required>--%>

<%--        <button type="submit">Create Customer</button>--%>
<%--    </form>--%>

    <!-- Search Customer -->
    <div class="top-bar">
        <%--        searh for a staff --%>
        <form action="<%= request.getContextPath() %>/ShowCustomerInfo" method="get" class="search-form">
            <input type="text" name="query" placeholder="Search by Name or Position" class="search-input"
                   value="<%= (request.getAttribute("query") != null) ? (String)request.getAttribute("query") : "" %>">
            <button type="submit" class="search-button">Search</button>
        </form>
        <form action="<%= request.getContextPath() %>/views/CreateCustomer.jsp" method="post">
            <button type="submit" class="create-button">+ Create Customer</button>
        </form>
    </div>

    <!-- List customers -->
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Address</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>

        <%
            if (customerList != null) {
                for (Customer customer : customerList) {
        %>
        <tr>
            <td><%= customer.getUserId() %></td>
            <td><%= customer.getFirstName() %> <%= customer.getLastName() %></td>
            <td><%= customer.getEmail() %></td>
            <td><%= customer.getPhone() %></td>
            <td><%= customer.getAddress() %>, <%= customer.getCity() %>, <%= customer.getState() %> <%= customer.getPostcode() %>, <%= customer.getCountry() %></td>
            <td>
                <form action="<%= request.getContextPath() %>/toggleCustomerStatus" method="post">
                    <input type="hidden" name="customerId" value="<%= customer.getUserId() %>">
                    <input type="hidden" name="currentPage" value="<%= currentPage %>">
                    <input type="hidden" name="query" value="<%= query %>">
                    <button type="submit" name="status" value="<%= customer.getStatus() %>"
                            class="<%= "Active".equals(customer.getStatus()) ? "active" : "inactive" %>">
                        <%= customer.getStatus() %>
                    </button>
                </form>
            </td>
            <td>
                <form action="<%=request.getContextPath()%>/showCustomer" method="post">
                    <input type="hidden" name="customerId" value="<%=customer.getUserId()%>">
                    <button type="submit" name="view" value="view">View</button>
                </form>
                <form action="<%=request.getContextPath()%>/showCustomer" method="post">
                    <input type="hidden" name="customerId" value="<%=customer.getUserId()%>">
                    <button type="submit" name="update" class="update" value="update">Update</button>
                </form>
                <form action="<%=request.getContextPath()%>/DeleteCustomer" method="get">
                    <input type="hidden" name="customerId" value="<%=customer.getUserId()%>">
                    <button type="submit" onclick="return confirm('Are you sure you want to delete this staff?');"
                            class="delete" >Delete</button>
                </form>
            </td>
        </tr>

        <%

            }
        } else {

        %>

        <tr>
            <td colspan="7">No customers found.</td>
        </tr>

        <% } %>

    </table>

    <div class="pagination">
        <span>Total <%= totalRecords %> records & Total <%= totalPages%> Pages</span>

        <form action="<%= request.getContextPath() %>/ShowCustomerInfo" method="get">
            <input type="hidden" name="page" value="<%=currentPage - 1 %>">
            <input type="hidden" name="query" value="<%= query %>">
            <button type="submit" <%= (currentPage == 1) ? "disabled" : "" %> >Previous</button>
        </form>

        <form action="<%= request.getContextPath() %>/ShowCustomerInfo" method="get">
            <input type="hidden" name="page" value="<%= currentPage + 1 %>">
            <input type="hidden" name="query" value="<%= query %>">
            <button type="submit" <%= (currentPage >= totalPages) ? "disabled" : "" %> >Next</button>
        </form>
    </div>
</div>



</body>

</html>
