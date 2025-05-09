package com.controller.OrderController;

import com.bean.Customer;
import com.bean.Order;
import com.bean.Product;
import com.dao.DBManager;
import com.dao.OrderDao;
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
        int userId;

        // ✅ 获取 userId（guest 或登录）
        Customer customer = (Customer) session.getAttribute("loggedInUser");
        if (customer == null) {
            if (session.getAttribute("guestId") == null) {
                int guestId = -Math.abs(session.getId().hashCode());
                session.setAttribute("guestId", guestId);
            }
            userId = (int) session.getAttribute("guestId");
        } else {
            userId = customer.getUserId();
        }

        try {
            // ✅ 获取参数
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String action = request.getParameter("action");

            // ✅ 非法数量处理
            if (quantity <= 0) {
                request.setAttribute("error", "Quantity must be greater than 0.");
                forwardBackToProduct(request, response, productId, quantity);
                return;
            }

            DBManager db = (DBManager) session.getAttribute("db");
            Product product = db.getProductDao().findProductById(productId);

            if (product == null) {
                request.setAttribute("error", "Product not found.");
                response.sendRedirect(request.getContextPath() + "/productServlet");
                return;
            }

            int stock = product.getQuantity();
            if (quantity > stock) {
                request.setAttribute("error", "Not enough stock. Only " + stock + " item(s) available.");
                forwardBackToProduct(request, response, productId, quantity);
                return;
            }

            // ✅ 设置订单状态
            OrderStatus status = "Submit".equalsIgnoreCase(action)
                    ? OrderStatus.Confirmed
                    : OrderStatus.Saved;

            // ✅ 构建订单对象
            Order order = new Order();
            order.setOrderStatus(status);
            order.setCreateDate(new Timestamp(System.currentTimeMillis()));

            Product orderedProduct = new Product();
            orderedProduct.setProductId(productId);
            orderedProduct.setQuantity(quantity);

            List<Product> productList = new ArrayList<>();
            productList.add(orderedProduct);
            order.setProducts(productList);

            OrderDao orderDao = new OrderDao(db.getConnection());
            orderDao.saveOrder(order, userId);

            // ✅ 提交时更新库存
            if (status == OrderStatus.Confirmed) {
                int newStock = stock - quantity;
                db.getProductDao().updateProductQuantity(productId, newStock);
            }

            response.sendRedirect(request.getContextPath() + "/viewOrder");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/productServlet");
        }
    }

    // ✅ 转发回 productDetail 页面（含错误和回显数量）
    private void forwardBackToProduct(HttpServletRequest request, HttpServletResponse response, int productId, int quantity)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        try {
            Product product = db.getProductDao().findProductById(productId);
            if (product != null) {
                request.setAttribute("product", product);
                request.setAttribute("quantity", quantity); // ✅ 回显数量
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/views/productDetail.jsp").forward(request, response);
    }
}
