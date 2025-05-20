package com.controller.PaymentController;

import com.bean.Customer;
import com.bean.Payment;
import com.dao.DBManager;
import com.dao.PaymentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/DeletePayment")
public class DeletePayment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao dao = db.getPaymentDao();

        // Ensure the user is logged in
        Customer customer = (Customer) session.getAttribute("loggedInUser");
        if (customer == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        try {
            String paymentIdStr = req.getParameter("paymentId");

            // Validate and parse payment ID
            int paymentId = Integer.parseInt(paymentIdStr);
            int userId = customer.getUserId();

            // Retrieve the payment record by ID and user
            Payment payment = dao.getPaymentByIdAndUser(paymentId, userId);

            // Allow deletion only if the payment exists and is not marked as 'Paid'
            if (payment != null && !"Paid".equalsIgnoreCase(payment.getStatus())) {
                dao.delete(paymentId);
            }

        } catch (NumberFormatException e) {
            // Invalid or missing payment ID
        } catch (Exception e) {
            // Handle unexpected errors
            e.printStackTrace();
        }

        // Redirect to payment view after processing
        resp.sendRedirect(req.getContextPath() + "/ViewPayment");
    }

    // Redirect GET requests to ViewPayment to avoid HTTP 405 error
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/ViewPayment");
    }
}
