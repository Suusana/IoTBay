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
import java.util.List;

@WebServlet("/DeletePayment")
public class DeletePayment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao dao = db.getPaymentDao();
        PaymentLogDao logDao = db.getPaymentLogDao();

        try {
            String paymentIdStr = req.getParameter("paymentId");
            String orderIdStr = req.getParameter("orderId");

            // case 1: delete a specific payment by its ID
            if (paymentIdStr != null && !paymentIdStr.isEmpty()) {
                int paymentId = Integer.parseInt(paymentIdStr);
                Payment payment = dao.getPaymentById(paymentId);

                // prevent deleting a paid payment
                if ("Paid".equalsIgnoreCase(payment.getStatus())) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Cannot delete a paid payment.");
                    return;
                }

                dao.delete(paymentId);

                // log the delete action
                PaymentLog log = new PaymentLog();
                log.setPaymentId(paymentId);
                log.setUserId(payment.getUserId());
                log.setOrderId(payment.getOrderId());
                log.setAction("DELETE");
                log.setTimestamp(new Date(System.currentTimeMillis()));
                logDao.log(log);

                // redirect to a specific page if provided
                String redirectTo = req.getParameter("redirectTo");
                if (redirectTo != null && !redirectTo.isEmpty()) {
                    resp.sendRedirect(redirectTo);
                } else {
                    resp.sendRedirect(req.getContextPath() + "/ViewPayment?orderId=" + payment.getOrderId());
                }
                return;
            }

            // case 2: delete any pending payment under a specific order
            if (orderIdStr != null && !orderIdStr.isEmpty()) {
                int orderId = Integer.parseInt(orderIdStr);
                Customer customer = (Customer) session.getAttribute("loggedInUser");

                List<Payment> payments = dao.getPaymentsByOrderId(orderId);
                for (Payment p : payments) {
                    if ("Pending".equalsIgnoreCase(p.getStatus())) {
                        dao.delete(p.getPaymentId());

                        // log each deleted pending payment
                        PaymentLog log = new PaymentLog();
                        log.setPaymentId(p.getPaymentId());
                        log.setUserId(p.getUserId());
                        log.setOrderId(p.getOrderId());
                        log.setAction("DELETE");
                        log.setTimestamp(new Date(System.currentTimeMillis()));
                        logDao.log(log);
                    }
                }

                resp.sendRedirect(req.getContextPath() + "/ViewPayment?orderId=" + orderId);
                return;
            }

            // if no parameters provided, redirect to payment list
            resp.sendRedirect(req.getContextPath() + "/ViewPayment");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting payment.");
        }
    }
}
