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
            // Retrieve the order ID from the request parameter
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            request.setAttribute("orderId", orderId);

            // Check if the user is logged in
            Object userObj = session.getAttribute("loggedInUser");

            if (userObj == null) {
                // If the user is a guest, no saved payment methods are needed
                // Just forward to the SelectPayment page with guest email
                String guestEmail = (String) session.getAttribute("guestEmail");
                request.setAttribute("guestEmail", guestEmail); // in case the JSP needs it
                request.getRequestDispatcher("/views/SelectPayment.jsp").forward(request, response);
                return;
            }

            // If the user is logged in, retrieve their saved payment methods
            Customer customer = (Customer) userObj;
            PaymentDao paymentDao = db.getPaymentDao();
            List<Payment> savedPayments = paymentDao.getPaymentsByUserId(customer.getUserId());

            // Set the data and forward to the SelectPayment page
            request.setAttribute("savedPayments", savedPayments);
            request.getRequestDispatcher("/views/SelectPayment.jsp").forward(request, response);

        } catch (Exception e) {
            // If any error occurs, redirect the user back to the order page
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/viewOrder");
        }
    }
}
