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

@WebServlet("/AddCustomer")
public class AddCustomerHandler extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();

        Customer customer = new Customer();
        customer.setUsername(req.getParameter("name"));
        customer.setEmail(req.getParameter("email"));
        customer.setStatus("Active");
        customer.setAddress(req.getParameter("address"));

        try {
            customerDao.addUser(customer);
            resp.sendRedirect(req.getContextPath() + "/ShowCustomerInfo");
        } catch (SQLException e) {
            System.out.println("Failed to create customer: " + e.getMessage());
        }
    }
}
