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

@WebServlet("/EditUserDetailsServlet")
public class EditUserDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db =  (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();
        StaffDao staffDao = db.getStaffDao();
        String userType = (String) session.getAttribute("userType");

        if (userType == null) {
            session.setAttribute("errorMessage", "Please login to edit your profile");
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        } else if (userType.equalsIgnoreCase("customer")) {
            Customer customer = (Customer) session.getAttribute("loggedInUser");

            Customer updatedCustomer;
            try {
                // update customer to latest data from database before displaying
                updatedCustomer = customerDao.getUserById(customer.getUserId());
                session.setAttribute("loggedInUser",  updatedCustomer);
                resp.sendRedirect(req.getContextPath()+"/views/editUserDetails.jsp");
                return;
            } catch (SQLException e) {
                System.out.println("Couldn't retrieve customer from database");
                throw new RuntimeException(e);
            }
        } else if (userType.equalsIgnoreCase("staff")) {
            Staff staff = (Staff) session.getAttribute("loggedInUser");

            Staff updatedStaff;
            try {
                // update staff to latest data from database before displaying
                updatedStaff = staffDao.getStaffById(staff.getStaffId());
                session.setAttribute("loggedInUser",  updatedStaff);
                resp.sendRedirect(req.getContextPath()+"/views/editStaffPersonalDetails.jsp");
                return;
            } catch (SQLException ex) {
                System.out.println("Couldn't retrieve staff from database");
                throw new RuntimeException(ex);
            }
        }

        session.setAttribute("errorMessage", "Please login to view your profile");
        resp.sendRedirect(req.getContextPath()+"/views/login.jsp");

    }

}