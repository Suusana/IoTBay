<%@ page import="com.bean.Payment" %>
<%@ page import="java.util.List" %>

<h2>Payment List</h2>

<% List<Payment> payments = (List<Payment>) request.getAttribute("payments"); %>
<% if (payments != null && !payments.isEmpty()) { %>
<table border="1">
    <tr>
        <th>Payment ID</th>
        <th>Card Holder</th>
        <th>Amount</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    <% for (Payment p : payments) { %>
    <tr>
        <td><%= p.getPaymentId() %></td>
        <td><%= p.getCardHolder() %></td>
        <td><%= p.getAmount() %></td>
        <td><%= p.getStatus() %></td>
        <td>
            <a href="<%= request.getContextPath() %>/EditPayment?paymentId=<%= p.getPaymentId() %>">Edit</a>
            <form action="<%= request.getContextPath() %>/DeletePayment" method="post" style="display:inline;">
                <input type="hidden" name="paymentId" value="<%= p.getPaymentId() %>">
                <button type="submit">Delete</button>
            </form>
        </td>
    </tr>
    <% } %>
</table>
<% } else { %>
<p>No payments found.</p>
<% } %>