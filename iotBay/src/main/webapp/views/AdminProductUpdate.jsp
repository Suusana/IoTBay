<%--
  Created by IntelliJ IDEA.
  User: yunseo
  Date: 08/05/2025
  Time: 2:57 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="com.bean.Product" %>
<%@ page import="com.bean.Category" %>
<%@ page import="com.bean.Staff" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product Details</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/sideBar.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/StaffDetail.css">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/assets/css/ProductUpdate.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>
<%
    Staff staff = (Staff) session.getAttribute("loggedInUser");
%>
<div class="sideBar">
    <h2>Admin Panel</h2>
    <h4>Current Staff: <%= staff.getStaffName()%></h4>
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
    <h1>Product Detail</h1>
    <%
        Product product = (Product) request.getAttribute("product");
    %>
    <div class="card">
        <%--        profile icon--%>
        <img src="<%= request.getContextPath() %>/assets/img/<%= product.getImage() %>" alt="Device">

        <div class="info-section">
            <form action="<%=request.getContextPath()%>/UpdateProductServlet" method="post" enctype="multipart/form-data">
                <input type="hidden" name="productId" value="<%=product.getProductId()%>"/>
                <label>Product Name : <input type="text" name="productName" value="<%=product.getProductName()%>"/></label>
                <label>Price : <input type="text" name="price" value="<%=product.getPrice()%>"/></label>
                <label>Quantity : <input type="number" name="quantity" value="<%=product.getQuantity()%>"/></label>
                <label>Description : <textarea name="description"> <%=product.getDescription()%></textarea></label>
                <label>Image : <input type="file" name="image" value="<%=product.getImage()%>"/></label>
                <%
                    Category category = new Category();
                    category = product.getCategory();
                    int categoryID = category.getCategoryId();
                %>
                <label>Category ID: <input type="number" name="categoryId" value="<%=categoryID%>"/></label>
                <div>Category: <%=product.getCategory().getCategory()%></div>
                <button class="update" type="submit">Update</button>
            </form>
        </div>`
    </div>

    <a href="<%= request.getContextPath() %>/ProductManagementServlet">
        <button class="back-button">Back to Product Management</button>
    </a>
</div>
</body>
</html>
