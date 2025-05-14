package com.controller.PaymentController;

import com.bean.Customer;
import com.bean.Order;
import com.bean.Payment;
import com.dao.DBManager;
import com.dao.OrderDao;
import com.dao.PaymentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/CheckPendingPayment")
public class CheckPendingPayment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao paymentDao = db.getPaymentDao();
        OrderDao orderDao = db.getOrderDao();

        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            String step = request.getParameter("step");

            Customer customer = (Customer) session.getAttribute("loggedInUser");

            // Fetch the order and store it in session
            Order order = orderDao.findOrderByOrderId(orderId);
            session.setAttribute("order", order);

            // Fetch user's previously used payment records
            List<Payment> previousPayments = paymentDao.getPaymentsByUserId(customer.getUserId());

            // If user chooses to proceed with an existing pending payment
            if ("proceed".equalsIgnoreCase(step)) {
                request.setAttribute("orderId", orderId);
                request.setAttribute("previousPayments", previousPayments);

                // Look for any pending payment linked to this order
                for (Payment p : previousPayments) {
                    if (p.getOrderId() == orderId &&
                            p.getStatus() != null &&
                            p.getStatus().trim().equalsIgnoreCase("Pending")) {
                        request.setAttribute("pendingPayment", p);
                        break;
                    }
                }

                // Forward to confirmation popup with pending payment info
                request.getRequestDispatcher("/views/confirmPopup.jsp").forward(request, response);
                return;
            }

            // If no action yet, show prompt asking user to select or add payment
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("/views/confirmPrompt.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // If something goes wrong, send back to order view
            response.sendRedirect(request.getContextPath() + "/viewOrder");
        }
    }
}
