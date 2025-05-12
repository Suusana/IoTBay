package com.controller.OrderController;

import com.bean.Order;
import com.bean.Product;
import com.dao.DBManager;
import com.dao.OrderDao;
import com.dao.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/manageOrder")
public class ManageOrder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        OrderDao orderDao = db.getOrderDao();
        ProductDao productDao = db.getProductDao();

        String orderIdParam = request.getParameter("orderId");

        try {
            int orderId = Integer.parseInt(orderIdParam);

            Order order = orderDao.findOrderByOrderId(orderId);
            int productId = orderDao.getProductIdByOrderId(orderId);
            Product product = productDao.getProductById(productId);
            List<Product> productList = new ArrayList<>();
            productList.add(product);
            order.setProducts(productList);

            request.setAttribute("order", order);
            request.getRequestDispatcher("/views/manageOrder.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Order ID");
        }
    }
}
