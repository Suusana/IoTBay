package com.controller.OrderController;

import com.bean.Customer;
import com.bean.Order;
import com.bean.Product;
import com.dao.DBManager;
import com.dao.OrderDao;
import com.dao.ProductDao;
import com.enums.OrderStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/createOrder")
public class CreateOrder extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        OrderDao orderDao = db.getOrderDao();
        ProductDao productDao = db.getProductDao();

        // get userID from session
        Customer customer = (Customer) session.getAttribute("loggedInUser");

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String action = request.getParameter("action");

            if (quantity <= 0) {
                request.setAttribute("error", "Quantity must be greater than 0.");
                forwardBackToProduct(request, response, productId, quantity);
                return;
            }

            Product product = productDao.getProductById(productId);
            int stock = product.getQuantity();

            if (quantity > stock) {
                request.setAttribute("error", "Not enough stock. Only " + stock + " item(s) available.");
                forwardBackToProduct(request, response, productId, quantity);
                return;
            }

            // only two buttons: Submit or Save, change Order status
            OrderStatus status = "Submit".equalsIgnoreCase(action)
                    ? OrderStatus.Confirmed
                    : OrderStatus.Saved;

            Order order = new Order();
            order.setOrderStatus(status);
            order.setCreateDate(new Timestamp(System.currentTimeMillis()));

            // Copy all product details into the orderedProduct object
            Product orderedProduct = new Product();
            orderedProduct.setProductId(product.getProductId());
            orderedProduct.setProductName(product.getProductName());
            orderedProduct.setPrice(product.getPrice());
            orderedProduct.setDescription(product.getDescription());
            orderedProduct.setQuantity(quantity);

            List<Product> productList = new ArrayList<>();
            productList.add(orderedProduct);
            order.setProducts(productList);
            order.setQuantity(quantity);

            // Save the order in DB
            orderDao.saveOrder(order, customer.getUserId());

            // Save order before proceeding to payment
            // Store order and product in session so they can be accessed in ConfirmPayment.jsp
            session.setAttribute("order", order);
            session.setAttribute("product", product);

            if (status == OrderStatus.Confirmed) {
                // update stock
                productDao.updateProductQuantity(productId, quantity);
                product.setQuantity(stock - quantity);
                session.setAttribute("product", product);

                // Redirect instead of forward to update browser address bar and prevent duplicate POST
                //response.sendRedirect(request.getContextPath() + "/views/ConfirmPayment.jsp");
                response.sendRedirect(request.getContextPath() + "/SelectPayment?orderId=" + order.getOrderId());

            } else {
                // If status is Saved, just redirect to order list
                response.sendRedirect(request.getContextPath() + "/viewOrder");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }

    // If stock is less than the quantity the user wants, go back to product detail page
    // Also show available stock
    private void forwardBackToProduct(HttpServletRequest request, HttpServletResponse response, int productId, int quantity)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        DBManager db = (DBManager) session.getAttribute("db");

        try {
            Product product = db.getProductDao().getProductById(productId);
            if (product != null) {
                request.setAttribute("product", product);
                request.setAttribute("quantity", quantity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/views/productDetail.jsp").forward(request, response);
    }
}
