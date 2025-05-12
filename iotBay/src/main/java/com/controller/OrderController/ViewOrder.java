package com.controller.OrderController;

import com.bean.Customer;
import com.bean.Order;
import com.bean.Product;
import com.dao.DBManager;
import com.dao.OrderDao;
import com.dao.ProductDao;
import com.dao.StaffDao;
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
        DBManager db = (DBManager) session.getAttribute("db");
        OrderDao orderDao = db.getOrderDao();
        ProductDao productDao = db.getProductDao();

        Customer customer = (Customer) session.getAttribute("loggedInUser");

        try {
            List<Order> orders = new ArrayList<>();
            String orderIdParam = request.getParameter("orderId");
            String orderDateParam = request.getParameter("orderDate");

            if (orderIdParam != null && !orderIdParam.isEmpty()) {
                try {
                    int orderId = Integer.parseInt(orderIdParam);
                    Order order = orderDao.searchOrderByOrderId(orderId, customer.getUserId());
                    if (order != null) {
                        int product_id = orderDao.getProductIdByOrderId(orderId);
                        Product product = productDao.getProductById(product_id);
                        List<Product> products = new ArrayList<>();
                        products.add(product);
                        order.setProducts(products);
                        orders.add(order);
                    } else {
                        request.setAttribute("message", "Order not found.");
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("message", "Invalid order ID format.");
                }
            } else if (orderDateParam != null && !orderDateParam.isEmpty()) {
                orders = orderDao.searchOrderByDate(orderDateParam, customer.getUserId());
                if (orders.isEmpty()) {
                    request.setAttribute("message", "No orders found for that date.");
                }
            } else {
                orders = orderDao.getOrdersByUserId(customer.getUserId());
            }
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/views/orderList.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", "System error while retrieving orders.");
            request.getRequestDispatcher("/views/orderList.jsp").forward(request, response);
        }
    }
}
