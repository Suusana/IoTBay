package com.controller;

import com.bean.Product;
import com.dao.DBConnector;
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;


@WebServlet("/ProductManagementServlet")
public class ProductManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            //change the dp path to -> "jdbc:sqlite:/absolute/path/to/your/database.db"
            Connection connection = DriverManager.getConnection("jdbc:sqlite:/Users/yunseo/.SmartTomcat/IoTBay/IoTBay/IoTBayDB.db");
            ProductDao productDao = new ProductDao(connection);

            List<Product> allProducts = productDao.getAllProducts();
            req.setAttribute("allProducts", allProducts); // set to request
            req.getRequestDispatcher("/views/ProductManagement.jsp").forward(req, resp); // forward to JSP

            System.out.println("Fetched products: " + allProducts.size()); //To check whether this code runs

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}