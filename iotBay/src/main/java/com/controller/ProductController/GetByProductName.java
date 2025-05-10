package com.controller.ProductController;

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

@WebServlet("/GetByProductName")
public class GetByProductName extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException{
        try{
            HttpSession session = req.getSession();
            DBManager db = (DBManager) session.getAttribute("db");
            ProductDao productDao = db.getProductDao();

            String productName = req.getParameter("productName");
            List<Product> searchAllProducts = productDao.getProductByNameLike(productName);
//            if (product != null) {
//                req.setAttribute("product", product);
//                req.getRequestDispatcher("/views/AdminProductSearchResult.jsp").forward(req, resp);
//            } else{
//                req.setAttribute("message", "404 NotFound");
//                req.getRequestDispatcher("/views/AdminProductSearchResult.jsp").forward(req,resp);
//            }
            req.setAttribute("searchAllProducts", searchAllProducts); // set to request
            req.getRequestDispatcher("/views/AdminProductSearchResult.jsp").forward(req, resp); // forward to JSP
            System.out.println("Fetched products: " + searchAllProducts.size()); //To check whether this code runs

        } catch(SQLException | IOException e){
            req.setAttribute("error", "Not Found");
            System.out.println("Failed to load a product");
            throw new ServletException(e);
        }

    }
}

