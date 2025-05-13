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
import java.util.List;

@WebServlet("/ViewPayment")
public class ViewPayment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao dao = db.getPaymentDao();

        String orderIdStr = req.getParameter("orderId");

        try {
            List<Payment> payments;
            int orderId = -1;

            if (orderIdStr != null && !orderIdStr.isEmpty()) {
                orderId = Integer.parseInt(orderIdStr);
                payments = dao.getPaymentsByOrderId(orderId);
                req.setAttribute("orderId", orderId);
            } else {
                payments = dao.getAllPayments();
            }

            req.setAttribute("paymentList", payments);
            req.getRequestDispatcher("/views/ViewPayment.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid orderId");
        } catch (SQLException e) {
            throw new ServletException("Error retrieving payments", e);
        }
    }
}
