package com.controller.PaymentController;

import com.bean.Customer;
import com.bean.Payment;
import com.dao.DBManager;
import com.dao.PaymentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ViewPayment")
public class ViewPayment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // check if user is logged in
        Customer loggedInUser = (Customer) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        // get the logged-in user's ID
        int userId = loggedInUser.getUserId();

        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao dao = db.getPaymentDao();

        String orderIdStr = req.getParameter("orderId");
        String searchPaymentIdStr = req.getParameter("searchPaymentId");
        String searchDate = req.getParameter("searchDate");

        try {
            List<Payment> payments = new ArrayList<>();

            // search by payment ID (restricted to current user)
            if (searchPaymentIdStr != null && !searchPaymentIdStr.isEmpty()) {
                try {
                    int searchPaymentId = Integer.parseInt(searchPaymentIdStr);
                    Payment p = dao.getPaymentByIdAndUser(searchPaymentId, userId);  // secure call
                    if (p != null) {
                        payments.add(p);
                    }
                } catch (NumberFormatException ignored) {}
            }
            // search by payment date (restricted to current user)
            else if (searchDate != null && !searchDate.isEmpty()) {
                payments = dao.getPaymentsByDateAndUser(searchDate, userId);
            }
            // search by order ID (restricted to current user)
            else if (orderIdStr != null && !orderIdStr.isEmpty()) {
                int orderId = Integer.parseInt(orderIdStr);
                payments = dao.getPaymentsByOrderIdAndUser(orderId, userId);
                req.setAttribute("orderId", orderId);
            }
            // load all payments for the current user
            else {
                payments = dao.getPaymentsByUserId(userId);
            }

            // forward payment list to JSP
            req.setAttribute("paymentList", payments);
            req.getRequestDispatcher("/views/ViewPayment.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving payments");
        }
    }
}
