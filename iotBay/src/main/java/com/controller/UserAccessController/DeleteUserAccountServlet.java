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

@WebServlet("/DeleteUserAccountServlet")
public class DeleteUserAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db =  (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();
        Customer customer = (Customer) session.getAttribute("loggedInUser");

        try {
            customerDao.deleteUser(customer);
            session.removeAttribute("loggedInUser");
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        } catch (SQLException e) {
            session.setAttribute("error", "Couldn't delete user account");
            resp.sendRedirect(req.getContextPath()+"/deleteAccount.jsp");
            System.out.println("User could not be deleted");
            throw new RuntimeException(e);
        }
    }
}
