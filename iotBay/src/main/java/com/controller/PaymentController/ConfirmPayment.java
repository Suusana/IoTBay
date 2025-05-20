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

            int orderId = Integer.parseInt(req.getParameter("orderId"));
            String method = req.getParameter("paymentMethod");
            Order order = orderDao.findOrderByOrderId(orderId);

            if (order == null || order.getProducts() == null || order.getProducts().isEmpty()) {
                req.setAttribute("message", "Order details missing.");
                req.setAttribute("orderId", orderId);
                req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                return;
            }

            BigDecimal totalAmount = order.getTotalAmount();

            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setAmount(totalAmount);
            payment.setMethod(method);
            payment.setPaymentDate(new Date(System.currentTimeMillis()));

            // Determine user ID (0 = guest)
            payment.setUserId(customer != null ? customer.getUserId() : 0);

            if ("Credit Card".equalsIgnoreCase(method)) {
                String firstName = req.getParameter("firstName");
                String lastName = req.getParameter("lastName");
                String cardNumber = String.join("",
                        req.getParameter("cardPart1"),
                        req.getParameter("cardPart2"),
                        req.getParameter("cardPart3"),
                        req.getParameter("cardPart4")
                );
                String cvc = req.getParameter("cvc");
                String expiryDate = req.getParameter("expiryDate");

                payment.setCardHolder(firstName + " " + lastName);
                payment.setCardNumber(cardNumber);
                payment.setCvc(cvc);
                payment.setExpiryDate(Date.valueOf(expiryDate));
                payment.setStatus("Paid");

            } else if ("Bank Transfer".equalsIgnoreCase(method)) {
                payment.setBsb(req.getParameter("bsb"));
                payment.setAccountName(req.getParameter("accountName"));
                payment.setAccountNumber(req.getParameter("accountNumber"));
                payment.setStatus("Pending");
            }

            // Save payment
            paymentDao.save(payment, payment.getUserId(), orderId);

            // Log action
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

            // Choose correct confirmation screen
            String guestEmail = req.getParameter("guestEmail");

            // Use username pattern to detect guest
            boolean isGuestUser = customer == null ||
                    (customer.getUsername() != null && customer.getUsername().startsWith("Guest"));

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
