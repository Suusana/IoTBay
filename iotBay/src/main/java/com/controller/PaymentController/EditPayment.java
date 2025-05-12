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
import java.sql.SQLException;

@WebServlet("/EditPayment")
public class EditPayment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int paymentId = Integer.parseInt(req.getParameter("paymentId"));
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao dao = db.getPaymentDao();

        try {
            Payment payment = dao.getPaymentById(paymentId);
            req.setAttribute("payment", payment);
            req.getRequestDispatcher("/views/EditPayment.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Failed to load payment", e);
        }
    }
}
