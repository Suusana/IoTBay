package com.controller.UserAccessController;

import com.bean.Customer;
import com.dao.CustomerDao;
import com.dao.DBManager;
import com.dao.UserAccessLogDao;
import com.enums.Status;
import com.enums.UserType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AnonymousCreation")
public class AnonymousCreation extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();
        UserAccessLogDao userAccessLogDao = db.getUserAccessLogDao();

        Customer customer = new Customer();
        // create guest ID
        long timestamp = System.currentTimeMillis();
        customer.setFirstName(String.valueOf(timestamp));
        customer.setLastName("Guest");

        customer.setUsername("guest" + timestamp);
        customer.setPassword("guest" + timestamp);
        customer.setEmail("guest" + timestamp + "@example.com");
        customer.setType("Individual");
        session.setAttribute("userType", "guest"); // modify to save guest user type

        try {
            customerDao.setUser(customer);

            Customer user = customerDao.getLastUser();
            int userAccessLogId = userAccessLogDao.logLogin(user.getUserId(), "guest"); // ✅ 핵심 수정
            session.setAttribute("userAccessLogId", userAccessLogId);

            session.setAttribute("loggedInUser", user);
            session.setAttribute("userType", "guest"); // modify to save guest user type

            resp.sendRedirect("views/welcome.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to save guest user");
        }
    }
}
