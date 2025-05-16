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

@WebServlet("/SelectPayment")
public class SelectPayment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        DBManager db = (DBManager) session.getAttribute("db");

        try {
            // Get current logged-in user
            Customer customer = (Customer) session.getAttribute("loggedInUser");

            // Get orderId from parameter
            int orderId = Integer.parseInt(request.getParameter("orderId"));

            // Get saved payment methods for the user
            PaymentDao paymentDao = db.getPaymentDao();
            List<Payment> savedPayments = paymentDao.getPaymentsByUserId(customer.getUserId());

            // Set data to forward
            request.setAttribute("orderId", orderId);
            request.setAttribute("savedPayments", savedPayments);

            request.getRequestDispatcher("/views/SelectPayment.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/viewOrder");
        }
    }
}
