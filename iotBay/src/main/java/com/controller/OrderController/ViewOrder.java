package com.controller.OrderController;

import com.bean.Order;
import com.dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/viewOrder")
public class ViewOrder extends HttpServlet {
    private OrderDao orderDao;

    @Override
    public void init() {
        Connection connection = (Connection) getServletContext().getAttribute("dbConn");
        orderDao = new OrderDao(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 获取 session 中的用户 ID，如果没有就设为 guest（-1）
        HttpSession session = request.getSession();
        Integer customerId = (Integer) session.getAttribute("customerId");
        if (customerId == null) {
            customerId = -1; // guest
        }

        // 获取查询参数
        String orderIdPara = request.getParameter("orderId");
        String orderDatePara = request.getParameter("orderDate");

        List<Order> orderList = new ArrayList<>();

        try {
            // 按订单编号查询
            if (orderIdPara != null && !orderIdPara.isEmpty()) {
                int orderId = Integer.parseInt(orderIdPara);
                Order order = orderDao.findOrderByOrderId(orderId, customerId);
                if (order != null) {
                    orderList.add(order);
                }
            }
            // 按订单日期查询
            else if (orderDatePara != null && !orderDatePara.isEmpty()) {
                java.util.Date parseDate = new SimpleDateFormat("yyyy-MM-dd").parse(orderDatePara);
                java.sql.Date sqlDate = new java.sql.Date(parseDate.getTime());
                orderList = orderDao.findOrderByDate(sqlDate, customerId);
            }
            // 查询所有订单
            else {
                orderList = orderDao.findOrderByCustomerId(customerId);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            request.setAttribute("message", "Error retrieving orders.");
        }

        // 设置属性并跳转到 JSP 页面显示结果
        request.setAttribute("orderList", orderList);
        request.getRequestDispatcher("/views/orderList.jsp").forward(request, response);

    }
}
