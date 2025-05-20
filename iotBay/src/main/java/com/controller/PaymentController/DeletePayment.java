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

        System.out.println("[DEBUG] Entered doPost() in DeletePayment");

        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao dao = db.getPaymentDao();

        Customer customer = (Customer) session.getAttribute("loggedInUser");
        if (customer == null) {
            System.out.println("[DEBUG] No logged-in user. Redirecting to login.jsp");
            resp.sendRedirect("login.jsp");
            return;
        }

        try {
            String paymentIdStr = req.getParameter("paymentId");
            System.out.println("[DEBUG] paymentId parameter: " + paymentIdStr);

            int paymentId = Integer.parseInt(paymentIdStr);
            int userId = customer.getUserId();
            System.out.println("[DEBUG] Logged-in user ID: " + userId);

            Payment payment = dao.getPaymentByIdAndUser(paymentId, userId);

            if (payment == null) {
                System.out.println("[DEBUG] Payment not found or not owned by user.");
                // You could log this or show feedback later
            } else if ("Paid".equalsIgnoreCase(payment.getStatus())) {
                System.out.println("[DEBUG] Payment is 'Paid'. Cannot delete.");
                // You could log this or show feedback later
            } else {
                System.out.println("[DEBUG] Deleting payment ID: " + paymentId);
                dao.delete(paymentId);
                System.out.println("[DEBUG] Payment deleted successfully.");
            }

        } catch (NumberFormatException e) {
            System.out.println("[DEBUG] Invalid paymentId format.");
        } catch (Exception e) {
            System.out.println("[DEBUG] Exception occurred during deletion:");
            e.printStackTrace();
        }

        // Use redirect to avoid 405 error
        resp.sendRedirect(req.getContextPath() + "/ViewPayment");
    }

    // handle GET defensively (not used, but avoid 405 if accessed directly)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/ViewPayment");
    }
}
