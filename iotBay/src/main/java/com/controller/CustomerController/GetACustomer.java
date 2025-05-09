
package com.controller.CustomerController;

import com.bean.Customer;
import com.dao.CustomerDao;
import com.dao.DBManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/showCustomer")
public class GetACustomer extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();

        int id = Integer.parseInt(req.getParameter("customerId"));
        try {
            Customer customer = customerDao.getUserById(id);
            if (customer != null) {
                req.setAttribute("customer", customer);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get customer by id");
        }

        String view = req.getParameter("view");
        if (view != null) {
            req.getRequestDispatcher("views/CustomerDetail.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("views/UpdateCustomer.jsp").forward(req, resp);
        }
    }
}
