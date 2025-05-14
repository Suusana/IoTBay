package com.controller.StaffController;

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

        // check if email or phone num is unique
        Integer staffId = Integer.parseInt(req.getParameter("staffId"));
        String email = req.getParameter("email");
        int phone = Integer.parseInt(req.getParameter("phone_num"));

        try {
            Integer id = staffDao.getStaffIdByPhoneOrEmail(phone, email, staffId);

            if (id != null) {
                req.setAttribute("msg", "This phone number or email is already exist.");
                req.getRequestDispatcher("/showStaff").forward(req, resp);
                return;
            }

            Staff staff = new Staff();
            staff.setStaffId(staffId);
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

        } catch (SQLException e) {
            System.out.println("Failed to get Info");
        }
    }
}
