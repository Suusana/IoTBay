package com.controller.OrderController;

import com.bean.Customer;
import com.bean.Order;
import com.dao.DBManager;
import com.dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/viewOrder")
public class ViewOrder extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // get CustomerID
        Customer customer = (Customer) session.getAttribute("loggedInUser");
        int userId;
        boolean isGuest = false;

        if (customer == null) {
            isGuest = true;
            if (session.getAttribute("guestId") == null) {
                int guestId = -Math.abs(session.getId().hashCode());
                session.setAttribute("guestId", guestId);
            }
            userId = (int) session.getAttribute("guestId");
        } else {
            userId = (int) customer.getUserId();
        }

        request.setAttribute("isGuest", isGuest); // provide guest information to JSP to display mentioned Message

        try {
            DBManager db = (DBManager) session.getAttribute("db");
            OrderDao orderDao = new OrderDao(db.getConnection());

            List<Order> orders = new ArrayList<>();
            String orderIdParam = request.getParameter("orderId");
            String orderDateParam = request.getParameter("orderDate");

            // Order by order ID first, then date
            if (orderIdParam != null && !orderIdParam.isEmpty()) {
                try {
                    int orderId = Integer.parseInt(orderIdParam);
                    Order order = orderDao.searchOrderByOrderId(orderId, userId);
                    if (order != null) {
                        orders.add(order);
                    } else {
                        request.setAttribute("message", "No order found with ID " + orderId);
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("message", "Invalid order ID format.");
                }
            } else if (orderDateParam != null && !orderDateParam.isEmpty()) {
                orders = orderDao.searchOrderByDate(orderDateParam, userId);
                if (orders.isEmpty()) {
                    request.setAttribute("message", "No orders found for that date.");
                }
            } else {
                orders = orderDao.findOrderByCustomerId(userId);
            }

            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/views/orderList.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("message", "System error while retrieving orders.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
