package com.controller.OrderController;

import com.bean.Order;
import com.bean.Product;
import com.dao.DBManager;
import com.dao.OrderDao;
import com.dao.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/viewOrderDetails")
public class ViewOrderDetails extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        OrderDao orderDao = db.getOrderDao();
        ProductDao productDao = db.getProductDao();

        String orderIdParam = request.getParameter("orderId");

        try {
            int orderId = Integer.parseInt(orderIdParam);
            Order order = orderDao.findOrderByOrderId(orderId);
            int product_id = orderDao.getProductIdByOrderId(orderId);
            Product product = productDao.getProductById(product_id);
            List<Product> products = new ArrayList<>();
            products.add(product);
            order.setProducts(products);

            request.setAttribute("order", order);
            request.getRequestDispatcher("/views/orderDetails.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Fail to get order");
        }
    }

}
