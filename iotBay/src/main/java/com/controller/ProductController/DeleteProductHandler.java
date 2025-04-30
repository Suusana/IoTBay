package com.controller.ProductController;

import com.dao.DBManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.dao.ProductDao;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@WebServlet("/DeleteProduct")
public class DeleteProductHandler extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            DBManager db = (DBManager) session.getAttribute("db");
            ProductDao pd = db.getProductDao();

            int productId = Integer.parseInt(req.getParameter("productId"));
            pd.deleteProduct(productId);

            resp.sendRedirect(req.getContextPath() +"ProductManagement.jsp");
        } catch (SQLException e) {
            System.out.println("Failed to delete product");
        }
    }
}
