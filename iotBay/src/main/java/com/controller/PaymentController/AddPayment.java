package com.controller.PaymentController;

import com.bean.Customer;
import com.bean.Order;
import com.bean.Payment;
import com.bean.PaymentLog;
import com.bean.Product;
import com.dao.DBManager;
import com.dao.PaymentDao;
import com.dao.PaymentLogDao;
import com.dao.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/AddPayment")
public class AddPayment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");

        PaymentDao pd = db.getPaymentDao();
        ProductDao productDao = db.getProductDao();
        PaymentLogDao logDao = db.getPaymentLogDao();

        try {
            int orderId = Integer.parseInt(req.getParameter("orderId"));
            Customer customer = (Customer) session.getAttribute("loggedInUser");
            Order order = db.getOrderDao().findOrderByOrderId(orderId);
            List<Product> productList = order.getProducts();

            if (productList == null || productList.isEmpty()) {
                throw new IllegalStateException("Order has no products.");
            }

            Product product = productList.get(0);
            int quantity = product.getQuantity();
            int newStock = productDao.getProductById(product.getProductId()).getQuantity() - quantity;
            productDao.updateProductQuantity(product.getProductId(), newStock);

            String fullName = req.getParameter("firstName") + " " + req.getParameter("lastName");
            String cardNumber = req.getParameter("cardNumber");
            String cvv = req.getParameter("cvv");
            String expiryStr = req.getParameter("expiryDate");

            if (expiryStr == null || expiryStr.trim().isEmpty()) {
                throw new IllegalArgumentException("expiryDate is empty or null.");
            }

            Date expiryDate = Date.valueOf(expiryStr);
            Date paymentDate = new Date(System.currentTimeMillis());
            BigDecimal totalAmount = BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(quantity));

            String pendingIdStr = req.getParameter("pendingPaymentId");
            if (pendingIdStr != null && !pendingIdStr.isEmpty()) {
                int pendingId = Integer.parseInt(pendingIdStr);
                Payment pending = pd.getPaymentById(pendingId);

                if (pending != null && "Pending".equalsIgnoreCase(pending.getStatus().trim())) {
                    pending.setStatus("Paid");
                    pending.setPaymentDate(paymentDate);
                    pd.update(pending);

                    PaymentLog updateLog = new PaymentLog();
                    updateLog.setPaymentId(pending.getPaymentId());
                    updateLog.setUserId(customer.getUserId());
                    updateLog.setOrderId(orderId);
                    updateLog.setAction("UPDATE");
                    updateLog.setTimestamp(new Date(System.currentTimeMillis()));
                    logDao.log(updateLog);
                }
            }

            Payment newPayment = new Payment();
            newPayment.setOrderId(orderId);
            newPayment.setUserId(customer.getUserId());
            newPayment.setMethod("CreditCard");
            newPayment.setCardHolder(fullName);
            newPayment.setCardNumber(cardNumber);
            newPayment.setCvv(cvv);
            newPayment.setExpiryDate(expiryDate);
            newPayment.setPaymentDate(paymentDate);
            newPayment.setAmount(totalAmount);
            newPayment.setStatus("Paid");

            pd.save(newPayment, customer.getUserId(), orderId);

            PaymentLog createLog = new PaymentLog();
            createLog.setPaymentId(newPayment.getPaymentId());
            createLog.setUserId(customer.getUserId());
            createLog.setOrderId(orderId);
            createLog.setAction("CREATE");
            createLog.setTimestamp(new Date(System.currentTimeMillis()));
            logDao.log(createLog);

            resp.sendRedirect(req.getContextPath() + "/ViewPayment?orderId=" + orderId);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "An error occurred while processing payment.");
            req.getRequestDispatcher("/views/AddPayment.jsp").forward(req, resp);
        }
    }
}
