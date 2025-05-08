package com.controller.ProductController;

import com.bean.Product;
import com.dao.DBManager;
import com.dao.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/ProductDetailServlet")
public class ProductDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        ProductDao productDao = db.getProductDao();

        Integer productId = Integer.valueOf(req.getParameter("id"));
        try {
            Product product = productDao.getProductById(productId);
            req.setAttribute("product", product);
            req.getRequestDispatcher( "/views/productDetail.jsp").forward(req, resp);
        } catch (SQLException e) {
            System.out.println("Cannot get the product");
        }
    }
}

