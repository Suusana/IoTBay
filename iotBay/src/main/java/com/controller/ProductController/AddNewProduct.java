package com.controller.ProductController;

import com.bean.Category;
import com.bean.Product;
import com.dao.CategoryDao;
import com.dao.DBManager;
import com.dao.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
@MultipartConfig
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
        //String image = req.getParameter("image");
        String appPath=req.getServletContext().getRealPath("/");
        String uploadPath = appPath+ File.separator+"assets"+File.separator+"img";

        Part getFormImg = req.getPart("image");
        String imgName = Paths.get(getFormImg.getSubmittedFileName()).getFileName().toString();


        int categoryId = Integer.parseInt(req.getParameter("categoryId"));

       // product.setProductId(productId);
        product.setProductName(productName);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setDescription(description);
        if(imgName != null && !imgName.isEmpty()){
            getFormImg.write(uploadPath+File.separator+imgName);
            product.setImage(imgName);
        }else{
            product.setImage("");
        }
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
