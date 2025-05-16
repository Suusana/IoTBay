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

@WebServlet("/ProceedPayment")
public class ProceedPayment extends HttpServlet {
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
            int paymentId = Integer.parseInt(req.getParameter("paymentId"));
            int userId = customer.getUserId();

            // get payment and validate
            Payment payment = dao.getPaymentByIdAndUser(paymentId, userId);
            if (payment == null || !"Pending".equalsIgnoreCase(payment.getStatus())) {
                req.setAttribute("message", "Invalid or already processed payment.");
                req.getRequestDispatcher("/ViewPayment").forward(req, resp);
                return;
            }

            // update status
            payment.setStatus("Paid");
            payment.setPaymentDate(new Date(System.currentTimeMillis()));
            dao.update(payment);

            // log the update
            PaymentLog log = new PaymentLog();
            log.setPaymentId(payment.getPaymentId());
            log.setUserId(userId);
            log.setOrderId(payment.getOrderId());
            log.setAction("UPDATE");
            log.setTimestamp(new Date(System.currentTimeMillis()));
            logDao.log(log);

            req.setAttribute("message", "Payment completed successfully.");
            req.getRequestDispatcher("/ViewPayment").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "An error occurred while processing payment.");
            req.getRequestDispatcher("/ViewPayment").forward(req, resp);
        }
    }
}
