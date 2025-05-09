package com.controller.OrderController;

import com.bean.Order;
import com.bean.Product;
import com.dao.DBManager;
import com.dao.OrderDao;
import com.dao.ProductDao;
import com.enums.OrderStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/orderAction")
public class OrderAction extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        HttpSession session = request.getSession();
        DBManager dbManager = (DBManager) session.getAttribute("db");
        Connection connection = dbManager.getConnection();
        OrderDao orderDao = new OrderDao(connection);

        try {
            Order order = orderDao.findOrderByOrderId(orderId);

            if (order == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Order not found.");
                return;
            }

            if (!"Saved".equals(order.getOrderStatus().name())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Only Saved orders can be modified.");
                return;
            }

            ProductDao productDao = new ProductDao(connection);

            switch (action) {
                case "update":
                    boolean hasError = false;
                    StringBuilder errorMsg = new StringBuilder();

                    for (Product product : order.getProducts()) {
                        int productId = product.getProductId();

                        // getProductFreshQuantity
                        Product freshProduct = productDao.findProductById(productId);
                        int availableStock = freshProduct.getQuantity();

                        String quantityParam = request.getParameter("quantity_" + productId);
                        if (quantityParam != null && !quantityParam.isEmpty()) {
                            int newQuantity = Integer.parseInt(quantityParam);

                            if (newQuantity > availableStock) {
                                hasError = true;
                                errorMsg.append("Quantity for product ID ")
                                        .append(productId)
                                        .append(" exceeds available stock (")
                                        .append(availableStock)
                                        .append(").\n");
                            } else {
                                orderDao.updateOrderQuantity(order.getOrderId(), newQuantity);
                            }
                        } else {
                            System.out.println("Missing quantity param for product " + productId);
                        }
                    }

                    if (hasError) {
                        session.setAttribute("error", errorMsg.toString());
                    }

                    response.sendRedirect("viewOrderDetails?orderId=" + order.getOrderId());
                    break;


                case "submit":
                    // update the order's status
                    orderDao.updateOrderStatus(orderId, OrderStatus.Confirmed);

                    // using productDao to update productQuantity
                    for (Product product : order.getProducts()) {
                        int productId = product.getProductId();
                        int orderQuantity = product.getQuantity();

                        Product dbProduct = productDao.findProductById(productId);
                        int currentStock = dbProduct.getQuantity();

                        int updatedStock = currentStock - orderQuantity;
                        if (updatedStock < 0) updatedStock = 0;

                        productDao.updateProductQuantity(productId, updatedStock);
                    }

                    response.sendRedirect("viewOrderDetails?orderId=" + orderId);
                    break;


                case "cancel":
                    orderDao.updateOrderStatus(orderId, OrderStatus.Cancelled);
                    response.sendRedirect("viewOrderDetails?orderId=" + orderId);
                    break;

                default:
                    response.sendRedirect("viewOrder");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("viewOrder");
        }
    }
}
