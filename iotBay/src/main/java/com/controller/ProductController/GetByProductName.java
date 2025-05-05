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

@WebServlet("/GetByProductName")
public class GetByProductName extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
        try{
            HttpSession session = req.getSession();
            DBManager db = (DBManager) session.getAttribute("db");
            ProductDao productDao = db.getProductDao();

            String productName = req.getParameter("productName");
            Product product = productDao.getProductByName(productName);
            if (product != null) {
                req.setAttribute("product", product);
                req.getRequestDispatcher("/views/AdminProductSearchResult.jsp").forward(req, resp);
            } else{
                req.setAttribute("message", "404 NotFound");
                req.getRequestDispatcher("/views/AdminProductSearchResult.jsp").forward(req,resp);
            }

        } catch(SQLException | IOException e){
            System.out.println("Failed to load a product");
            throw new ServletException(e);
        }

    }
}

