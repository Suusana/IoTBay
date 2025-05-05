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

import java.sql.Connection;
import java.sql.DriverManager;
//This is for views/shop.jsp page. It shows list of device record to customers
@WebServlet("/productServlet")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(); //detect current HTTP session
            DBManager db = (DBManager) session.getAttribute("db"); //able to use productdao
            ProductDao pd = db.getProductDao();

            List<Product> allProducts = pd.getAllProducts();
            req.setAttribute("allProducts", allProducts); // set to request
            req.getRequestDispatcher("/views/shop.jsp").forward(req, resp); // forward to JSP

            System.out.println("Fetched products: " + allProducts.size()); //To check whether this code runs

        } catch (SQLException e) {
            System.out.println("Failed to load products");
            throw new ServletException(e);
        }
    }
}
