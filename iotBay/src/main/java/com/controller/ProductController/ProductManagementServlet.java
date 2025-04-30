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

import java.util.List;


@WebServlet("/ProductManagementServlet")
public class ProductManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            HttpSession session = req.getSession();
            DBManager db = (DBManager) session.getAttribute("db");
            ProductDao productDao = db.getProductDao();

            List<Product> allProducts = productDao.getAllProducts();
            req.setAttribute("allProducts", allProducts); // set to request
            req.getRequestDispatcher("/views/ProductManagement.jsp").forward(req, resp); // forward to JSP

            System.out.println("Fetched products: " + allProducts.size()); //To check whether this code runs

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}