package com.controller.UserAccessController;

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

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        if (session.getAttribute("loggedInUser") == null) {
            session.setAttribute("errorMessage", "Please login before attempting to logout");
            resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
            return;
        }

        // Log logout time
        Integer userAccessLogId = (Integer) session.getAttribute("userAccessLogId");
        if (userAccessLogId != null) {
            DBManager db =  (DBManager) session.getAttribute("db");
            UserAccessLogDao userAccessLogDao = db.getUserAccessLogDao();
            try {
                userAccessLogDao.logLogout(userAccessLogId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // Logout
        session.removeAttribute("loggedInUser");
        resp.sendRedirect(req.getContextPath()+"/index.jsp");
    }
}
