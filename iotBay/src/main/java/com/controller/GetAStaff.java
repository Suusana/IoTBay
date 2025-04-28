package com.controller;

import com.bean.Staff;
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

@WebServlet("/showStaff")
public class GetAStaff extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        StaffDao staffDao = db.getStaffDao();

        int id = Integer.parseInt(req.getParameter("staffId"));
        try {
            Staff staff = staffDao.getStaffById(id);
            if (staff != null) {
                req.setAttribute("staff", staff);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get staff by id");
        }

        String view = req.getParameter("view");
        if (view != null) {
            // for viewing staff detail
            req.getRequestDispatcher("views/StaffDetails.jsp").forward(req, resp);
        } else {
            //for updating the staff
            req.getRequestDispatcher("views/UpdateStaff.jsp").forward(req, resp);
        }
    }
}
