package com.controller.PaymentController;

import com.bean.Customer;
import com.bean.Order;
import com.bean.Payment;
import com.bean.PaymentLog;
import com.dao.DBManager;
import com.dao.OrderDao;
import com.dao.PaymentDao;
import com.dao.PaymentLogDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet("/ConfirmPayment")
public class ConfirmPayment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        Customer customer = (Customer) session.getAttribute("loggedInUser");

        try {
            PaymentDao paymentDao = db.getPaymentDao();
            PaymentLogDao logDao = db.getPaymentLogDao();
            OrderDao orderDao = db.getOrderDao();

            // Parse the order ID from the request (fallback if invalid)
            int orderId;
            try {
                orderId = Integer.parseInt(req.getParameter("orderId"));
            } catch (NumberFormatException e) {
                req.setAttribute("error", "Invalid order ID.");
                req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                return;
            }

            // Get selected payment method
            String method = req.getParameter("paymentMethod");

            // Load the order details to verify products and amount
            Order order = orderDao.findOrderByOrderId(orderId);
            if (order == null || order.getProducts() == null || order.getProducts().isEmpty()) {
                req.setAttribute("message", "Order details missing.");
                req.setAttribute("orderId", orderId);
                req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                return;
            }

            BigDecimal totalAmount = order.getTotalAmount();

            // Create a new Payment object and populate basic details
            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setAmount(totalAmount);
            payment.setMethod(method);
            payment.setPaymentDate(new Date(System.currentTimeMillis()));
            payment.setUserId(customer != null ? customer.getUserId() : 0);  // 0 = guest user

            if ("Credit Card".equalsIgnoreCase(method)) {
                // Collect card input fields
                String firstName = req.getParameter("firstName");
                String lastName = req.getParameter("lastName");
                String part1 = req.getParameter("cardPart1");
                String part2 = req.getParameter("cardPart2");
                String part3 = req.getParameter("cardPart3");
                String part4 = req.getParameter("cardPart4");
                String cvc = req.getParameter("cvc");
                String expiryDate = req.getParameter("expiryDate");

                // Validate card number parts (must be exactly 4 digits each)
                if (!part1.matches("\\d{4}") || !part2.matches("\\d{4}")
                        || !part3.matches("\\d{4}") || !part4.matches("\\d{4}")) {
                    req.setAttribute("error", "Card numbers must be 16 digits.");
                    req.setAttribute("orderId", orderId);
                    req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                    return;
                }

                // Validate CVC (must be 3 digits)
                if (!cvc.matches("\\d{3}")) {
                    req.setAttribute("error", "CVC must be 3 digits.");
                    req.setAttribute("orderId", orderId);
                    req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                    return;
                }

                // Validate expiry date format and ensure it is not in the past
                LocalDate expiry;
                try {
                    expiry = LocalDate.parse(expiryDate);
                    if (expiry.isBefore(LocalDate.now())) {
                        req.setAttribute("error", "Expiry date must be in the future.");
                        req.setAttribute("orderId", orderId);
                        req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                        return;
                    }
                } catch (Exception e) {
                    req.setAttribute("error", "Invalid expiry date.");
                    req.setAttribute("orderId", orderId);
                    req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                    return;
                }

                // Store the validated credit card details
                String cardNumber = part1 + part2 + part3 + part4;
                payment.setCardHolder(firstName + " " + lastName);
                payment.setCardNumber(cardNumber);
                payment.setCvc(cvc);
                payment.setExpiryDate(Date.valueOf(expiry));
                payment.setStatus("Paid");

            } else if ("Bank Transfer".equalsIgnoreCase(method)) {
                // Get bank transfer info
                String bsb = req.getParameter("bsb");
                String accountName = req.getParameter("accountName");
                String accountNumber = req.getParameter("accountNumber");

                // Validate BSB (must be 6 digits)
                if (bsb == null || !bsb.matches("\\d{6}")) {
                    req.setAttribute("error", "BSB must be 6 digits.");
                    req.setAttribute("orderId", orderId);
                    req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                    return;
                }

                // Validate account number (typically 6 to 10 digits)
                if (accountNumber == null || !accountNumber.matches("\\d{6,10}")) {
                    req.setAttribute("error", "Account number must be 6–10 digits.");
                    req.setAttribute("orderId", orderId);
                    req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                    return;
                }

                // Make sure account name is provided
                if (accountName == null || accountName.trim().isEmpty()) {
                    req.setAttribute("error", "Account name cannot be empty.");
                    req.setAttribute("orderId", orderId);
                    req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                    return;
                }

                // Store bank transfer info
                payment.setBsb(bsb);
                payment.setAccountName(accountName);
                payment.setAccountNumber(accountNumber);
                payment.setStatus("Pending");

            } else {
                // Handle unknown payment methods
                req.setAttribute("error", "Invalid payment method.");
                req.setAttribute("orderId", orderId);
                req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                return;
            }

            // Save the payment in the database
            paymentDao.save(payment, payment.getUserId(), orderId);

            // Record the payment action in the log
            PaymentLog log = new PaymentLog();
            log.setPaymentId(payment.getPaymentId());
            log.setUserId(payment.getUserId());
            log.setOrderId(orderId);
            log.setAction("CREATE");
            log.setTimestamp(new Date(System.currentTimeMillis()));
            logDao.log(log);

            // Set attributes for confirmation screen
            req.setAttribute("payment", payment);
            req.setAttribute("orderId", orderId);

            // Check if this is a guest user
            String guestEmail = req.getParameter("guestEmail");
            boolean isGuestUser = customer == null ||
                    (customer.getUsername() != null && customer.getUsername().startsWith("Guest"));

            // Forward to the appropriate confirmation page
            if (isGuestUser && guestEmail != null && !guestEmail.isEmpty()) {
                session.setAttribute("guestEmail", guestEmail);
                req.getRequestDispatcher("/views/GuestConfirmPayment.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/views/ConfirmPayment.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Payment processing failed.");
            req.setAttribute("orderId", req.getParameter("orderId"));
            req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
        }
    }
}
