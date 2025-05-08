package com.controller.CustomerController;

import com.bean.Customer;
import com.dao.CustomerDao;
import com.dao.DBManager;
import com.dao.DBConnector;
import java.sql.Connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AddCustomer")
public class AddCustomerHandler extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");

        if (db == null) {
            DBConnector connector = new DBConnector();
            Connection conn = connector.getConnection();

            if (conn == null) {
                throw new ServletException("XX. DBConnector returned null connection. Check DB path or SQLite driver.");
            }

            db = new DBManager(conn);
            session.setAttribute("db", db);
        }

        CustomerDao customerDao = db.getCustomerDao();

        Customer customer = new Customer();
        customer.setUsername(req.getParameter("name"));
        customer.setEmail(req.getParameter("email"));
        customer.setStatus("Active");
        customer.setAddress(req.getParameter("address"));

        try {
            customerDao.addUser(customer);
            resp.sendRedirect(req.getContextPath() + "/ShowCustomerInfo");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }
}
package com.controller.CustomerController;

import com.bean.Customer;
import com.dao.CustomerDao;
import com.dao.DBManager;
import com.dao.DBConnector;
import java.sql.Connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AddCustomer")
public class AddCustomerHandler extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");

        if (db == null) {
            DBConnector connector = new DBConnector();
            Connection conn = connector.getConnection();

            if (conn == null) {
                throw new ServletException("XX. DBConnector returned null connection. Check DB path or SQLite driver.");
            }

            db = new DBManager(conn);
            session.setAttribute("db", db);
        }

        CustomerDao customerDao = db.getCustomerDao();

        Customer customer = new Customer();
        customer.setUsername(req.getParameter("name"));
        customer.setEmail(req.getParameter("email"));
        customer.setStatus("Active");
        customer.setAddress(req.getParameter("address"));

        try {
            customerDao.addUser(customer);
            resp.sendRedirect(req.getContextPath() + "/ShowCustomerInfo");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        }
    }
}
