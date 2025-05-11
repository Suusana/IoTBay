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

@WebServlet("/GetByCategoryToCustomer")
public class GetByCategoryToCustomer extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try{
            HttpSession session = req.getSession();
            DBManager db = (DBManager) session.getAttribute("db");
            ProductDao productDao = db.getProductDao();

            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            List<Product> products= productDao.getProductByCategory(categoryId);
            CategoryDao cd = db.getCategoryDao();
            Category category = cd.getCategoryById(categoryId);


            if (products != null) {
                req.setAttribute("products", products);
                req.setAttribute("category", category.getCategory());
                req.getRequestDispatcher("/views/SearchByCategory.jsp").forward(req, resp);
            } else {
                req.setAttribute("message", "404 NotFound");
                req.setAttribute("category", category.getCategory());
                req.getRequestDispatcher("/views/SearchByCategory.jsp").forward(req, resp);
            }

        } catch(SQLException | IOException e){
            System.out.println("Failed to load a product");
            throw new ServletException(e);
        }

    }
}
