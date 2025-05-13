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
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/UpdatePayment")
public class UpdatePayment extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao dao = db.getPaymentDao();

        try {
            String idParam = req.getParameter("paymentId");

            if (idParam == null || idParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Missing paymentId");
            }

            int paymentId = Integer.parseInt(idParam);

            // 기존 결제 정보 가져오기
            Payment payment = dao.getPaymentById(paymentId);
            if (payment == null) {
                req.setAttribute("error", "Payment not found.");
                req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                return;
            }

            // 입력 값 반영
            payment.setCardHolder(req.getParameter("cardHolder"));
            payment.setCardNumber(req.getParameter("cardNumber"));
            payment.setCvv(req.getParameter("cvv"));

            String expiryParam = req.getParameter("expiryDate");
            if (expiryParam == null || expiryParam.trim().isEmpty()) {
                req.setAttribute("error", "Expiry date is missing.");
                req.setAttribute("payment", payment);
                req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                return;
            }

            try {
                payment.setExpiryDate(Date.valueOf(expiryParam));
            } catch (IllegalArgumentException e) {
                req.setAttribute("error", "Invalid expiry date format.");
                req.setAttribute("payment", payment);
                req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
                return;
            }

            // 기본값 보완
            if (payment.getAmount() == null)
                payment.setAmount(new java.math.BigDecimal("0.00"));
            if (payment.getPaymentDate() == null)
                payment.setPaymentDate(new Date(System.currentTimeMillis()));
            if (payment.getMethod() == null)
                payment.setMethod("CreditCard");
            if (payment.getStatus() == null)
                payment.setStatus("Paid");

            // 업데이트 실행
            dao.update(payment);

            resp.sendRedirect(req.getContextPath() + "/ViewPayment?orderId=" + payment.getOrderId());

        } catch (NumberFormatException e) {
            e.printStackTrace();
            req.setAttribute("error", "Invalid number format: " + e.getMessage());
            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);

        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error: " + e.getMessage());
            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
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
