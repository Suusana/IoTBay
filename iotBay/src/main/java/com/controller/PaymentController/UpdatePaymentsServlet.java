package com.controller.PaymentController;

import com.bean.Payment;
import com.dao.DBManager;
import com.dao.PaymentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

//@WebServlet("/UpdatePayment")
//public class UpdatePaymentsServlet extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//
//        HttpSession session = req.getSession();
//        DBManager db = (DBManager) session.getAttribute("db");
//
//        if (db == null) {
//            throw new ServletException("DB connection not initialized in session.");
//        }
//
//        PaymentDao dao = db.getPaymentDao();
//
//        try {
//            int paymentId = Integer.parseInt(req.getParameter("paymentId"));
//            int orderId = Integer.parseInt(req.getParameter("orderId"));
//            String method = req.getParameter("method");
//            String cardHolder = req.getParameter("cardHolder");
//            String cardNumber = req.getParameter("cardNumber");
//            Date expiryDate = Date.valueOf(req.getParameter("expiryDate"));
//            BigDecimal amount = new BigDecimal(req.getParameter("amount"));
//            Date paymentDate = Date.valueOf(req.getParameter("paymentDate"));
//            String status = req.getParameter("status");
//
//            Payment payment = new Payment();
//            payment.setPaymentId(paymentId);
//            payment.setOrderId(orderId);
//            payment.setMethod(method);
//            payment.setCardHolder(cardHolder);
//            payment.setCardNumber(cardNumber);
//            payment.setExpiryDate(expiryDate);
//            payment.setAmount(amount);
//            payment.setPaymentDate(paymentDate);
//            payment.setStatus(status);
//
//            dao.update(payment);
//
//            resp.sendRedirect(req.getContextPath() + "/ViewPayments?orderId=" + orderId);
//
//        } catch (SQLException | NumberFormatException e) {
//            e.printStackTrace();
//            req.setAttribute("error", "Failed to update payment.");
//            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
//        }
//    }
//}
