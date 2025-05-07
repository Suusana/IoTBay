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

@WebServlet("/DeleteCustomer")
public class DeleteCustomerHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();

        int id = Integer.parseInt(req.getParameter("customerId"));
        try {
            Customer customer = customerDao.getUserById(id);
            customerDao.deleteUser(customer);
            resp.sendRedirect(req.getContextPath() + "/ShowCustomerInfo");
        } catch (SQLException e) {
            System.out.println("Failed to delete customer");
        }
    }
}
