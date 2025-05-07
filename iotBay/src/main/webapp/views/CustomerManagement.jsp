<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bean.Customer" %>
<html>
<head>
    <title>Customer Management</title>
    <link rel="stylesheet" href="../assets/css/base.css">
    <link rel="stylesheet" href="../assets/css/sideBar.css">
    <link rel="stylesheet" href="../assets/css/customerManagement.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>

<div class="sideBar">
    <h2>Admin Panel</h2>
    <a href="./AdminDashboard.jsp">
        <i class="fa-solid fa-house fa-lg"></i>
        <span>Dashboard</span>
    </a>
    <a href="./CustomerManagement.jsp" class="current">
        <i class="fa-solid fa-user fa-lg"></i>
        <span>Customer Management</span>
    </a>
    <a href="./StaffManagement.jsp">
        <i class="fa-solid fa-user-tie fa-lg"></i>
        <span>Staff Management</span>
    </a>
    <a href="#">
        <i class="fa-solid fa-right-from-bracket fa-lg"></i>
        <span>Logout</span>
    </a>
</div>

<div class="main-content">
    <h1>Customer Management</h1>

    <!-- Crate customer -->
    <h2>Create New Customer</h2>
    <form class="customer-form" action="<%=request.getContextPath()%>/AddCustomer" method="post">

        <input type="text" name="name" placeholder="Full Name" required>
        <input type="email" name="email" placeholder="Email" required>

        <select name="type" required>

            <option value="">Select Type</option>
            <option value="Company">Company</option>
            <option value="Individual">Individual</option>

        </select>

        <input type="text" name="address" placeholder="Address" required>

        <button type="submit">Create Customer</button>
    </form>

    <!-- Search Customer -->
    <h2>Search Customer</h2>
    <form action="SearchCustomer" method="get">
        <input type="text" name="keyword" placeholder="Enter name or status" />
        <button type="submit">Search</button>
    </form>

    <!-- List customers -->
    <h2>Customer List</h2>

    <table border="1">

        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Address</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>

        <%
            List<Customer> customerList = (List<Customer>) request.getAttribute("customerList");

            if (customerList != null) {
                for (Customer customer : customerList) {
        %>
        <tr>
            <td><%= customer.getUserId() %></td>
            <td><%= customer.getFirstName() %> <%= customer.getLastName() %></td>
            <td><%= customer.getEmail() %></td>
            <td><%= customer.getPhone() %></td>
            <td><%= customer.getAddress() %>, <%= customer.getCity() %>, <%= customer.getState() %> <%= customer.getPostcode() %>, <%= customer.getCountry() %></td>
            <td><%= customer.getStatus() %></td>
            <td>
                <a href="UpdateCustomer?id=<%= customer.getUserId() %>">Edit</a> |
                <a href="DeleteCustomer?id=<%= customer.getUserId() %>" onclick="return confirm('Delete this customer?')">Delete</a> |
                <a href="ToggleCustomerStatus?id=<%= customer.getUserId() %>">Toggle Status</a>
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
</div>

</body>

</html>
