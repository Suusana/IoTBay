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

@WebServlet("/UpdateCustomer")
public class UpdateCustomerHandler extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();

        Customer customer = new Customer();
        customer.setUserId(Integer.parseInt(req.getParameter("customerId")));
        customer.setUsername(req.getParameter("username"));
        customer.setPassword(req.getParameter("password"));
        customer.setFirstName(req.getParameter("firstName"));
        customer.setLastName(req.getParameter("lastName"));
        customer.setPhone(Long.parseLong(req.getParameter("phone_num")));
        customer.setType(req.getParameter("type"));
        customer.setEmail(req.getParameter("email"));
        customer.setAddress(req.getParameter("address"));
        customer.setCity(req.getParameter("city"));
        customer.setPostcode(Integer.parseInt(req.getParameter("postcode")));
        customer.setState(req.getParameter("state"));
        customer.setCountry(req.getParameter("country"));

        try {
            customerDao.updateCustomer(customer);
            resp.sendRedirect(req.getContextPath() + "/ShowCustomerInfo");
        } catch (SQLException e) {
            System.out.println("Failed to update a customer");
        }
    }
}
