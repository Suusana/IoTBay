package com.controller.OrderController;

import com.bean.Order;
import com.dao.DBManager;
import com.dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/manageOrder")
public class ManageOrder extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderIdParam = request.getParameter("orderId");

        try {
            int orderId = Integer.parseInt(orderIdParam);

            // ✅ 从 session 获取 DBManager 和连接
            HttpSession session = request.getSession();
            DBManager dbManager = (DBManager) session.getAttribute("db");
            Connection connection = dbManager.getConnection();
            OrderDao orderDao = new OrderDao(connection);

            Order order = orderDao.findOrderByOrderId(orderId);

            if (order != null) {
                request.setAttribute("order", order);
                request.getRequestDispatcher("/views/manageOrder.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Order ID");
        }
    }
}
