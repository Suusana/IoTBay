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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/orderAction")
public class OrderAction extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        OrderDao orderDao = db.getOrderDao();
        ProductDao productDao = db.getProductDao();

        String action = request.getParameter("action");
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try {
            Order order = orderDao.findOrderByOrderId(orderId);
            int prodId = orderDao.getProductIdByOrderId(orderId);
            Product prod = productDao.getProductById(prodId);
            List<Product> list = new ArrayList<>();
            list.add(prod);
            order.setProducts(list);

            switch (action) {
                case "update":
                    boolean hasError = false;
                    StringBuilder errorMsg = new StringBuilder();
                    for (Product product : order.getProducts()) {
                        int productId = product.getProductId();

                        Product freshProduct = productDao.getProductById(productId);
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
                        }
                    }

                    if (hasError) {
                        session.setAttribute("error", errorMsg.toString());
                    }

                    response.sendRedirect("viewOrderDetails?orderId=" + order.getOrderId());
                    break;

                case "submit":
                    boolean hasError2 = false;
                    StringBuilder errorMsg2 = new StringBuilder();

                    for (Product product : order.getProducts()) {
                        int productId = product.getProductId();

                        Product freshProduct = productDao.getProductById(productId);
                        int availableStock = freshProduct.getQuantity();

                        String quantityParam = request.getParameter("quantity_" + productId);
                        if (quantityParam != null && !quantityParam.isEmpty()) {
                            int newQuantity = Integer.parseInt(quantityParam);

                            if (newQuantity > availableStock) {
                                hasError2 = true;
                                errorMsg2.append("Quantity for product ID ")
                                        .append(productId)
                                        .append(" exceeds available stock (")
                                        .append(availableStock)
                                        .append(").\n");
                            } else {
                                product.setQuantity(newQuantity);
                            }
                        }
                    }

                    if (hasError2) {
                        session.setAttribute("error", errorMsg2.toString());
                        response.sendRedirect("viewOrderDetails?orderId=" + order.getOrderId());
                        break;
                    }

                    orderDao.updateOrderStatus(order.getOrderId(), OrderStatus.Confirmed);

                    // update stock
                    for (Product product : order.getProducts()) {
                        int productId = product.getProductId();
                        int finalQuantity = product.getQuantity();
                        productDao.updateProductQuantity(productId, finalQuantity);
                    }

                    // go to payment page
                    session.setAttribute("order", order);
                    session.setAttribute("product", order.getProducts().get(0));
                    response.sendRedirect(request.getContextPath() + "/views/ConfirmPayment.jsp");
                    break;

                case "cancel":
                    orderDao.updateOrderStatus(orderId, OrderStatus.Cancelled);
                    response.sendRedirect("viewOrderDetails?orderId=" + orderId);
                    break;

                default:
                    response.sendRedirect("/viewOrder");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/viewOrder");
        }
    }
}
