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

@WebServlet("/EditUserDetailsServlet")
public class EditUserDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db =  (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();
        Customer customer = (Customer) session.getAttribute("loggedInUser");

        if (customer == null) {
            session.setAttribute("errorMessage", "Please login to edit your profile");
            resp.sendRedirect(req.getContextPath()+"/login.jsp");
            return;
        }

        Customer updatedCustomer;
        try {
            // update customer to latest data from database before displaying
            updatedCustomer = customerDao.getUserById(customer.getUserId());
            session.setAttribute("loggedInUser",  updatedCustomer);
            resp.sendRedirect(req.getContextPath()+"/views/editUserDetails.jsp");
        } catch (SQLException e) {
            System.out.println("Couldn't retrieve user");
            throw new RuntimeException(e);
        }


    }

}
