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

@WebServlet("/UpdatePayment")
public class UpdatePaymentsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        if (db == null) {
            throw new ServletException("DB connection not initialized in session.");
        }
        PaymentDao paymentDao = db.getPaymentDao();

        req.setCharacterEncoding("UTF-8");
        // Retrieve form parameters
        String paymentIdStr   = req.getParameter("paymentId");
        String orderIdStr     = req.getParameter("orderId");
        String method         = req.getParameter("method");
        String cardHolder     = req.getParameter("cardHolder");
        String cardNumber     = req.getParameter("cardNumber");
        String expiryDateStr  = req.getParameter("expiryDate");
        String amountStr      = req.getParameter("amount");
        String paymentDateStr = req.getParameter("paymentDate");
        String status         = req.getParameter("status");

        // Validate required fields are not empty
        if (paymentIdStr == null || paymentIdStr.trim().isEmpty() ||
                orderIdStr == null || orderIdStr.trim().isEmpty() ||
                method == null || method.trim().isEmpty() ||
                cardHolder == null || cardHolder.trim().isEmpty() ||
                cardNumber == null || cardNumber.trim().isEmpty() ||
                expiryDateStr == null || expiryDateStr.trim().isEmpty() ||
                amountStr == null || amountStr.trim().isEmpty() ||
                paymentDateStr == null || paymentDateStr.trim().isEmpty() ||
                status == null || status.trim().isEmpty()) {
            req.setAttribute("errorMessage", "Please fill in all required fields.");
            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
            return;
        }

        int paymentId, orderId;
        BigDecimal amount;
        Date expiryDate, paymentDate;
        try {
            // Validate numeric formats for IDs
            paymentId = Integer.parseInt(paymentIdStr);
            orderId = Integer.parseInt(orderIdStr);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Invalid payment or order ID.");
            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
            return;
        }
        try {
            // Validate amount numeric format
            amount = new BigDecimal(amountStr);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Amount must be a numeric value.");
            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
            return;
        }
        try {
            // Validate date formats
            expiryDate = Date.valueOf(expiryDateStr);
        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", "Invalid expiry date format.");
            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
            return;
        }
        try {
            paymentDate = Date.valueOf(paymentDateStr);
        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", "Invalid payment date format.");
            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
            return;
        }

        // Create Payment object and set fields for update
        Payment payment = new Payment();
        payment.setPaymentId(paymentId);
        payment.setOrderId(orderId);
        payment.setMethod(method);
        payment.setCardHolder(cardHolder);
        payment.setCardNumber(cardNumber);
        payment.setExpiryDate(expiryDate);
        payment.setAmount(amount);
        payment.setPaymentDate(paymentDate);
        payment.setStatus(status);

        try {
            // Update payment in database
            paymentDao.update(payment);
            // Redirect to view updated payments list for this order
            resp.sendRedirect(req.getContextPath() + "/ViewPayments?orderId=" + orderId);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Payment update failed: " + e.getMessage());
            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
        }
    }
}
