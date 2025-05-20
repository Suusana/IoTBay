package com.controller.PaymentController;

import com.bean.Customer;
import com.bean.Payment;
import com.dao.DBManager;
import com.dao.PaymentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;
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

        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getUsername().toLowerCase().contains("guest")) {
            resp.sendRedirect(req.getContextPath() + "/GuestViewPayment");
            return;
        }

        int userId = loggedInUser.getUserId();
        String orderIdStr = req.getParameter("orderId");
        String searchPaymentIdStr = req.getParameter("searchPaymentId");
        String searchDateStr = req.getParameter("searchDate");

        try {
            List<Payment> payments = new ArrayList<>();

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
            } else if (searchDateStr != null && !searchDateStr.isEmpty()) {
                try {
                    Date searchDate = Date.valueOf(searchDateStr); // yyyy-MM-dd
                    payments = dao.getPaymentsByTimestampDate(searchDate, userId);
                } catch (IllegalArgumentException e) {
                    req.setAttribute("message", "Invalid Date format.");
                }
            } else if (orderIdStr != null && !orderIdStr.isEmpty()) {
                try {
                    int orderId = Integer.parseInt(orderIdStr);
                    payments = dao.getPaymentsByOrderIdAndUser(orderId, userId);
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "Invalid Order ID.");
                }
            } else {
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
