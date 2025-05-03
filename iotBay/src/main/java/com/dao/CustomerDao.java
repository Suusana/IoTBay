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

