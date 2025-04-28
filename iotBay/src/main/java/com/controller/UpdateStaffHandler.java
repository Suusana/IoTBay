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

@WebServlet("/UpdateStaff")
public class UpdateStaffHandler extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        DBManager db = (DBManager) session.getAttribute("db");
        StaffDao staffDao = db.getStaffDao();

        Staff staff = new Staff();
        staff.setStaffId(Integer.parseInt(req.getParameter("staffId")));
        staff.setStaffName(req.getParameter("staff_name"));
        staff.setPassword(req.getParameter("password"));
        staff.setPhoneNum(new Integer(req.getParameter("phone_num")));
        staff.setEmail(req.getParameter("email"));
        staff.setPosition(req.getParameter("position"));
        staff.setAddress(req.getParameter("address"));
        staff.setCity(req.getParameter("city"));
        staff.setPostcode(req.getParameter("postcode"));
        staff.setState(req.getParameter("state"));
        staff.setCountry(req.getParameter("country"));

        try {
            staffDao.UpdateStaff(staff);
            resp.sendRedirect(req.getContextPath() + "/ShowStaffInfo");
        } catch (SQLException e) {
            System.out.println("Failed to update a staff");
        }
    }
}
