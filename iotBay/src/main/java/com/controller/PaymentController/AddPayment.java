package com.controller.PaymentController;

import com.bean.Customer;
import com.bean.Order;
import com.bean.Payment;
import com.bean.Product;
import com.dao.DBManager;
import com.dao.OrderDao;
import com.dao.PaymentDao;
import com.dao.ProductDao;
import com.enums.OrderStatus;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AddPayment")
public class AddPayment extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");

        PaymentDao pd = db.getPaymentDao();
        ProductDao productDao = db.getProductDao();
        OrderDao orderDao = db.getOrderDao();

        try {
            int productId = Integer.parseInt(req.getParameter("productId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            Customer customer = (Customer) session.getAttribute("loggedInUser");

            Product product = productDao.getProductById(productId);

            Order order = new Order();
            order.setOrderStatus(OrderStatus.Confirmed);
            order.setCreateDate(new Timestamp(System.currentTimeMillis()));
            order.setQuantity(quantity);

            List<Product> productList = new ArrayList<>();
            productList.add(product);
            order.setProducts(productList);

            orderDao.saveOrder(order, customer.getUserId());
            int orderId = order.getOrderId();

            int newStock = product.getQuantity() - quantity;
            productDao.updateProductQuantity(productId, newStock);

            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setUserId(customer.getUserId());
            payment.setMethod("CreditCard");
            payment.setCardHolder(req.getParameter("cardHolder"));
            payment.setCardNumber(req.getParameter("cardNumber"));
            payment.setCvv(req.getParameter("cvv"));

            payment.setExpiryDate(Date.valueOf(req.getParameter("expiryDate"))); // assuming Payment.expiryDate is Date
            payment.setPaymentDate(new Date(System.currentTimeMillis()));
            payment.setAmount(BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(quantity)));
            payment.setStatus("Paid");

            pd.save(payment, customer.getUserId(), orderId);
            resp.sendRedirect(req.getContextPath() + "/ViewPayments?orderId=" + orderId);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/views/PaymentError.jsp");
        }
    }
}
