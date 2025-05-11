package com.controller.PaymentController;

import com.bean.*;
import com.bean.Payment;
import com.dao.*;
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AddPayment")
public class AddPaymentsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        PaymentDao pd = db.getPaymentDao();
        ProductDao productDao = db.getProductDao();
        OrderDao orderDao = db.getOrderDao();

        Payment payment = new Payment();
        payment.setMethod(req.getParameter("paymentMethod"));
        payment.setAccountName(req.getParameter("accountName"));
        payment.setBSB(Integer.valueOf(req.getParameter("bsb")));
        payment.setAccountNum(Integer.valueOf(req.getParameter("accountNumber")));

        try {
            int productId = Integer.parseInt(req.getParameter("productId"));
//            int orderId = Integer.parseInt(req.getParameter("orderId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            Customer customer = (Customer) session.getAttribute("loggedInUser");

            Product product = productDao.getProductById(productId);

            Order order = new Order();
            order.setOrderStatus(OrderStatus.Confirmed);
            order.setCreateDate(new Timestamp(System.currentTimeMillis()));

            Product orderedProduct = new Product();
            orderedProduct.setProductId(product.getProductId());
            orderedProduct.setQuantity(quantity);

            List<Product> productList = new ArrayList<>();
            productList.add(orderedProduct);
            order.setProducts(productList);

//            pd.save(payment, customer.getUserId(), orderId);
            productDao.updateProductQuantity(productId, quantity);
            orderDao.saveOrder(order, customer.getUserId());
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/views/PaymentError.jsp");
        }
    }
}
