package com.controller.ProductController;

import com.bean.Product;
import com.bean.Category;
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

@WebServlet("/UpdateProductServlet")
public class UpdateProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        ProductDao productDao = db.getProductDao();

        Integer productId = Integer.parseInt(req.getParameter("productId"));
        try {
            Product product = productDao.getProductById(productId);
            req.setAttribute("product", product);
            req.getRequestDispatcher( "/views/AdminProductUpdate.jsp").forward(req, resp);
        } catch (SQLException e) {
            System.out.println("Cannot get the product");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            DBManager db = (DBManager) session.getAttribute("db");
            ProductDao pd = db.getProductDao();

            Product product = new Product();

            int productId = Integer.parseInt(req.getParameter("productId"));
            String productName = req.getParameter("productName");
            double price = Double.parseDouble(req.getParameter("price"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            String description = req.getParameter("description");
            String image = req.getParameter("image");
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));

            product.setProductId(productId);
            product.setProductName(productName);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setDescription(description);
            product.setImage(image);
            //product db stores categoryId but product class stores string(category name)
            //So get CategoryName using categoryId from category db.
            CategoryDao cd = db.getCategoryDao();
            Category category = cd.getCategoryById(categoryId);
            product.setCategory(category);

            pd.updateProduct(product);

            resp.sendRedirect(req.getContextPath() + "/ProductManagementServlet");
        } catch (SQLException e) {
            System.out.println("Failed to update a Product");
            e.printStackTrace();
        }
    }
}
