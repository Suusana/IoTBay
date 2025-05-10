package com.controller.PaymentController;

import com.bean.Invoice;
import com.bean.Payment;
import com.dao.DBManager;
import com.dao.InvoiceDao;
import com.dao.PaymentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/AddPayment")
public class AddPaymentsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao pd = db.getPaymentDao();
        InvoiceDao invoiceDao = db.getInvoiceDao();

        Payment payment = new Payment();

        int orderId = Integer.parseInt(req.getParameter("orderId"));
        String method = req.getParameter("method");
        String cardHolder = req.getParameter("cardHolder");
        String cardNumber = req.getParameter("cardNumber");
        Date expiryDate = Date.valueOf(req.getParameter("expiryDate"));
        Date paymentDate = new Date(System.currentTimeMillis());
        String status = "Saved";

        payment.setOrderId(orderId);
        payment.setMethod(method);
        payment.setCardHolder(cardHolder);
        payment.setCardNumber(cardNumber);
        payment.setExpiryDate(expiryDate);
        payment.setPaymentDate(paymentDate);
        payment.setStatus(status);

        try {
            Invoice invoice = invoiceDao.findInvoiceByOrderId(orderId);
            if (invoice != null) {
                payment.setAmount(BigDecimal.valueOf(invoice.getTotalPrice()));
            }

            pd.save(payment);
            resp.sendRedirect(req.getContextPath() + "/ViewPayments?orderId=" + payment.getOrderId());
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/views/PaymentError.jsp");
        }
    }
}
