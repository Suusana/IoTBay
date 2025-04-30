package com.controller.UserAccessController;

import com.bean.Customer;
import com.dao.CustomerDao;
import com.dao.DBManager;
import com.enums.Status;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db =  (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String username = email.split("@")[0];
        String password = req.getParameter("password");
        Long phoneNumber = Long.valueOf(req.getParameter("phoneNumber"));
        String state = req.getParameter("state");
        String city = req.getParameter("city");
        Integer postalCode = Integer.valueOf(req.getParameter("postalCode"));
        String country = req.getParameter("country");
        String address;
        if (req.getParameter("unit") != null) {
            address = req.getParameter("unit") + " " + req.getParameter("street");
        } else {
            address = req.getParameter("street");
        }
        String status = Status.REGISTERED.getStatus();

        Customer customer = new Customer(username, password, firstName, lastName, phoneNumber, email, status, address,
                city, state, postalCode, country, new ArrayList<>());
        session.setAttribute("loggedInUser", customer);
        try {
            customerDao.addUser(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("/views/welcome.jsp");
    }
}
