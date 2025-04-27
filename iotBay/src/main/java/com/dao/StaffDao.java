package com.dao;

import java.sql.Connection;

public class StaffDao {
    private final Connection connection;

    public StaffDao(Connection connection) {
        this.connection = connection;
    }


}
