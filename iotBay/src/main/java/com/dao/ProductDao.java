package com.dao;

import com.bean.Category;
import com.bean.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private final Connection connection;
   // private String db_url = "jdbc:mysql://localhost:3306/iotbay";
    public ProductDao(Connection connection) {
        this.connection = connection;
    }

    //Product CRUD
    //This is just to check whether the code is working
    public int getProductCount() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from product");
        ResultSet totalProductAmount = preparedStatement.executeQuery();
        if(totalProductAmount.next()) {
            return totalProductAmount.getInt(1);
        }
        return 0;
    }

    //Product CREATE - staff only
    public void createProduct(Product product) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO product (product_name, price, quantity, description, image, category_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)");
        ps.setString(1, product.getProductName());
        ps.setDouble(2, product.getPrice());
        ps.setInt(3, product.getQuantity());
        ps.setString(4, product.getDescription());
        ps.setString(5, product.getImage()); // assuming image filename or path
        ps.setInt(6, product.getCategory().getCategoryId());

        ps.executeUpdate();
    }
/*  Product_T
*   private Integer productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private String description;
    private String image;
    private Category category;
*
* */
    //Product READ - everyone can see it
    //getAllProducts shows all the products from the database -> the shop.jsp will show all these products
    //Feature 2 - READ - Customer and staff user can list the IoT device records
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM product");
        ResultSet ProductTable = preparedStatement.executeQuery();
        while(ProductTable.next()) {
         Product product = new Product();
         product.setProductId(ProductTable.getInt("product_id"));
         product.setProductName(ProductTable.getString("product_name"));
         product.setQuantity(ProductTable.getInt("quantity"));
         product.setPrice(ProductTable.getDouble("price"));
         product.setDescription(ProductTable.getString("description"));
         product.setImage(ProductTable.getString("image"));
          int categoryId = ProductTable.getInt("category_id");
          Category category = new Category();
          category.setCategoryId(categoryId);
          product.setCategory(category);
         products.add(product);
        }
        return products;
    }

    public void getProductById(int productID) throws SQLException {
        PreparedStatement preparedstatement = connection.prepareStatement("select * from product where product_id=?");
        preparedstatement.setInt(1,productID);
        preparedstatement.executeQuery();
        // return product;
    }

    //Product Update - staff only
    public void updateProduct(Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Product SET price = ? productName=? category=? quantity=? image=? description=? category_id=? WHERE product_id = ?");
        preparedStatement.setInt(1, product.getProductId());
        preparedStatement.setDouble(2,product.getPrice());
        preparedStatement.setString(3, product.getProductName());
        preparedStatement.setInt(4,product.getQuantity());
        preparedStatement.setString(5,product.getImage());
        preparedStatement.setString(6,product.getDescription());
        preparedStatement.setInt(7,product.getCategory().getCategoryId());
        preparedStatement.executeUpdate();
    }

    //Product Delete - staff only
    public void deleteProduct(int productId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("delete from product where product_id = ?");
        preparedStatement.setInt(1, productId);
        preparedStatement.executeUpdate();
    }
}
