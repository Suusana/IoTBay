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

        Customer customer = (Customer) session.getAttribute("loggedInUser");
        if (customer == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        try {
            int paymentId = Integer.parseInt(req.getParameter("paymentId"));
            int userId = customer.getUserId();

            Payment payment = dao.getPaymentByIdAndUser(paymentId, userId);
            if (payment == null) {
                req.setAttribute("message", "Payment record not found.");
            } else if ("Paid".equalsIgnoreCase(payment.getStatus())) {
                req.setAttribute("message", "Cannot delete a completed payment.");
            } else {
                dao.delete(paymentId);
                req.setAttribute("message", "Payment deleted successfully.");
            }

        } catch (NumberFormatException e) {
            req.setAttribute("message", "Invalid Payment ID.");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "Error occurred while deleting payment.");
        }

        req.getRequestDispatcher("/ViewPayment").forward(req, resp);
    }
}
