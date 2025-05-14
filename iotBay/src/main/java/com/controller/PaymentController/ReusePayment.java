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

import java.util.List;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/ReusePayment")
public class ReusePayment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao dao = db.getPaymentDao();
        OrderDao orderDao = db.getOrderDao();
        PaymentLogDao logDao = db.getPaymentLogDao();

        try {
            int paymentId = Integer.parseInt(req.getParameter("paymentId"));
            int orderId = Integer.parseInt(req.getParameter("orderId"));
            Customer customer = (Customer) session.getAttribute("loggedInUser");

            // Load the selected previous payment
            Payment existing = dao.getPaymentById(paymentId);
            if (existing == null) {
                // If the selected card doesn't exist, redirect to manual entry
                resp.sendRedirect(req.getContextPath() + "/views/AddPayment.jsp");
                return;
            }

            // Load the order and store it in session (used in other screens)
            Order order = orderDao.findOrderByOrderId(orderId);
            session.setAttribute("order", order);

            // Look for an existing pending payment for this order
            List<Payment> userPayments = dao.getPaymentsByUserId(customer.getUserId());
            Payment pending = null;
            for (Payment p : userPayments) {
                if (p.getOrderId() == orderId &&
                        p.getStatus() != null &&
                        p.getStatus().trim().equalsIgnoreCase("Pending")) {
                    pending = p;
                    break;
                }
            }

            // If no pending payment is found, redirect to new card entry
            if (pending == null) {
                resp.sendRedirect(req.getContextPath() + "/views/AddPayment.jsp");
                return;
            }

            // Update the pending payment with the selected saved card info
            pending.setCardHolder(existing.getCardHolder());
            pending.setCardNumber(existing.getCardNumber());
            pending.setCvv(existing.getCvv());
            pending.setExpiryDate(existing.getExpiryDate());
            pending.setPaymentDate(new Date(System.currentTimeMillis()));
            pending.setStatus("Paid");

            dao.update(pending);

            // Log the update action
            PaymentLog log = new PaymentLog();
            log.setPaymentId(pending.getPaymentId());
            log.setUserId(customer.getUserId());
            log.setOrderId(orderId);
            log.setAction("UPDATE");
            log.setTimestamp(new Date(System.currentTimeMillis()));
            logDao.log(log);

            session.removeAttribute("order");

            // Redirect to the ViewPayment page to confirm update
            resp.sendRedirect(req.getContextPath() + "/ViewPayment?orderId=" + orderId);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/views/AddPayment.jsp");
        }
    }
}
