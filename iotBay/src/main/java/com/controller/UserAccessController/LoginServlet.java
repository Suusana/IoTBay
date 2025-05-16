package com.controller.UserAccessController;

import com.bean.Customer;
import com.bean.Staff;
import com.dao.CustomerDao;
import com.dao.DBManager;
import com.dao.StaffDao;
import com.dao.UserAccessLogDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db =  (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();
        StaffDao staffDao = db.getStaffDao();

        String email = req.getParameter("email").trim();
        String password = req.getParameter("password");

        if (email.isEmpty() || password.isEmpty()) {
            session.setAttribute("errorMessage", "Email and Password are required");
            resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
            return;
        }

        try {
            // Try customer login
            Customer customer = customerDao.getUser(email, password);
            if (customer != null) {
                session.removeAttribute("errorMessage");
                session.setAttribute("loggedInUser", customer);

                String userTypeFromDb = customer.getType(); // "customer" or "guest"
                session.setAttribute("userType", userTypeFromDb);

                UserAccessLogDao userAccessLogDao = db.getUserAccessLogDao();
                int userAccessLogId = userAccessLogDao.logLogin(customer.getUserId(), userTypeFromDb);
                session.setAttribute("userAccessLogId", userAccessLogId);

                resp.sendRedirect("views/welcome.jsp");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Can't retrieve customer from database");
        }

        try {
            // Try staff login
            Staff staff = staffDao.getStaffForLogin(email, password);
            if (staff != null) {
                session.removeAttribute("errorMessage");
                session.setAttribute("loggedInUser", staff);
                session.setAttribute("userType", "staff");

                UserAccessLogDao userAccessLogDao = db.getUserAccessLogDao();
                int userAccessLogId = userAccessLogDao.logLogin(staff.getStaffId(), "staff");
                session.setAttribute("userAccessLogId", userAccessLogId);

                resp.sendRedirect("views/welcome.jsp");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Can't retrieve staff from database");
            e.printStackTrace();
        }

        // Login failed
        session.setAttribute("errorMessage", "Incorrect Email or Password");
        resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
    }
}
