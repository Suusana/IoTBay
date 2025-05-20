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

@WebServlet("/GetByCategoryAndNameToCustomer")
public class GetbyCategoryAndNameToCustomer extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try{
            HttpSession session = req.getSession();
            DBManager db = (DBManager) session.getAttribute("db");
            ProductDao pd = db.getProductDao();
            CategoryDao cd = db.getCategoryDao();

            String searchName = req.getParameter("productName");
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            Category category = cd.getCategoryById(categoryId);
            System.out.println("Searching for: " + searchName + ", categoryId: " + categoryId);
            List<Product> products = pd.getProductByCategory_searchName(searchName, categoryId);

            if (products != null) {
                System.out.println("c_products size: " + products.size());

                req.setAttribute("c_products", products);
                req.setAttribute("category", category.getCategory());
                req.setAttribute("searchBtnCategory", true);
                req.getRequestDispatcher("/views/SearchByCategory.jsp").forward(req, resp);
            } else {
                req.setAttribute("message", "404 NotFound");
                req.setAttribute("category", category.getCategory());
                req.setAttribute("searchBtnCategory", true);
                req.getRequestDispatcher("/views/SearchByCategory.jsp").forward(req, resp);
            }
            req.setAttribute("searchBtnCategory", false);
        }catch (SQLException | IOException e){
            e.printStackTrace();
        }
    }

}
