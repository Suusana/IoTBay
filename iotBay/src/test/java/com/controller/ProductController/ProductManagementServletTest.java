package com.controller.ProductController;
import com.bean.Product;
import com.dao.ProductDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductManagementServletTest {

    @Test
    public void testFetchProduct() throws SQLException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
    }

    @Test
    public void testProductCategorySearch() throws IOException,SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        ProductDao pd = new ProductDao(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("categoryId")).thenReturn(1);
        when(resultSet.getString("searchName")).thenReturn("sensor");



        String searchName="sensor";
        Product product = new Product();
        try{
            List<Product> products = pd.getProductByCategory_searchName(searchName,1);
            if(products.size()>0) {
                System.out.println(products.get(0).getProductName());
            }else{
                System.out.println("NO PRODUCT FOUND");
            }
        }catch(SQLException e) {
            System.out.println("Cateogry_SearchName Error");
            e.printStackTrace();
        }

    }
}