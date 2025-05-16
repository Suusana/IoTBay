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

            int orderId = Integer.parseInt(req.getParameter("orderId"));
            String method = req.getParameter("paymentMethod");
            Order order = orderDao.findOrderByOrderId(orderId);


            if (order == null || order.getProducts() == null || order.getProducts().isEmpty() || order.getProducts().get(0).getPrice() == null) {
                req.setAttribute("message", "Order or product details are missing. Please try again.");
                req.setAttribute("orderId", orderId);
                req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                return;
            }

            BigDecimal totalAmount = order.getTotalAmount();


            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setUserId(customer.getUserId());
            payment.setAmount(totalAmount);
            payment.setMethod(method);
            payment.setPaymentDate(new Date(System.currentTimeMillis()));

            if ("Credit Card".equalsIgnoreCase(method)) {
                String firstName = req.getParameter("firstName");
                String lastName = req.getParameter("lastName");
                String cvc = req.getParameter("cvc");
                String expiryDate = req.getParameter("expiryDate");
                String cardNumber = String.join("",
                        req.getParameter("cardPart1"),
                        req.getParameter("cardPart2"),
                        req.getParameter("cardPart3"),
                        req.getParameter("cardPart4")
                );


                if (firstName == null || !firstName.matches("[A-Za-z]+") ||
                        lastName == null || !lastName.matches("[A-Za-z]+")) {
                    req.setAttribute("message", "Card holder name must contain only letters.");
                    req.setAttribute("orderId", orderId);
                    req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                    return;
                }

                if (cardNumber.length() != 16 || !cardNumber.matches("\\d{16}")) {
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

                if (expiryDate == null || LocalDate.parse(expiryDate).isBefore(LocalDate.now())) {
                    req.setAttribute("message", "Card is expired.");
                    req.setAttribute("orderId", orderId);
                    req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
                    return;
                }


                payment.setCardHolder(firstName + " " + lastName);
                payment.setCardNumber(cardNumber);
                payment.setCvc(cvc);
                payment.setExpiryDate(Date.valueOf(expiryDate));
                payment.setStatus("Paid");

            } else if ("Bank Transfer".equalsIgnoreCase(method)) {
                String bsb = req.getParameter("bsb");
                String accountName = req.getParameter("accountName");
                String accountNumber = req.getParameter("accountNumber");

                if (bsb == null || bsb.length() != 6 || !bsb.matches("\\d{6}")) {
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


            paymentDao.save(payment, customer.getUserId(), orderId);


            PaymentLog log = new PaymentLog();
            log.setPaymentId(payment.getPaymentId());
            log.setUserId(customer.getUserId());
            log.setOrderId(orderId);
            log.setAction("CREATE");
            log.setTimestamp(new Date(System.currentTimeMillis()));
            logDao.log(log);


            resp.sendRedirect(req.getContextPath() + "/ViewPayment?orderId=" + orderId);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Failed to process payment. Please try again.");
            req.setAttribute("orderId", req.getParameter("orderId"));
            req.getRequestDispatcher("/views/SelectPayment.jsp").forward(req, resp);
        }
    }
}
