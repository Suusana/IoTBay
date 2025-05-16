package com.controller.PaymentController;

import com.bean.Customer;
import com.bean.Order;
import com.bean.Payment;
import com.dao.CustomerDao;
import com.dao.DBManager;
import com.dao.OrderDao;
import com.dao.PaymentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/GuestViewPayment")
public class GuestViewPayment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        OrderDao orderDao = db.getOrderDao();
        CustomerDao customerDao = db.getCustomerDao();
        PaymentDao paymentDao = db.getPaymentDao();

        String orderIdStr = req.getParameter("orderId");
        String guestEmail = req.getParameter("guestEmail");

        try {
            // Only proceed if both order ID and email are provided
            if (orderIdStr != null && guestEmail != null && !orderIdStr.isEmpty() && !guestEmail.isEmpty()) {
                int orderId = Integer.parseInt(orderIdStr);

                // 1. Find the order by ID
                Order order = orderDao.findOrderByOrderId(orderId);

                if (order == null || order.getBuyer() == null) {
                    req.setAttribute("message", "Order not found or invalid.");
                    req.getRequestDispatcher("/views/GuestViewPayment.jsp").forward(req, resp);
                    return;
                }

                // 2. Verify guest email
                Customer buyer = order.getBuyer();
                if (!guestEmail.equalsIgnoreCase(buyer.getEmail())) {
                    req.setAttribute("message", "The email does not match the order.");
                    req.getRequestDispatcher("/views/GuestViewPayment.jsp").forward(req, resp);
                    return;
                }

                // 3. Retrieve payment history for the order
                List<Payment> guestPayments = paymentDao.getPaymentsByOrderId(orderId);

                req.setAttribute("guestPayments", guestPayments);
                req.setAttribute("orderId", orderId);
                req.getRequestDispatcher("/views/GuestViewPayment.jsp").forward(req, resp);

            } else {
                // No input yet – show the input form only
                req.getRequestDispatcher("/views/GuestViewPayment.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "An error occurred while retrieving guest payment.");
            req.getRequestDispatcher("/views/GuestViewPayment.jsp").forward(req, resp);
        }
    }
}
