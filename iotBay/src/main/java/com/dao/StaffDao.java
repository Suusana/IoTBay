package com.dao;

import com.bean.Customer;
import com.bean.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDao {
    private final Connection connection;

    public StaffDao(Connection connection) {
        this.connection = connection;
    }

    // get staff information by page (7 records per page)
    public List<Staff> getStaffByPage(int page) throws SQLException {
        List<Staff> staffList = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from Staff limit ?,7");
        int Page = (page - 1) * 7;
        ps.setInt(1, Page);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            Staff staff = new Staff();
            int num = resultSet.getInt("status");
            staff.setStatus(num == 1 ? "Active" : "Inactive");

            staff.setStaffId(resultSet.getInt("staff_id"));
            staff.setStaffName(resultSet.getString("staff_name"));
            staff.setPhoneNum(resultSet.getInt("phone_num"));
            staff.setEmail(resultSet.getString("email"));
            staff.setPosition(resultSet.getString("position"));
            staff.setAddress(resultSet.getString("address"));
            staff.setCity(resultSet.getString("city"));
            staff.setState(resultSet.getString("state"));
            staff.setCountry(resultSet.getString("country"));

            staffList.add(staff);
        }
        return staffList;
    }

    // get the total staff count
    public int getStaffCount() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select count(*) from Staff");
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    // get all searched staff by page
    public List<Staff> getStaffByNameOrPositionByPage(String keyword, int page) throws SQLException {
        List<Staff> staffList = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from Staff where staff_name like ? or position like ? limit ?,7");
        String searchText = "%" + keyword + "%";
        int Page = (page - 1) * 7;
        ps.setString(1, searchText);
        ps.setString(2, searchText);
        ps.setInt(3, Page);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            Staff staff = new Staff();
            int num = resultSet.getInt("status");
            staff.setStatus(num == 1 ? "Active" : "Inactive");

            staff.setStaffId(resultSet.getInt("staff_id"));
            staff.setStaffName(resultSet.getString("staff_name"));
            staff.setPhoneNum(resultSet.getInt("phone_num"));
            staff.setEmail(resultSet.getString("email"));
            staff.setPosition(resultSet.getString("position"));
            staff.setAddress(resultSet.getString("address"));
            staff.setCity(resultSet.getString("city"));
            staff.setState(resultSet.getString("state"));
            staff.setCountry(resultSet.getString("country"));

            staffList.add(staff);
        }
        return staffList;
    }

    //get the total searched staff count
    public int getSearchStaffCount(String keyword) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select count(*) from Staff where staff_name like ? or position like ? ");
        String searchText = "%" + keyword + "%";
        ps.setString(1, searchText);
        ps.setString(2, searchText);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    //    add a new staff
    public void addStaff(Staff staff) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into Staff(staff_name,password,phone_num,email," +
                "position,status,address,city,postcode,state,country) values(?,?,?,?,?,?,?,?,?,?,?)");

        int status = ("Active".equals(staff.getStatus())) ? 1 : 0;
        ps.setString(1, staff.getStaffName());
        ps.setString(2, staff.getPassword());
        ps.setInt(3, staff.getPhoneNum());
        ps.setString(4, staff.getEmail());
        ps.setString(5, staff.getPosition());
        ps.setInt(6, status);
        ps.setString(7, staff.getAddress());
        ps.setString(8, staff.getCity());
        ps.setString(9, staff.getPostcode());
        ps.setString(10, staff.getState());
        ps.setString(11, staff.getCountry());

        ps.executeUpdate();
    }

    //   get a staff by their id
    public Staff getStaffById(int staffId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from Staff where staff_id=?");
        ps.setInt(1, staffId);
        ResultSet resultSet = ps.executeQuery();
        Staff staff = new Staff();
        if (resultSet.next()) {
            int num = resultSet.getInt("status");
            staff.setStatus(num == 1 ? "Active" : "Inactive");

            staff.setStaffId(resultSet.getInt("staff_id"));
            staff.setStaffName(resultSet.getString("staff_name"));
            staff.setPassword(resultSet.getString("password"));
            staff.setPhoneNum(resultSet.getInt("phone_num"));
            staff.setEmail(resultSet.getString("email"));
            staff.setPosition(resultSet.getString("position"));
            staff.setAddress(resultSet.getString("address"));
            staff.setCity(resultSet.getString("city"));
            staff.setPostcode(resultSet.getString("postcode"));
            staff.setState(resultSet.getString("state"));
            staff.setCountry(resultSet.getString("country"));
        }
        return staff;
    }

    // Read staff for login
    public Staff getStaffForLogin(String email, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Staff WHERE email = ? AND password = ?");
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        Staff staff = null;

        if (resultSet.next()) {
            staff = new Staff();
            staff.setStaffId(resultSet.getInt("staff_id"));
            staff.setStaffName(resultSet.getString("staff_name")); // change to first and last later
            staff.setPassword(resultSet.getString("password"));
            staff.setPhoneNum(resultSet.getInt("phone_num"));
            staff.setEmail(resultSet.getString("email"));
            staff.setPosition(resultSet.getString("position"));
            staff.setStatus(resultSet.getString("status"));
            staff.setAddress(resultSet.getString("address"));
            staff.setCity(resultSet.getString("city"));
            staff.setPostcode(resultSet.getString("postcode"));
            staff.setState(resultSet.getString("state"));
            staff.setCountry(resultSet.getString("country"));

        }
        return staff;

    }

    //    update a staff
    public void UpdateStaff(Staff staff) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update Staff set staff_name = ?, password = ?, phone_num = ?," +
                "email=?, position = ?, address = ?, city = ?, postcode = ?, state = ?,country = ? where staff_id=?");

        ps.setString(1, staff.getStaffName());
        ps.setString(2, staff.getPassword());
        ps.setInt(3, staff.getPhoneNum());
        ps.setString(4, staff.getEmail());
        ps.setString(5, staff.getPosition());
        ps.setString(6, staff.getAddress());
        ps.setString(7, staff.getCity());
        ps.setString(8, staff.getPostcode());
        ps.setString(9, staff.getState());
        ps.setString(10, staff.getCountry());
        ps.setInt(11, staff.getStaffId());

        ps.executeUpdate();
    }

//    toggle Staff Status
    public void setStatusById(int staffId, int status) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update Staff set status = ? where staff_id=?");
        ps.setInt(1, status);
        ps.setInt(2, staffId);
        ps.executeUpdate();
    }

    public boolean emailExists(String email) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT 1 FROM Staff WHERE email = ?");
        try {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // true if email exists in table
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // delete a staff
    public void deleteStaffById(int staffId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("delete from Staff where staff_id=?");
        ps.setInt(1, staffId);
        ps.executeUpdate();
    }
}
