package com.controller.UserAccessController;

import com.bean.Customer;
import com.bean.Staff;
import com.bean.UserAccessLog;
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
import java.util.LinkedList;

@WebServlet("/ViewAccessLogsServlet")
public class ViewAccessLogsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db =  (DBManager) session.getAttribute("db");
        String userType = (String) session.getAttribute("userType");
        UserAccessLogDao userAccessLogDao = db.getUserAccessLogDao();

        if (userType == null) {
            session.setAttribute("errorMessage", "Please login before accessing account history");
            resp.sendRedirect(req.getContextPath()+"/views/login.jsp");
            return;
        }

        Customer customer;
        Staff staff;
        // get access logs if customer
        if (userType.equalsIgnoreCase("Customer")) {
            customer = (Customer) session.getAttribute("loggedInUser");

            try {
                LinkedList<UserAccessLog> accessLogs = userAccessLogDao.getLogsByUser(customer.getUserId(), userType);
                req.setAttribute("accessLogs", accessLogs);
                req.getRequestDispatcher("/views/viewAccessLogs.jsp").forward(req, resp);
            } catch (SQLException e) {
                System.out.println("Could not retrieve customer's access logs");
                throw new RuntimeException(e);
            }
            // get access logs if staff
        } else if (userType.equalsIgnoreCase("Staff")) {
            staff = (Staff) session.getAttribute("loggedInUser");

            LinkedList<UserAccessLog> accessLogs = null;
            try {
                accessLogs = userAccessLogDao.getLogsByUser(staff.getStaffId(), userType);
                req.setAttribute("accessLogs", accessLogs);
                req.getRequestDispatcher("/views/viewAccessLogs.jsp").forward(req, resp);
            } catch (SQLException e) {
                System.out.println("Could not retrieve staff's access logs");
                throw new RuntimeException(e);
            }

        }

    }
}
