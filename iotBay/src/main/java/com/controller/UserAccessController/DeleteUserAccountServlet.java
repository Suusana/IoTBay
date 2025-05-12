package com.controller.UserAccessController;

import com.bean.Customer;
import com.bean.Order;
import com.bean.Staff;
import com.dao.CustomerDao;
import com.dao.DBManager;
import com.dao.OrderDao;
import com.dao.StaffDao;
import com.enums.OrderStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/DeleteUserAccountServlet")
public class DeleteUserAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db =  (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();
        StaffDao staffDao = db.getStaffDao();
        String userType = (String) session.getAttribute("userType");
        OrderDao orderDao = db.getOrderDao();

        if (session.getAttribute("loggedInUser") == null || userType == null) {
            session.setAttribute("errorMessage", "Please login before trying to delete your account");
            resp.sendRedirect(req.getContextPath()+"/views/login.jsp");
            return;
        }

        Customer customer;
        Staff staff;
        if (userType.equalsIgnoreCase("customer")) {
            customer = (Customer) session.getAttribute("loggedInUser");
            List<Order> orders;

            // set users orders status' to cancelled
            try {
                orders = orderDao.getOrdersByUserId(customer.getUserId());
                for (Order order : orders) {
                    order.setOrderStatus(OrderStatus.Cancelled);
                    orderDao.updateOrderStatus(order.getOrderId(), OrderStatus.Cancelled);
                }
            } catch (SQLException e) {
                System.out.println("Couldn't cancel user's orders");
                throw new RuntimeException(e);
            }

            // delete account and logout user
            try {
                customerDao.deleteUser(customer);
                session.removeAttribute("loggedInUser");
                resp.sendRedirect(req.getContextPath()+"/index.jsp");
            } catch (SQLException e) {
                session.setAttribute("errorMessage", "Couldn't delete customer account");
                resp.sendRedirect(req.getContextPath()+"/deleteAccount.jsp");
                System.out.println("Customer could not be deleted");
                throw new RuntimeException(e);
            }

        } else if (userType.equalsIgnoreCase("staff")) {
            staff = (Staff) session.getAttribute("loggedInUser");

            try {
                staffDao.deleteStaffById(staff.getStaffId());
                session.removeAttribute("loggedInUser");
                resp.sendRedirect(req.getContextPath()+"/index.jsp");
            } catch (SQLException e) {
                session.setAttribute("errorMessage", "Couldn't delete staff account");
                resp.sendRedirect(req.getContextPath()+"/deleteAccount.jsp");
                System.out.println("Staff could not be deleted");
                throw new RuntimeException(e);
            }
        }

    }
}
