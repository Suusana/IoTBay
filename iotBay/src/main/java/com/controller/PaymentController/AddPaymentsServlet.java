package com.controller.PaymentController;

import com.bean.Invoice;
import com.bean.Payment;
import com.dao.DBManager;
import com.dao.InvoiceDao;
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

@WebServlet("/AddPayment")
public class AddPaymentsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        if (db == null) {
            throw new ServletException("DB connection not initialized in session.");
        }
        PaymentDao paymentDao = db.getPaymentDao();
        InvoiceDao invoiceDao = db.getInvoiceDao();

        req.setCharacterEncoding("UTF-8");
        // Retrieve form parameters
        String orderIdStr    = req.getParameter("orderId");
        String method        = req.getParameter("method");
        String cardHolder    = req.getParameter("cardHolder");
        String cardNumber    = req.getParameter("cardNumber");
        String expiryDateStr = req.getParameter("expiryDate");

        // Validate required fields are not empty
        if (orderIdStr == null || orderIdStr.trim().isEmpty() ||
                method == null || method.trim().isEmpty() ||
                cardHolder == null || cardHolder.trim().isEmpty() ||
                cardNumber == null || cardNumber.trim().isEmpty() ||
                expiryDateStr == null || expiryDateStr.trim().isEmpty()) {
            req.setAttribute("errorMessage", "Please fill in all required fields.");
            req.getRequestDispatcher("/views/Payment.jsp").forward(req, resp);
            return;
        }

        int orderId;
        Date expiryDate;
        try {
            // Validate numeric and date formats
            orderId = Integer.parseInt(orderIdStr);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Order ID must be a number.");
            req.getRequestDispatcher("/views/Payment.jsp").forward(req, resp);
            return;
        }
        try {
            expiryDate = Date.valueOf(expiryDateStr);
        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", "Invalid expiry date format.");
            req.getRequestDispatcher("/views/Payment.jsp").forward(req, resp);
            return;
        }

        // Create Payment object and set fields
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setMethod(method);
        payment.setCardHolder(cardHolder);
        payment.setCardNumber(cardNumber);
        payment.setExpiryDate(expiryDate);
        payment.setPaymentDate(new Date(System.currentTimeMillis())); // current time as payment date
        payment.setStatus("Saved");

        try {
            // Retrieve invoice to determine amount
            Invoice invoice = invoiceDao.findInvoiceByOrderId(orderId);
            if (invoice == null) {
                req.setAttribute("errorMessage", "Invalid Order ID or no invoice found.");
                req.getRequestDispatcher("/views/Payment.jsp").forward(req, resp);
                return;
            }
            // Set amount from invoice total price
            payment.setAmount(BigDecimal.valueOf(invoice.getTotalPrice()));
            // Save payment to database
            paymentDao.save(payment);
            // Redirect to view payments list for this order
            resp.sendRedirect(req.getContextPath() + "/ViewPayments?orderId=" + orderId);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Payment creation failed: " + e.getMessage());
            req.getRequestDispatcher("/views/Payment.jsp").forward(req, resp);
        }
    }
}
