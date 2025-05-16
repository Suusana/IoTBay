package com.controller.PaymentController;

import com.bean.Customer;
import com.bean.Payment;
import com.dao.DBManager;
import com.dao.PaymentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ViewPayment")
public class ViewPayment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao dao = db.getPaymentDao();

        // Check if user is logged in
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");

        // Reject if guest or not logged in
        if (loggedInUser == null || loggedInUser.getUsername().toLowerCase().contains("guest")) {
            resp.sendRedirect(req.getContextPath() + "/GuestViewPayment");
            return;
        }

        // Get the logged-in user's ID
        int userId = loggedInUser.getUserId();

        // Get search parameters
        String orderIdStr = req.getParameter("orderId");
        String searchPaymentIdStr = req.getParameter("searchPaymentId");
        String searchDate = req.getParameter("searchDate");

        try {
            List<Payment> payments = new ArrayList<>();

            // Search by payment ID
            if (searchPaymentIdStr != null && !searchPaymentIdStr.isEmpty()) {
                try {
                    int searchPaymentId = Integer.parseInt(searchPaymentIdStr);
                    Payment p = dao.getPaymentByIdAndUser(searchPaymentId, userId);
                    if (p != null) {
                        payments.add(p);
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "Invalid Payment ID format.");
                }
            }
            // Search by date
            else if (searchDate != null && !searchDate.isEmpty()) {
                payments = dao.getPaymentsByDateAndUser(searchDate, userId);
            }
            // View by order ID
            else if (orderIdStr != null && !orderIdStr.isEmpty()) {
                try {
                    int orderId = Integer.parseInt(orderIdStr);
                    payments = dao.getPaymentsByOrderIdAndUser(orderId, userId);
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "Invalid Order ID.");
                }
            }
            // View all for this user
            else {
                payments = dao.getPaymentsByUserId(userId);
            }

            req.setAttribute("paymentList", payments);
            req.getRequestDispatcher("/views/ViewPayment.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}
