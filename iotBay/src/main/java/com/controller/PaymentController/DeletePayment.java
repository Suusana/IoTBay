package com.controller.PaymentController;

import com.bean.Customer;
import com.bean.Payment;
import com.bean.PaymentLog;
import com.dao.DBManager;
import com.dao.PaymentDao;
import com.dao.PaymentLogDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/DeletePayment")
public class DeletePayment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao dao = db.getPaymentDao();
        PaymentLogDao logDao = db.getPaymentLogDao();

        Customer customer = (Customer) session.getAttribute("loggedInUser");
        if (customer == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        try {
            String paymentIdStr = req.getParameter("paymentId");
            int paymentId = Integer.parseInt(paymentIdStr);
            int userId = customer.getUserId();

            // Retrieve the payment
            Payment payment = dao.getPaymentByIdAndUser(paymentId, userId);

            // Allow deletion only if not 'Paid'
            if (payment != null && !"Paid".equalsIgnoreCase(payment.getStatus())) {
                // Log the DELETE action
                PaymentLog log = new PaymentLog();
                log.setPaymentId(paymentId);
                log.setUserId(userId);
                log.setOrderId(payment.getOrderId());
                log.setAction("DELETE");
                log.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
                logDao.log(log);

                // Delete the payment
                dao.delete(paymentId);
            }

        } catch (NumberFormatException e) {
            // Invalid payment ID
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/ViewPayment");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/ViewPayment");
    }
}
