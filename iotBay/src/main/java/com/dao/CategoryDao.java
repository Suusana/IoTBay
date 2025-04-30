package com.dao;

import com.bean.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private final Connection connection;
    public CategoryDao(Connection connection) {
        this.connection = connection;
    }

    //get all the categories
//    public List<Category> getAllCategories() {
//        PreparedStatement ps = ;
//    }

    // get first 3 categories - for homepage
    public List<Category> get3Categories() throws SQLException {
        List<Category> list = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from Category limit 3");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Category category = new Category();
            category.setCategoryId(rs.getInt("category_id"));
            category.setCategory(rs.getString("category"));
            list.add(category);
        }
        return list;
    }
}
