package com.controller.OrderController;

import com.bean.Order;
import com.bean.Product;
import com.dao.DBManager;
import com.dao.OrderDao;
import com.enums.OrderStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

        // getUserID from session, if guest , auto give a negative ID
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            userId = -1 * (int)(System.currentTimeMillis() % 100000);
            session.setAttribute("userId", userId);
        }

        try {
            // get para from frontend
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String action = request.getParameter("action"); // Save or Submit

            // submit -> Confirmed, save -> saved
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


            DBManager dbManager = (DBManager) session.getAttribute("dbmanager");
            OrderDao orderDao = new OrderDao(dbManager.getConnection());

            orderDao.saveOrder(order, userId);

            // Successful, jump to viewOrder.jsp
            response.sendRedirect(request.getContextPath() + "/viewOrder");


        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Failed to create order: " + e.getMessage());
            // if error, jump back to createOrder.jsp (Will change the jsp later)
            response.sendRedirect(request.getContextPath() + "/newOrder.jsp");

        }
    }
}

