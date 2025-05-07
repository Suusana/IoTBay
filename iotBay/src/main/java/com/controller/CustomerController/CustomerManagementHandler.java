package com.controller.CustomerController;

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
import java.util.List;

@WebServlet("/ShowCustomerInfo")
public class CustomerManagementHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        CustomerDao customerDao = db.getCustomerDao();

        String query = req.getParameter("query");
        String _page = req.getParameter("page");

        int page = (_page != null) ? Integer.parseInt(_page) : 1;

        try {
            List<Customer> customerList;
            int totalRecords;

            if (query != null && !query.trim().isEmpty()) {
                customerList = customerDao.getCustomerByNameOrEmailByPage(query, page);
                totalRecords = customerDao.getSearchCustomerCount(query);
                req.setAttribute("query", query);
            } else {
                customerList = customerDao.getCustomerByPage(page);
                totalRecords = customerDao.getCustomerCount();
            }

            req.setAttribute("customerList", customerList);
            req.setAttribute("customerCurrentPage", page);
            req.setAttribute("customerTotalRecords", totalRecords);
            req.setAttribute("customerTotalPage", (int) Math.ceil((double) totalRecords / 7));

            req.getRequestDispatcher("views/CustomerManagement.jsp").forward(req, resp);
        } catch (SQLException e) {
            System.out.println("Connection Error: " + e.getMessage());
        }
    }
}
