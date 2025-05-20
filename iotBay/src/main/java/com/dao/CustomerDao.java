package com.dao;

import com.bean.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.util.Objects;

public class CustomerDao {
    private final Connection connection;

    public CustomerDao(Connection connection) {
        this.connection = connection;
    }

    // Create user
    public void addUser(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO User (username, first_name, last_name, " +
                "password, email, phone, status, address, state, city, postcode, country,`type`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        int status = Objects.equals(customer.getStatus(), "Active") ? 1 : 0;

        preparedStatement.setString(1, customer.getUsername());
        preparedStatement.setString(2, customer.getFirstName());
        preparedStatement.setString(3, customer.getLastName());
        preparedStatement.setString(4, customer.getPassword());
        preparedStatement.setString(5, customer.getEmail());
        preparedStatement.setLong(6, customer.getPhone());
        preparedStatement.setInt(7, status);
        preparedStatement.setString(8, customer.getAddress());
        preparedStatement.setString(9, customer.getState());
        preparedStatement.setString(10, customer.getCity());
        preparedStatement.setLong(11, customer.getPostcode());
        preparedStatement.setString(12, customer.getCountry());
        preparedStatement.setString(13, customer.getType());
        preparedStatement.execute();
    }

    // Read user for login
    public Customer getUser(String email, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM User WHERE email = ? AND password = ?");
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        return mapCustomer(resultSet);

    }

    // Read user
    public Customer getUserById(Integer userId) throws SQLException {
        PreparedStatement preparedStatement =  connection.prepareStatement("SELECT * FROM User WHERE user_id = ?");
        preparedStatement.setLong(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return mapCustomer(resultSet);
    }


    // Delete User
    public void deleteUser(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM User WHERE user_id = ?");
        preparedStatement.setInt(1, customer.getUserId());
        preparedStatement.executeUpdate();
    }

    // Update Customer
    public void updateCustomer(Customer customer) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE User SET username=?, first_name=?, last_name=?, password=?, email=?, phone=?, address=?, " +
                        "state=?, city=?, postcode=?, country=?, `type`=? WHERE user_id=?"
        );
        ps.setString(1, customer.getUsername());
        ps.setString(2, customer.getFirstName());
        ps.setString(3, customer.getLastName());
        ps.setString(4, customer.getPassword());
        ps.setString(5, customer.getEmail());
        ps.setLong(6, customer.getPhone());
        ps.setString(7, customer.getAddress());
        ps.setString(8, customer.getState());
        ps.setString(9, customer.getCity());
        ps.setInt(10, customer.getPostcode());
        ps.setString(11, customer.getCountry());
        ps.setString(12, customer.getType());
        ps.setInt(13, customer.getUserId());
        ps.executeUpdate();
    }

    // Set user status to Active/ Inactive
    public void setStatusById(int id, int status) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE User SET status = ? WHERE user_id = ?");
        ps.setInt(1, status);
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    // Get customers for current page
    public List<Customer> getCustomerByPage(int page) throws SQLException {
        int offset = (page - 1) * 7;
        PreparedStatement ps = connection.prepareStatement("select * from User limit ?,7");
        ps.setInt(1, offset);
        ResultSet rs = ps.executeQuery();
        return mapCustomerList(rs);
    }

    // Search customers by name or email
    public List<Customer> getCustomerByNameOrEmailByPage(String query, int page) throws SQLException {
        int offset = (page - 1) * 7;
        String sql = "SELECT * FROM User WHERE first_name LIKE ? OR last_name LIKE ? OR email LIKE ? LIMIT ?,7";
        PreparedStatement ps = connection.prepareStatement(sql);
        String wildcard = "%" + query + "%";
        ps.setString(1, wildcard);
        ps.setString(2, wildcard);
        ps.setString(3, wildcard);
        ps.setInt(4, offset);
        ResultSet rs = ps.executeQuery();
        return mapCustomerList(rs);
    }

    // Get total number of customers
    public int getCustomerCount() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM User");
        rs.next();
        return rs.getInt(1);
    }

    // Get number of customers matching search query
    public int getSearchCustomerCount(String query) throws SQLException {
        String sql = "SELECT COUNT(*) FROM User WHERE first_name LIKE ? OR last_name LIKE ? OR email LIKE ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        String wildcard = "%" + query + "%";
        ps.setString(1, wildcard);
        ps.setString(2, wildcard);
        ps.setString(3, wildcard);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public boolean emailExists(String email) throws SQLException {
        PreparedStatement preparedStatement =  connection.prepareStatement("SELECT 1 FROM User WHERE email = ?");
        try {
            preparedStatement.setString(1, email);
            ResultSet resultSet =  preparedStatement.executeQuery();
            return resultSet.next(); // true if matching email exists
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Map a ResultSet to a single Customer object
    private Customer mapCustomer(ResultSet rs) throws SQLException {
        Customer customer = null;
        if (rs.next()) {
            customer = new Customer();
            int num = rs.getInt("status");
            customer.setUserId(rs.getInt("user_id"));
            customer.setUsername(rs.getString("username"));
            customer.setPassword(rs.getString("password"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setPhone(rs.getLong("phone"));
            customer.setEmail(rs.getString("email"));
            customer.setStatus(num==1?"Active":"Inactive");
            customer.setType(rs.getString("type"));
            customer.setAddress(rs.getString("address"));
            customer.setCity(rs.getString("city"));
            customer.setState(rs.getString("state"));
            customer.setPostcode(rs.getInt("postcode"));
            customer.setCountry(rs.getString("country"));
        }
        return customer;
    }

    // Map a ResultSet to a list of Customer objects
    private List<Customer> mapCustomerList(ResultSet rs) throws SQLException {
        List<Customer> list = new ArrayList<>();
        while (rs.next()) {
            Customer customer = new Customer();
            int num = rs.getInt("status");
            customer.setUserId(rs.getInt("user_id"));
            customer.setUsername(rs.getString("username"));
            customer.setPassword(rs.getString("password"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setPhone(rs.getLong("phone"));
            customer.setEmail(rs.getString("email"));
            customer.setStatus(num==1?"Active":"Inactive");
            customer.setType(rs.getString("type"));
            customer.setAddress(rs.getString("address"));
            customer.setCity(rs.getString("city"));
            customer.setState(rs.getString("state"));
            customer.setPostcode(rs.getInt("postcode"));
            customer.setCountry(rs.getString("country"));
            list.add(customer);
        }
        return list;
    }

    // usered for anonymous user creation
    public void setUser(Customer customer) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO User " +
                "(username, first_name, last_name, type, email, password) VALUES (?, ?, ?, ?, ?, ?)");
        ps.setString(1, customer.getUsername());
        ps.setString(2, customer.getFirstName());
        ps.setString(3, customer.getLastName());
        ps.setString(4, customer.getType());
        ps.setString(5, customer.getEmail());
        ps.setString(6, customer.getPassword());
        ps.executeUpdate();
    }

    // get the last user
    public Customer getLastUser() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from User order by user_id desc");
        ResultSet rs = ps.executeQuery();
        return mapCustomer(rs);
    }
}
