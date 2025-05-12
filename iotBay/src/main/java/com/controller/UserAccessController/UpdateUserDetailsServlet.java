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

@WebServlet("/UpdateUserDetailsServlet")
public class UpdateUserDetailsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db =  (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();
        StaffDao staffDao = db.getStaffDao();
        String userType = (String) session.getAttribute("userType");

        if (session.getAttribute("loggedInUser") == null || userType == null) {
            session.setAttribute("errorMessage", "Please login before editing your details");
            resp.sendRedirect(req.getContextPath()+"/views/login.jsp");
            return;
        }

        Customer currentCustomer;
        Staff currentStaff;
        // update if customer
        if (userType.equalsIgnoreCase("Customer")) {
            currentCustomer = (Customer) session.getAttribute("loggedInUser");

            Customer customer = new Customer();
            customer.setUsername(req.getParameter("username"));
            String password = req.getParameter("password");
            String confirmPassword = req.getParameter("confirmPassword");
            // if password field is empty, keep password as is
            // else check if password and confirmPassword match before updating
            if (password == null || confirmPassword == null || password.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
                customer.setPassword(currentCustomer.getPassword());
            } else if (!password.equals(confirmPassword)) {
                session.setAttribute("errorMessage", "Passwords do not match");
                resp.sendRedirect(req.getContextPath()+"/views/editUserDetails.jsp");
                return;
            } else {
                customer.setPassword(password);
            }
            customer.setFirstName(req.getParameter("firstName"));
            customer.setLastName(req.getParameter("lastName"));
            customer.setPhone(Long.valueOf(req.getParameter("phone")));
            customer.setEmail(req.getParameter("email"));
            customer.setAddress(req.getParameter("address"));
            customer.setCity(req.getParameter("city"));
            customer.setState(req.getParameter("state"));
            customer.setPostcode(Integer.valueOf(req.getParameter("postcode")));
            customer.setCountry(req.getParameter("country"));
            // set unchangeable values from old details
            customer.setType(currentCustomer.getType());
            customer.setUserId(currentCustomer.getUserId());

            try {
                customerDao.updateCustomer(customer);
                session.setAttribute("loggedInUser", customer);
                resp.sendRedirect(req.getContextPath()+"/ViewUserDetailsServlet");
            } catch (SQLException e) {
                System.out.println("Could not update customer");
                e.printStackTrace();
            }
        // update if staff
        } else if (userType.equalsIgnoreCase("Staff")) {
            currentStaff = (Staff) session.getAttribute("loggedInUser");

            Staff staff = new Staff();
            String staffName = req.getParameter("firstName") + " " + req.getParameter("lastName");
            staff.setStaffName(staffName);
            String password = req.getParameter("password");
            String confirmPassword = req.getParameter("confirmPassword");
            // if password field is empty, keep password as is
            // else check if password and confirmPassword match before updating
            if (password == null || confirmPassword == null || password.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
                staff.setPassword(currentStaff.getPassword());
            } else if (!password.equals(confirmPassword)) {
                session.setAttribute("errorMessage", "Passwords do not match");
                resp.sendRedirect(req.getContextPath()+"/views/editStaffPersonalDetails.jsp");
                return;
            } else {
                staff.setPassword(password);
            }
            staff.setPhoneNum(Integer.valueOf(req.getParameter("phone")));
            staff.setEmail(req.getParameter("email"));
            staff.setAddress(req.getParameter("address"));
            staff.setCity(req.getParameter("city"));
            staff.setPostcode(req.getParameter("postcode"));
            staff.setState(req.getParameter("state"));
            staff.setCountry(req.getParameter("country"));
            // set unchangeable values from old staff details
            staff.setStaffId(currentStaff.getStaffId());
            staff.setPosition(currentStaff.getPosition());
            staff.setStatus("Active");

            try {
                staffDao.UpdateStaff(staff);
                session.setAttribute("loggedInUser", staff);
                resp.sendRedirect(req.getContextPath()+"/ViewUserDetailsServlet");
            } catch (SQLException e) {
                System.out.println("Could not update staff");
                e.printStackTrace();
            }


        }


    }
}
