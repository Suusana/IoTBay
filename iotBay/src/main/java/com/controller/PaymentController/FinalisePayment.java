package com.controller.PaymentController;

import com.bean.Customer;
import com.bean.Order;
import com.bean.Payment;
import com.dao.DBManager;
import com.dao.OrderDao;
import com.dao.PaymentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet("/FinalisePayment")
public class FinalisePayment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");

        String orderIdStr = req.getParameter("orderId");
        String method = req.getParameter("paymentMethod");

        try {
            int orderId = Integer.parseInt(orderIdStr);
            OrderDao orderDao = db.getOrderDao();
            PaymentDao paymentDao = db.getPaymentDao();

            Order order = orderDao.findOrderByOrderId(orderId);
            if (order == null || order.getProducts() == null || order.getProducts().isEmpty()) {
                req.setAttribute("message", "Order is invalid or empty.");
                req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                return;
            }

            BigDecimal amount = order.getTotalAmount();
            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setAmount(amount);
            payment.setMethod(method);
            payment.setPaymentDate(new Date(System.currentTimeMillis()));


            Customer customer = (Customer) session.getAttribute("loggedInUser");
            int userId = (customer != null) ? customer.getUserId() : 0;
            payment.setUserId(userId);

            // Credit Card
            if ("Credit Card".equalsIgnoreCase(method)) {
                String firstName = req.getParameter("firstName");
                String lastName = req.getParameter("lastName");
                String cardNumber = String.join("",
                        req.getParameter("cardPart1"),
                        req.getParameter("cardPart2"),
                        req.getParameter("cardPart3"),
                        req.getParameter("cardPart4")
                );
                String expiryDateStr = req.getParameter("expiryDate");
                String cvc = req.getParameter("cvc");


                if (firstName == null || lastName == null || !firstName.matches("[A-Za-z]+") || !lastName.matches("[A-Za-z]+")) {
                    req.setAttribute("message", "Card holder name must contain only letters.");
                    req.setAttribute("orderId", orderId);
                    req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                    return;
                }

                if (cardNumber == null || !cardNumber.matches("\\d{16}")) {
                    req.setAttribute("message", "Card number must be 16 digits.");
                    req.setAttribute("orderId", orderId);
                    req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                    return;
                }

                if (cvc == null || !cvc.matches("\\d{3,4}")) {
                    req.setAttribute("message", "CVC must be 3 or 4 digits.");
                    req.setAttribute("orderId", orderId);
                    req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                    return;
                }

                Date expiry;
                try {
                    expiry = Date.valueOf(expiryDateStr);
                } catch (Exception ex) {
                    req.setAttribute("message", "Invalid expiry date format.");
                    req.setAttribute("orderId", orderId);
                    req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                    return;
                }

                if (expiry.toLocalDate().isBefore(LocalDate.now())) {
                    req.setAttribute("message", "Card is expired.");
                    req.setAttribute("orderId", orderId);
                    req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                    return;
                }

                payment.setCardHolder(firstName + " " + lastName);
                payment.setCardNumber(cardNumber);
                payment.setCvc(cvc);
                payment.setExpiryDate(expiry);
                payment.setStatus("Paid");

            } else if ("Bank Transfer".equalsIgnoreCase(method)) {
                String bsb = req.getParameter("bsb");
                String accountName = req.getParameter("accountName");
                String accountNumber = req.getParameter("accountNumber");

                if (bsb == null || !bsb.matches("\\d{6}")) {
                    req.setAttribute("message", "BSB must be 6 digits.");
                    req.setAttribute("orderId", orderId);
                    req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                    return;
                }

                if (accountName == null || accountName.trim().isEmpty()) {
                    req.setAttribute("message", "Account name is required.");
                    req.setAttribute("orderId", orderId);
                    req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                    return;
                }

                if (accountNumber == null || !accountNumber.matches("\\d{6,10}")) {
                    req.setAttribute("message", "Account number must be 6 to 10 digits.");
                    req.setAttribute("orderId", orderId);
                    req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                    return;
                }

                payment.setBsb(bsb);
                payment.setAccountName(accountName);
                payment.setAccountNumber(accountNumber);
                payment.setStatus("Pending");
            }

            // Save
            paymentDao.save(payment, userId, orderId);


            resp.sendRedirect(req.getContextPath() + "/ViewPayment?orderId=" + orderId);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("message", "Payment failed. Please check your input.");
            req.setAttribute("orderId", req.getParameter("orderId"));
            req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
        }
    }
}
