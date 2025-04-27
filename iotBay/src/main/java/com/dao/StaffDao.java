package com.dao;

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

        int status = ("Active".equals(staff.getStatus()))? 1:0;
        ps.setString(1, staff.getStaffName());
        ps.setString(2, staff.getPassword());
        ps.setInt(3, staff.getPhoneNum());
        ps.setString(4, staff.getEmail());
        ps.setString(5, staff.getPosition());
        ps.setInt(6,status);
        ps.setString(7, staff.getAddress());
        ps.setString(8, staff.getCity());
        ps.setString(9, staff.getPostcode());
        ps.setString(10, staff.getState());
        ps.setString(11, staff.getCountry());

        ps.executeUpdate();
    }
}
