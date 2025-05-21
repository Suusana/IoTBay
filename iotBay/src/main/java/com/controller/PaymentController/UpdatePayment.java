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
import java.sql.Timestamp;

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
            // Server-side validation: check if paymentId parameter exists
            String idParam = req.getParameter("paymentId");
            if (idParam == null || idParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Missing payment ID");
            }

            // Parse the ID and fetch the payment record
            int paymentId = Integer.parseInt(idParam);
            Payment payment = dao.getPaymentById(paymentId);

            // Validate that the payment exists
            if (payment == null) {
                req.setAttribute("error", "Payment not found.");
                req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                return;
            }

            // Determine the selected payment method
            String method = req.getParameter("actualMethod");

            if ("Credit Card".equalsIgnoreCase(method)) {
                // Collect credit card fields
                String firstName = req.getParameter("firstName");
                String lastName = req.getParameter("lastName");
                String cardHolder = firstName + " " + lastName;
                String cardNumber = req.getParameter("cardNumber");
                String cvc = req.getParameter("cvc");
                String expiryDate = req.getParameter("expiryDate");

                // Server-side validation: ensure card number is exactly 16 digits
                if (cardNumber == null || cardNumber.length() != 16) {
                    req.setAttribute("error", "Card number must be 16 digits.");
                    req.setAttribute("payment", payment);
                    req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                    return;
                }

                // Set updated credit card info
                payment.setMethod("Credit Card");
                payment.setCardHolder(cardHolder);
                payment.setCardNumber(cardNumber);
                payment.setCvc(cvc);

                // Validate expiry date format
                try {
                    payment.setExpiryDate(Date.valueOf(expiryDate));
                } catch (IllegalArgumentException e) {
                    req.setAttribute("error", "Invalid expiry date.");
                    req.setAttribute("payment", payment);
                    req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                    return;
                }

                // Mark payment as completed
                payment.setStatus("Paid");
                payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));

            } else if ("Bank Transfer".equalsIgnoreCase(method)) {
                // Collect bank transfer fields
                String bsb = req.getParameter("bsb");
                String accountName = req.getParameter("accountName");
                String accountNumber = req.getParameter("accountNumber");

                // Server-side validation: check all bank transfer fields
                if (bsb == null || bsb.length() != 6 ||
                        accountName == null || accountName.trim().isEmpty() ||
                        accountNumber == null || accountNumber.length() < 6) {

                    req.setAttribute("error", "Please fill out all bank transfer details correctly.");
                    req.setAttribute("payment", payment);
                    req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                    return;
                }

                // Set updated bank transfer info
                payment.setMethod("Bank Transfer");
                payment.setBsb(bsb);
                payment.setAccountName(accountName);
                payment.setAccountNumber(accountNumber);

                // Bank transfers are saved as pending
                payment.setStatus("Pending");
                payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));

            } else {
                // Server-side validation: unknown payment method
                req.setAttribute("error", "Unsupported payment method.");
                req.setAttribute("payment", payment);
                req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                return;
            }

            // Ensure amount is not null (for safety)
            if (payment.getAmount() == null) {
                payment.setAmount(new java.math.BigDecimal("0.00"));
            }

            // Attempt to update the payment record in the database
            int rowsUpdated = dao.update(payment);
            if (rowsUpdated == 0) {
                req.setAttribute("error", "Update failed. No rows were affected.");
                req.setAttribute("payment", payment);
                req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                return;
            }

            // Log the update action in the payment log table
            PaymentLog log = new PaymentLog();
            log.setPaymentId(payment.getPaymentId());
            log.setUserId(payment.getUserId());
            log.setOrderId(payment.getOrderId());
            log.setAction("UPDATE");
            log.setTimestamp(new Timestamp(System.currentTimeMillis()));
            logDao.log(log);

            // Redirect to the payment list view after successful update
            resp.sendRedirect(req.getContextPath() + "/ViewPayment");

        } catch (NumberFormatException e) {
            // Input was not a valid number
            req.setAttribute("error", "Invalid number format: " + e.getMessage());
            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);

        } catch (SQLException e) {
            // Handle any database-related errors
            req.setAttribute("error", "Database error: " + e.getMessage());
            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);

        } catch (IllegalArgumentException e) {
            // Handle other input validation issues
            req.setAttribute("error", "Invalid input: " + e.getMessage());
            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Redirect GET requests to home to avoid misuse
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
