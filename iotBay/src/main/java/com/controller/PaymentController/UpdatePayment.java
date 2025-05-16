package com.controller.PaymentController;

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
import java.sql.SQLException;

@WebServlet("/UpdatePayment")
public class UpdatePayment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao dao = db.getPaymentDao();
        PaymentLogDao logDao = db.getPaymentLogDao();

        try {
            String idParam = req.getParameter("paymentId");

            // check paymentId is provided
            if (idParam == null || idParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Missing paymentId");
            }

            int paymentId = Integer.parseInt(idParam);
            Payment payment = dao.getPaymentById(paymentId);

            // if the payment doesn't exist, return to the edit form with an error
            if (payment == null) {
                req.setAttribute("error", "Payment not found.");
                req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                return;
            }

            // update payment fields with new form input
            payment.setCardHolder(req.getParameter("cardHolder"));
            payment.setCardNumber(req.getParameter("cardNumber"));
            payment.setCvc(req.getParameter("cvc"));

            // validate and set expiry date
            String expiryParam = req.getParameter("expiryDate");
            if (expiryParam == null || expiryParam.trim().isEmpty()) {
                req.setAttribute("error", "Expiry date is missing.");
                req.setAttribute("payment", payment);
                req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                return;
            }

            try {
                payment.setExpiryDate(Date.valueOf(expiryParam));
            } catch (IllegalArgumentException e) {
                req.setAttribute("error", "Invalid expiry date format.");
                req.setAttribute("payment", payment);
                req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                return;
            }

            // set defaults for missing optional fields
            if (payment.getAmount() == null)
                payment.setAmount(new java.math.BigDecimal("0.00"));
            if (payment.getPaymentDate() == null)
                payment.setPaymentDate(new Date(System.currentTimeMillis()));
            if (payment.getMethod() == null)
                payment.setMethod("CreditCard");
            if (payment.getStatus() == null)
                payment.setStatus("Paid");

            // save updated payment
            dao.update(payment);

            // log the update action
            PaymentLog log = new PaymentLog();
            log.setPaymentId(payment.getPaymentId());
            log.setUserId(payment.getUserId());
            log.setOrderId(payment.getOrderId());
            log.setAction("UPDATE");
            log.setTimestamp(new Date(System.currentTimeMillis()));
            logDao.log(log);

            // redirect to ViewPayment after successful update
            resp.sendRedirect(req.getContextPath() + "/ViewPayment?orderId=" + payment.getOrderId());

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid number format: " + e.getMessage());
            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("error", "Database error: " + e.getMessage());
            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", "Invalid input: " + e.getMessage());
            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // redirect GET access to home page (not allowed for updates)
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
