package com.dao;

import com.bean.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class CustomerDao {
    private final Connection connection;

    public CustomerDao(Connection connection) {
        this.connection = connection;
    }
