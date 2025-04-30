package com.controller;

import com.bean.Category;
import com.bean.Product;
import com.dao.CategoryDao;
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
import java.util.List;

@WebServlet("/home")
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        ProductDao productDao = db.getProductDao();
        CategoryDao categoryDao = db.getCategoryDao();

        try {
            // get banner info
            Product banner = productDao.getLastProduct();
            req.setAttribute("banner", banner);

            // get product list(8)
            List<Product> productList = productDao.getEightProducts();
            req.setAttribute("productList", productList);

            // get 3 categories
            List<Category> categories = categoryDao.get3Categories();
            req.setAttribute("categories", categories);
        } catch (SQLException e) {
            System.out.println("Failed to get data information");
        }
        req.getRequestDispatcher("views/main.jsp").forward(req, resp);
    }
}
