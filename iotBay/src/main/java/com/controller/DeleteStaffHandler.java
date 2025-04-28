package com.controller;

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

@WebServlet("/DeleteStaff")
public class DeleteStaffHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        StaffDao staffDao = db.getStaffDao();

        int id = Integer.parseInt(req.getParameter("staffId"));
        try {
            staffDao.deleteStaffById(id);
            resp.sendRedirect(req.getContextPath() + "/ShowStaffInfo");
        } catch (SQLException e) {
            System.out.println("Failed to delete staff");
        }
    }
}
