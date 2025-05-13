package com.controller.PaymentController;

import com.bean.Customer;
import com.bean.Order;
import com.bean.Payment;
import com.bean.Product;
import com.dao.DBManager;
import com.dao.PaymentDao;
import com.dao.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
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

        try {
            Order order = (Order) session.getAttribute("order");
            if (order == null) {
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }

            Customer customer = (Customer) session.getAttribute("loggedInUser");
            List<Product> productList = order.getProducts();
            if (productList == null || productList.isEmpty()) {
                throw new IllegalStateException("Order has no products.");
            }

            Product product = productList.get(0);
            int quantity = product.getQuantity();
            int newStock = productDao.getProductById(product.getProductId()).getQuantity() - quantity;
            productDao.updateProductQuantity(product.getProductId(), newStock);

            Payment payment = new Payment();
            payment.setOrderId(order.getOrderId());
            payment.setUserId(customer.getUserId());
            payment.setMethod("CreditCard");

            // ✅ First Name + Last Name 조합
            String fullName = req.getParameter("firstName") + " " + req.getParameter("lastName");
            payment.setCardHolder(fullName);

            payment.setCardNumber(req.getParameter("cardNumber"));
            payment.setCvv(req.getParameter("cvv"));

            String expiryStr = req.getParameter("expiryDate");
            System.out.println(">>>> [expiryDate param received] = " + expiryStr);

            if (expiryStr == null || expiryStr.trim().isEmpty()) {
                throw new IllegalArgumentException("❌ expiryDate is empty or null.");
            }

            try {
                Date parsedDate = Date.valueOf(expiryStr);  // expects yyyy-MM-dd
                System.out.println(">>>> [expiryDate parsed successfully] = " + parsedDate);
                payment.setExpiryDate(parsedDate);
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Failed to parse expiryDate: " + expiryStr);
                throw new IllegalArgumentException("Invalid expiry date format.");
            }

            payment.setPaymentDate(new Date(System.currentTimeMillis()));

            BigDecimal totalAmount = BigDecimal.valueOf(product.getPrice())
                    .multiply(BigDecimal.valueOf(quantity));
            payment.setAmount(totalAmount);
            payment.setStatus("Paid");

            pd.save(payment, customer.getUserId(), order.getOrderId());
            session.removeAttribute("order");

            resp.sendRedirect(req.getContextPath() + "/ViewPayment?orderId=" + order.getOrderId());

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/views/PaymentError.jsp");
        }
    }
}
