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
        Customer currentCustomer = (Customer) session.getAttribute("loggedInUser");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        // if password field is empty, keep password as is
        // else check if password and confirmPassword match before updating
        if (password == null || confirmPassword == null || password.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
            password = currentCustomer.getPassword();
        } else if (!password.equals(confirmPassword)) {
            session.setAttribute("errorMessage", "Passwords do not match");
            resp.sendRedirect(req.getContextPath()+"/views/editUserDetails.jsp");
            return;
        }
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        Long phone = Long.valueOf(req.getParameter("phone"));
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String city = req.getParameter("city");
        String state = req.getParameter("state");
        Integer postcode = Integer.valueOf(req.getParameter("postcode"));
        String country = req.getParameter("country");

        Customer updatedCustomer = new Customer(username, password, firstName, lastName, phone, email, currentCustomer.getStatus(),
                address, city, state, postcode, country);
        updatedCustomer.setUserId(currentCustomer.getUserId());

        try {
            customerDao.updateCustomer(updatedCustomer);
            session.setAttribute("loggedInUser", updatedCustomer);
            resp.sendRedirect(req.getContextPath()+"/ViewUserDetailsServlet");
        } catch (SQLException e) {
            System.out.println("Could not update user");
            e.printStackTrace();
        }

    }
}
