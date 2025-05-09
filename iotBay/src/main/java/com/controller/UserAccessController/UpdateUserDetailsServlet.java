package com.controller.UserAccessController;

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

@WebServlet("/UpdateUserDetailsServlet")
public class UpdateUserDetailsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db =  (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();

        Customer customer = new Customer();
        customer.setUsername(req.getParameter("username"));
        customer.setPassword(req.getParameter("password"));
        customer.setFirstName(req.getParameter("firstName"));
        customer.setLastName(req.getParameter("lastname"));
        customer.setPhone(Long.valueOf(req.getParameter("phone_num")));
        customer.setEmail(req.getParameter("email"));
        customer.setAddress(req.getParameter("address"));
        customer.setCity(req.getParameter("city"));
        customer.setState(req.getParameter("state"));
        customer.setPostcode(Integer.valueOf(req.getParameter("postcode")));
        customer.setCountry(req.getParameter("country"));
        customer.setType(req.getParameter("type"));
        customer.setUserId(Integer.valueOf(req.getParameter("customerId")));

        try {
            customerDao.updateCustomer(customer);
            resp.sendRedirect(req.getContextPath()+"/ViewUserDetailsServlet");
        } catch (SQLException e) {
            System.out.println("Could not update user");
        }
    }
}
