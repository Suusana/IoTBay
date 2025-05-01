package com.controller.OrderController;

import com.bean.Order;
import com.dao.DBConnector;
import com.dao.OrderDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/viewOrder")
public class ViewOrder extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("🚀 进入 ViewOrder Servlet");

        List<Order> orderList = new ArrayList<>();
        DBConnector dbConnector = new DBConnector();

        try {
            Connection connection = dbConnector.getConnection();
            System.out.println("🛠 数据库连接对象：" + connection);

            if (connection == null) {
                throw new Exception("❌ 数据库连接是 null");
            }

            OrderDao orderDao = new OrderDao(connection);
            System.out.println("✅ 调用 orderDao.findAllOrders()");
            orderList = orderDao.findAllOrders();
            System.out.println("✅ 查询订单数：" + orderList.size());

        } catch (Exception e) {
            System.out.println("❌ 发生异常：" + e.getMessage());
            e.printStackTrace();
            request.setAttribute("message", "Error loading orders.");
        } finally {
            dbConnector.closeConnection();
            System.out.println("🔚 数据库连接已关闭");
        }

        request.setAttribute("orderList", orderList);
        request.getRequestDispatcher("/views/orderList.jsp").forward(request, response);
    }

}
