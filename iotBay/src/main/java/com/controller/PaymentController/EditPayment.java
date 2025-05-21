package com.controller.PaymentController;

import com.bean.Payment;
import com.dao.DBManager;
import com.dao.PaymentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/EditPayment")
public class EditPayment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Validate that the paymentId parameter is a valid number
            int paymentId = Integer.parseInt(req.getParameter("paymentId"));

            HttpSession session = req.getSession();
            DBManager db = (DBManager) session.getAttribute("db");
            PaymentDao dao = db.getPaymentDao();

            // Look up the payment record by ID
            Payment payment = dao.getPaymentById(paymentId);

            // Server-side validation: check if the payment exists
            if (payment == null) {
                // Return 404 Not Found if payment does not exist
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Payment not found.");
                return;
            }

            // Server-side validation: prevent editing of already paid payments
            if ("Paid".equalsIgnoreCase(payment.getStatus())) {
                // Return 403 Forbidden if trying to edit a completed payment
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Cannot edit a paid payment.");
                return;
            }

            // If valid, forward the payment object to the edit form view
            req.setAttribute("payment", payment);
            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            // Server-side validation: handle invalid or missing payment ID
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid payment ID.");
        } catch (SQLException e) {
            // Handle database error
            throw new ServletException("Failed to load payment", e);
        }
    }
}
