package com.controller.ProductController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.dao.ProductDao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@WebServlet("/DeleteProduct")
public class DeleteProductHandler extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/yunseo/.SmartTomcat/IoTBay/IoTBay/IoTBayDB.db");
            ProductDao productDao = new ProductDao(connection);

            int productId = Integer.parseInt(req.getParameter("productId"));
            productDao.deleteProduct(productId);

            resp.sendRedirect(req.getContextPath() +"ProductManagement.jsp");
        } catch (SQLException e) {
            System.out.println("Failed to delete product");
        }
    }
}
