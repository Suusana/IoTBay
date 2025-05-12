package com.controller.UserAccessController;

import com.bean.Customer;
import com.bean.Staff;
import com.bean.UserAccessLog;
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
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;

@WebServlet("/SearchAccessLogsServlet")
public class SearchAccessLogsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db =  (DBManager) session.getAttribute("db");
        String userType =  (String) session.getAttribute("userType");
        UserAccessLogDao userAccessLogDao = db.getUserAccessLogDao();

        String startDateStr = req.getParameter("startDate");
        String endDateStr = req.getParameter("endDate");

        System.out.println("Received date parameters: start=" + startDateStr + ", end=" + endDateStr);

        if (session.getAttribute("loggedInUser") == null || userType == null) {
            req.setAttribute("errorMessage", "Please login to search your access logs");
            req.getRequestDispatcher(req.getContextPath() + "/views/viewAccessLogs.jsp").forward(req, resp);
            return;
        }

        Customer customer;
        Staff staff;
        int userId = 0;
        if (userType.equalsIgnoreCase("customer")) {
            customer = (Customer) session.getAttribute("loggedInUser");
            userId = customer.getUserId();

        } else if (userType.equalsIgnoreCase("staff")) {
            staff = (Staff) session.getAttribute("loggedInUser");
            userId = staff.getStaffId();
        }

        // shows all logs if no dates selected
        if ((startDateStr == null || startDateStr.isEmpty()) && (endDateStr == null || endDateStr.isEmpty())) {
            try {
                LinkedList<UserAccessLog> userAccessLogs = userAccessLogDao.getLogsByUser(userId, userType);
                req.setAttribute("accessLogs", userAccessLogs);
                req.getRequestDispatcher(req.getContextPath() + "/views/viewAccessLogs.jsp").forward(req, resp);
                return;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // error if only one date is set
        if (startDateStr == null || startDateStr.isEmpty() || endDateStr == null || endDateStr.isEmpty()) {
            req.setAttribute("errorMessage", "Both start and end dates are required");
            req.getRequestDispatcher("/views/viewAccessLogs.jsp").forward(req, resp);
            return;
        }

        LocalDate startLocalDate;
        LocalDate endLocalDate;
        try {
            startLocalDate = LocalDate.parse(startDateStr);
            endLocalDate = LocalDate.parse(endDateStr);
        } catch (DateTimeParseException e) {
            req.setAttribute("errorMessage", "Invalid start date format");
            req.getRequestDispatcher("/views/viewAccessLogs.jsp").forward(req, resp);
            return;
        }

        if (startLocalDate.isAfter(endLocalDate)) {
            req.setAttribute("errorMessage", "Start date cannot be after end date");
            req.getRequestDispatcher("/views/viewAccessLogs.jsp").forward(req, resp);
            return;
        }

        // Convert to timestamps - start of day for start date, end of day for end date
        LocalDateTime startDateTime = startLocalDate.atStartOfDay();
        LocalDateTime endDateTime = endLocalDate.atTime(23, 59, 59);

        Timestamp startTimestamp = Timestamp.valueOf(startDateTime);
        Timestamp endTimestamp = Timestamp.valueOf(endDateTime);
        System.out.println("Start timestamp: " + startTimestamp);
        System.out.println("End timestamp: " + endTimestamp);

        LinkedList<UserAccessLog> filteredAccessLogs = null;
        try {
            filteredAccessLogs = userAccessLogDao.getLogsBetweenDate(userId, userType, startTimestamp, endTimestamp);
            req.setAttribute("accessLogs", filteredAccessLogs);
            req.setAttribute("startDate", startDateStr);
            req.setAttribute("endDate", endDateStr);
            req.getRequestDispatcher("/views/viewAccessLogs.jsp").forward(req, resp);
        } catch (SQLException e) {
            System.out.println("Could not retrieve access logs by date from database");
            e.printStackTrace();
        }
    }
}


