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

@WebServlet("/ViewUserDetailsServlet")
public class ViewUserDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db =  (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();
        StaffDao staffDao = db.getStaffDao();
        String userType = (String) session.getAttribute("userType");

        Customer customer = null;
        Staff staff = null;
        if (userType == null) {
            session.setAttribute("errorMessage", "Please login to edit your profile");
            resp.sendRedirect(req.getContextPath()+"/views/login.jsp");
            return;
        } else if (userType.equalsIgnoreCase("customer") || userType.equalsIgnoreCase("guest")) {
            customer = (Customer) session.getAttribute("loggedInUser");
        } // modify to save guest user type
        else if (userType.equalsIgnoreCase("staff")) {
            staff = (Staff) session.getAttribute("loggedInUser");
        }

        if (customer != null) {
            Customer updatedCustomer;
            try {
                // update customer to latest data from database before displaying
                updatedCustomer = customerDao.getUserById(customer.getUserId());
                session.setAttribute("loggedInUser",  updatedCustomer);
                resp.sendRedirect(req.getContextPath()+"/views/userDetails.jsp");
                return;
            } catch (SQLException e) {
                System.out.println("Couldn't retrieve user");
                throw new RuntimeException(e);
            }
        } else if (staff != null) {
            Staff updatedStaff;
            try {
                // update staff to latest data from database before displaying
                updatedStaff = staffDao.getStaffById(staff.getStaffId());
                session.setAttribute("loggedInUser",  updatedStaff);
                resp.sendRedirect(req.getContextPath()+"/views/staffPersonalDetails.jsp");
                return;
            } catch (SQLException e) {
                System.out.println("Couldn't retrieve user");
                throw new RuntimeException(e);
            }
        }

        // if both customer and staff is null - no user logged in
        session.setAttribute("errorMessage", "Please login to view your profile");
        resp.sendRedirect(req.getContextPath()+"/views/login.jsp");
    }

}
