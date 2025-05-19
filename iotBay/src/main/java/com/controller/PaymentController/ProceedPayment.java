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

@WebServlet("/ProceedPayment")
public class ProceedPayment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("[DEBUG] Entered doPost() in ProceedPayment");

        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao dao = db.getPaymentDao();
        PaymentLogDao logDao = db.getPaymentLogDao();

        try {
            String idParam = req.getParameter("paymentId");
            System.out.println("[DEBUG] Received paymentId: " + idParam);

            if (idParam == null || idParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Missing paymentId");
            }

            int paymentId = Integer.parseInt(idParam);
            Payment payment = dao.getPaymentById(paymentId);

            if (payment == null) {
                System.out.println("[DEBUG] Payment not found.");
            } else if ("Paid".equalsIgnoreCase(payment.getStatus())) {
                System.out.println("[DEBUG] Payment already marked as Paid.");
            } else {
                System.out.println("[DEBUG] Proceeding payment ID: " + paymentId);

                // Update status and payment date
                payment.setStatus("Paid");
                payment.setPaymentDate(new Date(System.currentTimeMillis()));

                dao.update(payment);
                System.out.println("[DEBUG] Payment updated to Paid");

                // log the proceed action
                PaymentLog log = new PaymentLog();
                log.setPaymentId(payment.getPaymentId());
                log.setUserId(payment.getUserId());
                log.setOrderId(payment.getOrderId());
                log.setAction("PROCEED");
                log.setTimestamp(new Date(System.currentTimeMillis()));
                logDao.log(log);

                System.out.println("[DEBUG] Payment action logged as PROCEED");
            }

        } catch (Exception e) {
            System.out.println("[DEBUG] Exception in ProceedPayment:");
            e.printStackTrace();
        }

        // Use redirect to prevent 405 error
        resp.sendRedirect(req.getContextPath() + "/ViewPayment");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // redirect GET requests to ViewPayment
        resp.sendRedirect(req.getContextPath() + "/ViewPayment");
    }
}
