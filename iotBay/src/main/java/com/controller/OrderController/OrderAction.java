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

            switch (action) {
                case "update":
                    boolean hasError = false;
                    StringBuilder errorMsg = new StringBuilder();

                    ProductDao productDao = new ProductDao(connection); // 🔄 确保使用最新库存

                    for (Product product : order.getProducts()) {
                        int productId = product.getProductId();

                        // ⛳ 正确方式：重新查数据库获取库存
                        Product freshProduct = productDao.findProductById(productId);
                        int availableStock = freshProduct.getQuantity();  // ✅ 正确库存值

                        String quantityParam = request.getParameter("quantity_" + productId);
                        if (quantityParam != null && !quantityParam.isEmpty()) {
                            int newQuantity = Integer.parseInt(quantityParam);

                            if (newQuantity > availableStock) {
                                hasError = true;
                                errorMsg.append("❌ Quantity for product ID ")
                                        .append(productId)
                                        .append(" exceeds available stock (")
                                        .append(availableStock)
                                        .append(").\n");
                            } else {
                                orderDao.updateOrderQuantity(order.getOrderId(), newQuantity); // ✅ 数据库更新
                                System.out.println("✔ Updated quantity for product " + productId + " to: " + newQuantity);
                            }
                        } else {
                            System.out.println("⚠ Missing quantity param for product " + productId);
                        }
                    }

                    if (hasError) {
                        session.setAttribute("error", errorMsg.toString());
                    }

                    response.sendRedirect("viewOrderDetails?orderId=" + order.getOrderId());
                    break;


                case "submit":
                    orderDao.updateOrderStatus(orderId, OrderStatus.Confirmed);
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
