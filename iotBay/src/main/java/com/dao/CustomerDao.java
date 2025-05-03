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
                "password, email, status, address, state, city, postcode, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                "password, email, phone, status, address, state, city, postcode, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, customer.getUsername());
        preparedStatement.setString(2, customer.getFirstName());
        preparedStatement.setString(3, customer.getLastName());
        preparedStatement.setString(4, customer.getPassword());
        preparedStatement.setString(5, customer.getEmail());
        preparedStatement.setString(6, customer.getStatus());
        preparedStatement.setString(7, customer.getAddress());
        preparedStatement.setString(8, customer.getState());
        preparedStatement.setString(9, customer.getCity());
        preparedStatement.setLong(10, customer.getPostcode());
        preparedStatement.setString(11, customer.getCountry());
        // add phone number to db
//        preparedStatement.setLong(12, customer.getPhone());
        preparedStatement.setLong(6, customer.getPhone());
        // add history to db
//        preparedStatement.setString(13, customer.getHistory().toString());
        preparedStatement.execute();
    }
}

