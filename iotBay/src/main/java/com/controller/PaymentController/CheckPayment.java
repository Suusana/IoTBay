package com.controller.PaymentController;

import com.bean.Customer;
import com.bean.Payment;
import com.dao.DBManager;
import com.dao.PaymentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/CheckPayment")
public class CheckPayment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao paymentDao = db.getPaymentDao();

        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            Customer customer = (Customer) session.getAttribute("loggedInUser");

            // Check if there's already a payment registered for this order
            List<Payment> paymentsForOrder = paymentDao.getPaymentsByOrderId(orderId);
            if (!paymentsForOrder.isEmpty()) {
                // If payment exists, redirect directly to the payment view
                response.sendRedirect(request.getContextPath() + "/ViewPayment?orderId=" + orderId);
                return;
            }

            // Retrieve previously used payment methods by the user
            List<Payment> previousPayments = paymentDao.getPaymentsByUserId(customer.getUserId());

            // Pass order ID and previous payments to the JSP
            request.setAttribute("orderId", orderId);
            request.setAttribute("previousPayments", previousPayments);

            // Forward to confirmation page with payment method selection modal
            request.getRequestDispatcher("/views/confirmPopup.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // If something goes wrong, redirect back to order view page
            response.sendRedirect(request.getContextPath() + "/viewOrder");
        }
    }
}
