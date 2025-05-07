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

@WebServlet("/AddNewProduct")
public class AddNewProduct extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        ProductDao pd = db.getProductDao();

        Product product = new Product();

        // int productId = Integer.parseInt(req.getParameter("productId"));
        String productName = req.getParameter("productName");
        double price = Double.parseDouble(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String description = req.getParameter("description");
        String image = req.getParameter("image");
        int categoryId = Integer.parseInt(req.getParameter("categoryId"));

       // product.setProductId(productId);
        product.setProductName(productName);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setDescription(description);
        product.setImage(image);
        try{
            CategoryDao cd = db.getCategoryDao();
            Category category = cd.getCategoryById(categoryId);
            product.setCategory(category);
        }catch(SQLException e){
            System.out.println("Category error detected");
            e.printStackTrace();
        }
        //This is for the test
        //('Capacitive Touch Sensor Module v2.0', 30, 18.90,
        // 'Capacitive touch sensor module used for detecting touch input in Arduino, Raspberry Pi, and interactive projects.',
        // 'Touch Sensor.png', 1),
        //
        try {
            pd.createProduct(product);
            resp.sendRedirect(req.getContextPath() + "/ProductManagementServlet");
        } catch (SQLException e) {
            System.out.println("Failed to add a new Product");
        }
    }
}
