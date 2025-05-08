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

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        Long phone = Long.valueOf(req.getParameter("phone"));
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String city = req.getParameter("city");
        String state = req.getParameter("state");
        Integer postcode = Integer.valueOf(req.getParameter("postcode"));
        String country = req.getParameter("country");

        Customer currentCustomer = (Customer) session.getAttribute("loggedInUser");
        Customer newCustomer = new Customer(username, password, firstName, lastName, phone, email, currentCustomer.getStatus(),
                address, city, state, postcode, country);
        newCustomer.setUserId(currentCustomer.getUserId());

        try {
            customerDao.updateCustomer(newCustomer);
            session.setAttribute("loggedInUser", newCustomer);
            resp.sendRedirect(req.getContextPath()+"/ViewUserDetailsServlet");
        } catch (SQLException e) {
            System.out.println("Could not update user");
            e.printStackTrace();
        }

    }
}
