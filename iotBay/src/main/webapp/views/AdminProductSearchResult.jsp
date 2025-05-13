<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 06/05/2025
  Time: 12:53 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="java.util.List" %>
<%@ page import="com.bean.Product" %>
<%@ page import="com.bean.Staff" %>

<html>
<%
    List<Product> searchAllProducts = (List<Product>) request.getAttribute("searchAllProducts");
    String message = (String) request.getAttribute("error");
%>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/sideBar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/ProductManagement.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<%
    Staff staff = (Staff) session.getAttribute("loggedInUser");
%>
<div class="sideBar">
    <h2>Admin Panel</h2>
    <h4>Current Staff: <%= staff.getStaffName()%>
    </h4>
    <a href="<%= request.getContextPath() %>/ShowCustomerInfo">
        <i class="fa-solid fa-user fa-lg"></i>
        <span>Customer Management</span>
    </a>
    <a href="<%= request.getContextPath() %>/ShowStaffInfo">
        <i class="fa-solid fa-user-tie fa-lg"></i>
        <span>Staff Management</span>
    </a>
    <a href="<%= request.getContextPath() %>/ProductManagementServlet" class="current">
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
<div class="main-content">
    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Description</th>
            <th>Image</th>
            <th>Operation</th>
        </tr>
        </thead>
        <tbody>

        <% if (searchAllProducts != null && !searchAllProducts.isEmpty()) {
            for (Product product : searchAllProducts) { %>
        <tr>
            <td><%= product.getProductId()%>
            </td>
            <td><%= product.getProductName()%>
            </td>
            <td><%= product.getPrice()%>
            </td>
            <td><%= product.getQuantity()%>
            </td>
            <td><%= product.getDescription()%>
            </td>
            <td><img src="<%= request.getContextPath() %>/assets/img/<%= product.getImage() %>" alt="Device">
            </td>
            <td class="operation">

                <form action="<%= request.getContextPath() %>/ProductDetailAdminServlet" method="get" >
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <button type="submit" name="view" value="view">View</button>
                </form>

                <form action="<%= request.getContextPath() %>/UpdateProductServlet" method="get" >
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <button type="submit" name="update" class="update">Update</button>
                </form>

                <form action="<%= request.getContextPath() %>/DeleteProduct" method="post"
                      onsubmit="return confirm('Are you sure you want to delete it permanently remove this product?')">
                    <input type="hidden" name="productId" value="<%= product.getProductId() %>">
                    <button class="delete" type="submit">Delete</button>
                </form>

            </td>
        </tr>
        <%
            }
        } else if (message != null) {
        %> <h3>Error: <%=message%>
        </h3>
        <%} else {%>
        <h3>No result</h3>
        <%}%>
        </tbody>
    </table>
</div>

</body>
</html>
