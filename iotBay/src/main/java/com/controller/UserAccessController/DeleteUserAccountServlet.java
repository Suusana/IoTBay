package com.controller.UserAccessController;

import com.bean.Customer;
import com.bean.Staff;
import com.dao.CustomerDao;
import com.dao.DBManager;
import com.dao.StaffDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/DeleteUserAccountServlet")
public class DeleteUserAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db =  (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();
        StaffDao staffDao = db.getStaffDao();
        String userType = (String) session.getAttribute("userType");

        if (session.getAttribute("loggedInUser") == null || userType == null) {
            session.setAttribute("errorMessage", "Please login before trying to delete your account");
            resp.sendRedirect(req.getContextPath()+"/views/login.jsp");
            return;
        }

        Customer customer;
        Staff staff;
        if (userType.equalsIgnoreCase("customer")) {
            customer = (Customer) session.getAttribute("loggedInUser");

            try {
                customerDao.deleteUser(customer);
                session.removeAttribute("loggedInUser");
                resp.sendRedirect(req.getContextPath()+"/index.jsp");
            } catch (SQLException e) {
                session.setAttribute("errorMessage", "Couldn't delete customer account");
                resp.sendRedirect(req.getContextPath()+"/deleteAccount.jsp");
                System.out.println("Customer could not be deleted");
                throw new RuntimeException(e);
            }

        } else if (userType.equalsIgnoreCase("staff")) {
            staff = (Staff) session.getAttribute("loggedInUser");

            try {
                staffDao.deleteStaffById(staff.getStaffId());
                session.removeAttribute("loggedInUser");
                resp.sendRedirect(req.getContextPath()+"/index.jsp");
            } catch (SQLException e) {
                session.setAttribute("errorMessage", "Couldn't delete staff account");
                resp.sendRedirect(req.getContextPath()+"/deleteAccount.jsp");
                System.out.println("Staff could not be deleted");
                throw new RuntimeException(e);
            }
        }

    }
}
