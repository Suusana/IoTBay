package com.controller.StaffController;

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

@WebServlet("/toggleStaffStatus")
public class toggleStaffStatus extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        StaffDao staffDao = db.getStaffDao();

        String currentPage = req.getParameter("currentPage");
        String query = req.getParameter("query");
        int id = Integer.parseInt(req.getParameter("staffId"));
        String _status = req.getParameter("status");

        int status = _status.equals("Active")?0:1;

        try {
            staffDao.setStatusById(id,status);
        } catch (SQLException e) {
            System.out.println("Failed to update staff status");
        }

        // if there is a search keyword, then add search info
        String url = req.getContextPath() + "/ShowStaffInfo?page=" + currentPage;
        if (query != null && !query.isEmpty()) {
            url += "&query=" + query;
        }

        resp.sendRedirect(url);
    }
}
