package com.controller.CustomerController;

import com.dao.CustomerDao;
import com.dao.DBManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/toggleCustomerStatus")
public class ToggleCustomerStatus extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();

        String currentPage = req.getParameter("currentPage");
        String query = req.getParameter("query");
        int id = Integer.parseInt(req.getParameter("customerId"));
        String _status = req.getParameter("status");

        int status = _status.equals("Active") ? 0 : 1;

        try {
            customerDao.setStatusById(id, status);
        } catch (SQLException e) {
            System.out.println("Failed to update customer status");
        }

        String url = req.getContextPath() + "/ShowCustomerInfo?page=" + currentPage;
        if (query != null && !query.isEmpty()) {
            url += "&query=" + query;
        }

        resp.sendRedirect(url);
    }
}
