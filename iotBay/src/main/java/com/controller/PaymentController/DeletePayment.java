package com.controller.PaymentController;

import com.bean.Payment;
import com.dao.DBManager;
import com.dao.PaymentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeletePayment")
public class DeletePayment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao dao = db.getPaymentDao();

        String paymentIdStr = req.getParameter("paymentId");

        if (paymentIdStr == null || paymentIdStr.trim().isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing paymentId");
            return;
        }

        try {
            int paymentId = Integer.parseInt(paymentIdStr);
            Payment payment = dao.getPaymentById(paymentId);

            if (payment == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Payment not found");
                return;
            }

            int orderId = payment.getOrderId();
            dao.delete(paymentId);

            // ✅ 삭제 후 ViewPayment로 리디렉트 (그대로 유지)
            resp.sendRedirect(req.getContextPath() + "/ViewPayment?orderId=" + orderId);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid paymentId");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error during deletion");
        }
    }
}
