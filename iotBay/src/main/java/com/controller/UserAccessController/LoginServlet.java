package com.controller.UserAccessController;

import com.bean.Customer;
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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db =  (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();

        String email = req.getParameter("email").trim();
        String password = req.getParameter("password");

        if (email.isEmpty() || password.isEmpty()) {
            session.setAttribute("errorMessage", "Email and Password are required");
            resp.sendRedirect(req.getContextPath() + "/views/login.jsp");
            return;
        }

        try {
            Customer customer = customerDao.getUser(email, password);

            if (customer == null) {
                session.setAttribute("errorMessage", "Incorrect Email or Password");
                resp.sendRedirect(req.getContextPath()+"/views/login.jsp");
            } else {
                session.removeAttribute("errorMessage");
                session.setAttribute("loggedInUser", customer);
                resp.sendRedirect(req.getContextPath()+"/home");
            }
        } catch (SQLException e) {
            System.out.println("Can't find customer in database");
            e.printStackTrace();
        }
    }
}
