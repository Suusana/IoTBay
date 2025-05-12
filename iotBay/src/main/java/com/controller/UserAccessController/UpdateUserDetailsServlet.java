package com.controller.UserAccessController;

import com.bean.Customer;
import com.bean.Staff;
import com.dao.CustomerDao;
import com.dao.DBManager;
import com.dao.StaffDao;
import com.util.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

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

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        long phoneNumber = -1;
        try {
            phoneNumber = Long.parseLong(req.getParameter("phone"));
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Phone number must be a valid number");
        }
        String address = req.getParameter("address");
        String city = req.getParameter("city");
        int postcode = -1;
        try {
            postcode = Integer.parseInt(req.getParameter("postcode"));
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Postcode must be a valid number");
        }
        String country = req.getParameter("country");
        String state = req.getParameter("state");

        // check submitted input for errors
        Map<String, String> errors = Utils.validateUserInput(firstName, lastName, email, password, confirmPassword,
                phoneNumber, state, city, postcode, country, address, true);

        // check if email is unique - ignore if input is user's current email
        Object user = session.getAttribute("loggedInUser");
        String currentEmail = (userType.equals("customer") ? ((Customer) user).getEmail() : ((Staff) user).getEmail());
        try {
            boolean isSameEmail = email.equalsIgnoreCase(currentEmail);
            if (!isSameEmail && (customerDao.emailExists(email) || staffDao.emailExists(email))) {
                errors.put("errorMessage", "Email is already registered to an account");
            }
        } catch (SQLException e) {
            System.out.println("Error checking for staffs duplicate email in registration");
            throw new RuntimeException(e);
        }

        // if any errors, display and don't let edit process
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            if (userType.equals("customer")) {
                req.getRequestDispatcher("/views/editUserDetails.jsp").forward(req, resp);
                return;
            } else if (userType.equals("staff")) {
                req.getRequestDispatcher("/views/editStaffPersonalDetails.jsp").forward(req, resp);
                return;
            }
            return;
        }

        Customer currentCustomer;
        Staff currentStaff;
        // update if customer
        if (userType.equalsIgnoreCase("Customer")) {
            currentCustomer = (Customer) session.getAttribute("loggedInUser");

            Customer customer = new Customer();
            customer.setUsername(username);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setPhone(phoneNumber);
            customer.setEmail(email);
            customer.setAddress(address);
            customer.setCity(city);
            customer.setState(state);
            customer.setPostcode(postcode);
            customer.setCountry(country);
            // set unchangeable values from old details
            customer.setType(currentCustomer.getType());
            customer.setUserId(currentCustomer.getUserId());

            // update password if valid input provided
            if (password != null && !password.trim().isEmpty()) {
                customer.setPassword(password);
            } else {
                customer.setPassword(currentCustomer.getPassword());
            }

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
            String staffName = firstName + " "  + lastName;
            staff.setStaffName(staffName);
            staff.setPhoneNum((int) phoneNumber);
            staff.setEmail(email);
            staff.setAddress(address);
            staff.setCity(city);
            staff.setPostcode(String.valueOf(postcode));
            staff.setState(state);
            staff.setCountry(country);
            // set unchangeable values from old staff details
            staff.setStaffId(currentStaff.getStaffId());
            staff.setPosition(currentStaff.getPosition());
            staff.setStatus("Active");

            // update password if valid input provided
            if (password != null && !password.trim().isEmpty()) {
                staff.setPassword(password);
            } else {
                staff.setPassword(currentStaff.getPassword());
            }

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
