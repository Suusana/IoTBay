package com.dao;

import com.bean.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerDao {
    private final Connection connection;

    public CustomerDao(Connection connection) {
        this.connection = connection;
    }

    // Create user
    public void addUser(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO User (username, first_name, last_name, " +
                "password, email, phone, status, address, state, city, postcode, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, customer.getUsername());
        preparedStatement.setString(2, customer.getFirstName());
        preparedStatement.setString(3, customer.getLastName());
        preparedStatement.setString(4, customer.getPassword());
        preparedStatement.setString(5, customer.getEmail());
        preparedStatement.setLong(6, customer.getPhone());
        preparedStatement.setString(7, customer.getStatus());
        preparedStatement.setString(8, customer.getAddress());
        preparedStatement.setString(9, customer.getState());
        preparedStatement.setString(10, customer.getCity());
        preparedStatement.setLong(11, customer.getPostcode());
        preparedStatement.setString(12, customer.getCountry());
        // add history to db
//        preparedStatement.setString(13, customer.getHistory().toString());
        preparedStatement.execute();
    }

    // Read user for login
    public Customer getUser(String email, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USER WHERE email = ? AND password = ?");
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        Customer customer = null;

        if (resultSet.next()) {
            customer = new Customer();
            customer.setUserId(resultSet.getLong("user_id"));
            customer.setUsername(resultSet.getString("username"));
            customer.setPassword(resultSet.getString("password"));
            customer.setFirstName(resultSet.getString("first_name"));
            customer.setLastName(resultSet.getString("last_name"));
            customer.setPhone(resultSet.getLong("phone"));
            customer.setEmail(resultSet.getString("email"));
            customer.setStatus(resultSet.getString("status"));
            customer.setAddress(resultSet.getString("address"));
            customer.setCity(resultSet.getString("city"));
            customer.setState(resultSet.getString("state"));
            customer.setPostcode(resultSet.getInt("postcode"));
            customer.setCountry(resultSet.getString("country"));
//            customer.setHistory(Arrays.asList(resultSet.getString("history").split(",")));
        }
        return customer;
    }

    // Read user
    public Customer getUserById(Long userId) throws SQLException {
        PreparedStatement preparedStatement =  connection.prepareStatement("SELECT * FROM User WHERE user_id = ?");
        preparedStatement.setLong(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        Customer customer = null;

        if (resultSet.next()) {
            customer = new Customer();
            customer.setUserId(resultSet.getLong("user_id"));
            customer.setUsername(resultSet.getString("username"));
            customer.setPassword(resultSet.getString("password"));
            customer.setFirstName(resultSet.getString("first_name"));
            customer.setLastName(resultSet.getString("last_name"));
            customer.setPhone(resultSet.getLong("phone"));
            customer.setEmail(resultSet.getString("email"));
            customer.setStatus(resultSet.getString("status"));
            customer.setAddress(resultSet.getString("address"));
            customer.setCity(resultSet.getString("city"));
            customer.setState(resultSet.getString("state"));
            customer.setPostcode(resultSet.getInt("postcode"));
            customer.setCountry(resultSet.getString("country"));
        }
        return customer;
    }


    // Delete User
    public void deleteUser(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USER WHERE user_id = ?");
        preparedStatement.setLong(1, customer.getUserId());
        preparedStatement.executeUpdate();
    }
}
