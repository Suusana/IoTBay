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
            String idParam = req.getParameter("paymentId");
            if (idParam == null || idParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Missing payment ID");
            }

            int paymentId = Integer.parseInt(idParam);
            Payment payment = dao.getPaymentById(paymentId);

            if (payment == null) {
                req.setAttribute("error", "Payment not found.");
                req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                return;
            }

            String method = req.getParameter("actualMethod");

            if ("Credit Card".equalsIgnoreCase(method)) {
                String firstName = req.getParameter("firstName");
                String lastName = req.getParameter("lastName");
                String cardHolder = firstName + " " + lastName;
                String cardNumber = req.getParameter("cardNumber");
                String cvc = req.getParameter("cvc");
                String expiryDate = req.getParameter("expiryDate");

                if (cardNumber == null || cardNumber.length() != 16) {
                    req.setAttribute("error", "Card number must be 16 digits.");
                    req.setAttribute("payment", payment);
                    req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                    return;
                }

                payment.setMethod("Credit Card");
                payment.setCardHolder(cardHolder);
                payment.setCardNumber(cardNumber);
                payment.setCvc(cvc);

                try {
                    payment.setExpiryDate(Date.valueOf(expiryDate));
                } catch (IllegalArgumentException e) {
                    req.setAttribute("error", "Invalid expiry date.");
                    req.setAttribute("payment", payment);
                    req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                    return;
                }

                payment.setStatus("Paid");
                payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));

            } else if ("Bank Transfer".equalsIgnoreCase(method)) {
                String bsb = req.getParameter("bsb");
                String accountName = req.getParameter("accountName");
                String accountNumber = req.getParameter("accountNumber");

                if (bsb == null || bsb.length() != 6 ||
                        accountName == null || accountName.trim().isEmpty() ||
                        accountNumber == null || accountNumber.length() < 6) {

                    req.setAttribute("error", "Please fill out all bank transfer details correctly.");
                    req.setAttribute("payment", payment);
                    req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                    return;
                }

                payment.setMethod("Bank Transfer");
                payment.setBsb(bsb);
                payment.setAccountName(accountName);
                payment.setAccountNumber(accountNumber);

                payment.setStatus("Pending");
                payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));

            } else {
                req.setAttribute("error", "Unsupported payment method.");
                req.setAttribute("payment", payment);
                req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                return;
            }

            if (payment.getAmount() == null) {
                payment.setAmount(new java.math.BigDecimal("0.00"));
            }

            int rowsUpdated = dao.update(payment);
            if (rowsUpdated == 0) {
                req.setAttribute("error", "Update failed. No rows were affected.");
                req.setAttribute("payment", payment);
                req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                return;
            }

            PaymentLog log = new PaymentLog();
            log.setPaymentId(payment.getPaymentId());
            log.setUserId(payment.getUserId());
            log.setOrderId(payment.getOrderId());
            log.setAction("UPDATE");
            log.setTimestamp(new Timestamp(System.currentTimeMillis()));
            logDao.log(log);

            resp.sendRedirect(req.getContextPath() + "/ViewPayment");

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
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
