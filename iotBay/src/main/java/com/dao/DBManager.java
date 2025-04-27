package com.dao;

import java.sql.*;

public class DBManager {
    private final Connection connection;

    private ProductDao productDao;
    private StaffDao staffDao;

    public DBManager(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public ProductDao getProductDao() {
        if (productDao == null) {
            productDao = new ProductDao(connection);
        }
        return productDao;
    }

    public StaffDao getStaffDao() {
        if (staffDao == null) {
            staffDao = new StaffDao(connection);
        }
        return staffDao;
    }
}
