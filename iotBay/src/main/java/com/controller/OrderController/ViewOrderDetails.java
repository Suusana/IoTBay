package com.controller.OrderController;

import com.bean.Order;
import com.dao.DBManager;
import com.dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/viewOrderDetails")
public class ViewOrderDetails extends HttpServlet {

    private OrderDao orderDao;

    @Override
    public void init() {
        Connection connection = (Connection) getServletContext().getAttribute("connection");
        orderDao = new OrderDao(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderIdParam = request.getParameter("orderId");

        if (orderIdParam != null) {
            try {
                // 从 session 中获取 dbManager 和连接
                HttpSession session = request.getSession();
                DBManager dbManager = (DBManager) session.getAttribute("db");
                Connection connection = dbManager.getConnection();

                OrderDao orderDao = new OrderDao(connection);  // ✅ 传入有效连接

                int orderId = Integer.parseInt(orderIdParam);
                Order order = orderDao.findOrderByOrderId(orderId);

                if (order != null) {
                    request.setAttribute("order", order);
                    request.getRequestDispatcher("/views/orderDetails.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID.");
            }
        } else {
            response.sendRedirect("viewOrder");
        }
    }

}
