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
    public Category getCategoryById(int categoryId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from Category where category_id=?");
        ps.setInt(1, categoryId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) { //re.nex()) instead of rs != null bc rs still exists even though there is no row
            Category category = new Category();
            category.setCategoryId(rs.getInt("category_id"));
            category.setCategory(rs.getString("category"));
            return category;
        }
        return null;
    }
    public int lenCategory() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select count(*) from Category");
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int len = rs.getInt(1);
            return len;
        }
        return 0;
    }
}
