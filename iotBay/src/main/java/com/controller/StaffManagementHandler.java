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
import java.util.List;

@WebServlet("/ShowStaffInfo")
public class StaffManagementHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        StaffDao staffDao = db.getStaffDao();

        String query = req.getParameter("query");
        String _page = req.getParameter("page");

        int page = (_page != null) ? Integer.parseInt(_page) : 1;

        try {
            List<Staff> staffList;
            int totalRecords;
            if (query != null && !query.trim().isEmpty()) {
                staffList = staffDao.getStaffByNameOrPositionByPage(query, page);
                totalRecords = staffDao.getSearchStaffCount(query);
                req.setAttribute("query", query);
            } else {
                staffList = staffDao.getStaffByPage(page);
                totalRecords = staffDao.getStaffCount();
            }

            req.setAttribute("staffList", staffList);
            req.setAttribute("staffCurrentPage", page);
            req.setAttribute("staffTotalRecords", totalRecords);
            req.setAttribute("staffTotalPage", (int) Math.ceil((double) totalRecords / 7));



            req.getRequestDispatcher("views/StaffManagement.jsp").forward(req, resp);
        } catch (SQLException e) {
            System.out.println("Connection Error");
        }
    }
}
