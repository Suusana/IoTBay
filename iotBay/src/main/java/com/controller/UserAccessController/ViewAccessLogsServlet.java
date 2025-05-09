package com.controller.UserAccessController;

import com.bean.Customer;
import com.bean.UserAccessLog;
import com.dao.CustomerDao;
import com.dao.DBManager;
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
        Customer customer = (Customer) session.getAttribute("loggedInUser");
        String userType = session.getAttribute("userType").toString().toLowerCase();
        UserAccessLogDao userAccessLogDao = db.getUserAccessLogDao();

        try {
            LinkedList<UserAccessLog> accessLogs = userAccessLogDao.getLogsByUser(customer.getUserId(), userType);
            req.setAttribute("accessLogs", accessLogs);
            req.getRequestDispatcher("/views/viewAccessLogs.jsp").forward(req, resp);
        } catch (SQLException e) {
            System.out.println("Could not retrieve user's access logs");
            throw new RuntimeException(e);
        }
    }
}
