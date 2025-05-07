package com.controller.OrderController;

import com.bean.Customer;
import com.bean.Order;
import com.bean.Product;
import com.dao.DBManager;
import com.dao.OrderDao;
import com.enums.OrderStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/createOrder")
public class CreateOrder extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int userId;

        // get customer ID
        Customer customer = (Customer) session.getAttribute("loggedInUser");
        if (customer == null) {
            if (session.getAttribute("guestId") == null) {
                int guestId = -Math.abs(session.getId().hashCode());
                session.setAttribute("guestId", guestId);
            }
            userId = (int) session.getAttribute("guestId");
        } else {
            userId = (int) customer.getUserId();
        }

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String action = request.getParameter("action");

            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than 0.");
            }

            // set order's status
            OrderStatus status = "Submit".equalsIgnoreCase(action)
                    ? OrderStatus.Confirmed
                    : OrderStatus.Saved;

            Order order = new Order();
            order.setOrderStatus(status);
            order.setCreateDate(new Timestamp(System.currentTimeMillis()));

            Product product = new Product();
            product.setProductId(productId);
            product.setQuantity(quantity);

            List<Product> productList = new ArrayList<>();
            productList.add(product);
            order.setProducts(productList);

            DBManager db = (DBManager) session.getAttribute("db");
            OrderDao orderDao = new OrderDao(db.getConnection());
            orderDao.saveOrder(order, userId);

            // jump to viewOrder when success
            response.sendRedirect(request.getContextPath() + "/viewOrder");

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "⚠ Failed to create order: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/productServlet");
        }
    }
}
