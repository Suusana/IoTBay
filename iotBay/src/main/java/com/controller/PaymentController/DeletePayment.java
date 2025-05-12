package com.controller.PaymentController;

import com.dao.DBManager;
import com.dao.PaymentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeletePayment")
public class DeletePayment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int paymentId = Integer.parseInt(req.getParameter("paymentId"));
        int orderId = Integer.parseInt(req.getParameter("orderId"));

        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao dao = db.getPaymentDao();

        try {
            dao.delete(paymentId);
            resp.sendRedirect(req.getContextPath() + "/ViewPayments?orderId=" + orderId);
        } catch (SQLException e) {
            throw new ServletException("Failed to delete payment", e);
        }
    }
}