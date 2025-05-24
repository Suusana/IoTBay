package com.controller.ProductController;
import com.controller.ProductController.AddNewProduct;
import com.bean.Product;
import com.bean.Category;
import com.dao.CategoryDao;
import com.dao.DBManager;
import com.dao.ProductDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;


import jakarta.servlet.http.Part;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductManagementServletTest {
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private HttpSession session;
    private DBManager db;
    private ProductDao pd;
    private CategoryDao cd;
    private ServletContext servletContext; //

    @BeforeEach
    public void setUp(){
        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        db = mock(DBManager.class);
        pd=mock(ProductDao.class);
        cd=mock(CategoryDao.class);
        servletContext = mock(ServletContext.class);
    }
    @Test
    public void testShowAllProducts() throws ServletException, IOException ,SQLException{
        ProductServlet productServlet = new ProductServlet();

        List<Product> products = new ArrayList<>();
        Product existingProduct = new Product();
        existingProduct.setProductId(1);
        existingProduct.setProductName("test");
        existingProduct.setQuantity(10);
        existingProduct.setPrice(10.0);
        existingProduct.setImage("image.png");
        existingProduct.setDescription("blablabla");
        existingProduct.setCategory(new Category(2,"home device"));
        products.add(existingProduct);
        Product existingProduct_2 = new Product();
        existingProduct_2.setProductId(2);
        existingProduct_2.setProductName("test_2");
        existingProduct_2.setQuantity(10);
        existingProduct_2.setPrice(10.0);
        existingProduct_2.setImage("image.png");
        existingProduct_2.setDescription("blablabla");
        existingProduct_2.setCategory(new Category(1,"smart device"));
        products.add(existingProduct_2);
        Product existingProduct_3 = new Product();
        existingProduct_3.setProductId(3);
        existingProduct_3.setProductName("test_3");
        existingProduct_3.setQuantity(10);
        existingProduct_3.setPrice(10.0);
        existingProduct_3.setImage("image.png");
        existingProduct_3.setDescription("blablabla");
        existingProduct_3.setCategory(new Category(1,"smart device"));
        products.add(existingProduct_3);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("db")).thenReturn(db);
        when(db.getProductDao()).thenReturn(pd);
        when(pd.getAllProducts()).thenReturn(products);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher("/views/shop.jsp")).thenReturn(dispatcher);
        productServlet.doGet(req, resp);

        verify(dispatcher).forward(req,resp);
       // System.out.println("Fetched products: " + pd.getAllProducts().size());
    }

    @Test
    public void testSearchProductViaCategory() throws ServletException, IOException, SQLException {
        GetByCategoryAndName getByCategoryAndName = new GetByCategoryAndName();

        List<Product> products = new ArrayList<>();
        Product existingProduct = new Product();
        existingProduct.setProductId(1);
        existingProduct.setProductName("test");
        existingProduct.setQuantity(10);
        existingProduct.setPrice(10.0);
        existingProduct.setImage("image.png");
        existingProduct.setDescription("blablabla");
        existingProduct.setCategory(new Category(2,"home device"));
        products.add(existingProduct);
        Product existingProduct_2 = new Product();
        existingProduct_2.setProductId(2);
        existingProduct_2.setProductName("test_2");
        existingProduct_2.setQuantity(10);
        existingProduct_2.setPrice(10.0);
        existingProduct_2.setImage("image.png");
        existingProduct_2.setDescription("blablabla");
        existingProduct_2.setCategory(new Category(1,"smart device"));
        products.add(existingProduct_2);
        Product existingProduct_3 = new Product();
        existingProduct_3.setProductId(3);
        existingProduct_3.setProductName("test_3");
        existingProduct_3.setQuantity(10);
        existingProduct_3.setPrice(10.0);
        existingProduct_3.setImage("image.png");
        existingProduct_3.setDescription("blablabla");
        existingProduct_3.setCategory(new Category(1,"smart device"));
        products.add(existingProduct_3);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("db")).thenReturn(db);
        when(db.getProductDao()).thenReturn(pd);
        when(db.getCategoryDao()).thenReturn(cd);
        when(req.getParameter("productName")).thenReturn("3");
        when(req.getParameter("categoryId")).thenReturn("1");
        when(cd.getCategoryById(1)).thenReturn(new Category(1,"smart device"));

        List<Product> category_filter = products.stream().filter(product->product.getCategory().getCategoryId()==1).collect(Collectors.toList());
        when(pd.getProductByCategory(1)).thenReturn(category_filter);

        List<Product> resultList = category_filter.stream().filter(product->product.getProductName().contains("3")).collect(Collectors.toList());
        when(pd.getProductByNameLike("3")).thenReturn(resultList);

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher("/views/AdminProductSearchByCategoryName.jsp")).thenReturn(dispatcher);
        getByCategoryAndName.doGet(req, resp);

        verify(dispatcher).forward(req,resp);
        System.out.println("Fetched products: " + resultList.size());


    }

    @Test
    public void testSearchProduct() throws ServletException,IOException,SQLException {
        GetByProductName getByProductName = new GetByProductName();

        List<Product> products = new ArrayList<>();
        Product existingProduct = new Product();
        existingProduct.setProductId(1);
        existingProduct.setProductName("test");
        existingProduct.setQuantity(10);
        existingProduct.setPrice(10.0);
        existingProduct.setImage("image.png");
        existingProduct.setDescription("blablabla");
        existingProduct.setCategory(new Category(2,"home device"));
        products.add(existingProduct);
        Product existingProduct_2 = new Product();
        existingProduct_2.setProductId(2);
        existingProduct_2.setProductName("es_2");
        existingProduct_2.setQuantity(10);
        existingProduct_2.setPrice(10.0);
        existingProduct_2.setImage("image.png");
        existingProduct_2.setDescription("blablabla");
        existingProduct_2.setCategory(new Category(1,"smart device"));
        products.add(existingProduct_2);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("db")).thenReturn(db);
        when(db.getProductDao()).thenReturn(pd);
        when(req.getParameter("productName")).thenReturn("t");

        List<Product> resultList = products.stream().filter(product->product.getProductName().contains("t")).collect(Collectors.toList());
        when(pd.getProductByNameLike("t")).thenReturn(resultList);

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher("/views/AdminProductSearchResult.jsp")).thenReturn(dispatcher);
        getByProductName.doGet(req, resp);

        verify(dispatcher).forward(req,resp);
        //System.out.println("Fetched products: " + pd.getProductByNameLike("t").size());
    }

    @Test
    public void testFilterByCategory() throws ServletException, IOException ,SQLException{
        GetByCategory getByCategory = new GetByCategory();

        List<Product> products = new ArrayList<>();
        Product existingProduct = new Product();
        existingProduct.setProductId(1);
        existingProduct.setProductName("test");
        existingProduct.setQuantity(10);
        existingProduct.setPrice(10.0);
        existingProduct.setImage("image.png");
        existingProduct.setDescription("blablabla");
        existingProduct.setCategory(new Category(2,"home device"));
        products.add(existingProduct);
        Product existingProduct_2 = new Product();
        existingProduct_2.setProductId(2);
        existingProduct_2.setProductName("test_2");
        existingProduct_2.setQuantity(10);
        existingProduct_2.setPrice(10.0);
        existingProduct_2.setImage("image.png");
        existingProduct_2.setDescription("blablabla");
        existingProduct_2.setCategory(new Category(1,"smart device"));
        products.add(existingProduct_2);
        Product existingProduct_3 = new Product();
        existingProduct_3.setProductId(2);
        existingProduct_3.setProductName("test_3");
        existingProduct_3.setQuantity(10);
        existingProduct_3.setPrice(10.0);
        existingProduct_3.setImage("image.png");
        existingProduct_3.setDescription("blablabla");
        existingProduct_3.setCategory(new Category(1,"smart device"));
        products.add(existingProduct_3);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("db")).thenReturn(db);
        when(db.getProductDao()).thenReturn(pd);
        when(db.getCategoryDao()).thenReturn(cd);
        when(req.getParameter("categoryId")).thenReturn("1");
        when(cd.getCategoryById(1)).thenReturn(new Category(1,"smart device"));

        List<Product> resultList = products.stream().filter(product->product.getCategory().getCategoryId()==1).collect(Collectors.toList());
        when(pd.getProductByCategory(1)).thenReturn(resultList);

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher("/views/AdminProductSearchByCategory.jsp")).thenReturn(dispatcher);
        getByCategory.doGet(req, resp);

        verify(dispatcher).forward(req,resp);
        System.out.println("Fetched products: " + pd.getProductByCategory(1).size());
    }

    @Test
    public void testDeleteProduct() throws ServletException, IOException,SQLException {
        DeleteProductHandler deleteProductHandler = new DeleteProductHandler();

        Product existingProduct = new Product();
        existingProduct.setProductId(1);
        existingProduct.setProductName("test");
        existingProduct.setQuantity(10);
        existingProduct.setPrice(10.0);
        existingProduct.setImage("image.png");
        existingProduct.setDescription("blablabla");
        existingProduct.setCategory(new Category(2,"home device"));

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("db")).thenReturn(db);
        when(db.getProductDao()).thenReturn(pd);
        when(db.getCategoryDao()).thenReturn(cd);

        when(req.getParameter("productId")).thenReturn("1");
        when(pd.getProductById(1)).thenReturn(existingProduct);

        deleteProductHandler.doPost(req, resp);
        verify(pd).deleteProduct(existingProduct);

        System.out.println("Product deleted");
    }

    @Test
    public void testUpdateProduct() throws ServletException, IOException,SQLException {
        UpdateProductServlet updateProductServlet = new UpdateProductServlet();
        Part img = mock(Part.class);
        Product existingProduct = new Product();
        existingProduct.setProductId(1);
        existingProduct.setProductName("test");
        existingProduct.setQuantity(10);
        existingProduct.setPrice(10.0);
        existingProduct.setImage("image.png");
        existingProduct.setDescription("blablabla");
        existingProduct.setCategory(new Category(2,"home device"));

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("db")).thenReturn(db);
        when(db.getProductDao()).thenReturn(pd);
        when(db.getCategoryDao()).thenReturn(cd);
        //new input from form
        when(req.getParameter("productId")).thenReturn("1");
        when(pd.getProductById(1)).thenReturn(existingProduct);

        when(req.getParameter("productName")).thenReturn(existingProduct.getProductName());
        when(req.getParameter("price")).thenReturn("18.9");
        when(req.getParameter("quantity")).thenReturn("1");
        when(req.getParameter("description")).thenReturn("Touch sensor_description");
        when(req.getParameter("categoryId")).thenReturn("1");
        when(cd.lenCategory()).thenReturn(10);
        when(cd.getCategoryById(1)).thenReturn(new Category(1,"Smart device"));
        //for image
        when(req.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRealPath("/")).thenReturn(System.getProperty("java.io.tmpdir"));
        when(req.getPart("image")).thenReturn(img);
        when(img.getSubmittedFileName()).thenReturn("");
        when(req.getContextPath()).thenReturn("");

        updateProductServlet.doPost(req, resp);
        ArgumentCaptor<Product> updateCaptor = ArgumentCaptor.forClass(Product.class);
        verify(pd).updateProduct(updateCaptor.capture());
        Product updatedProduct = updateCaptor.getValue();
        verify(resp).sendRedirect("/ProductManagementServlet");
        System.out.println("prev quantity: "+existingProduct.getQuantity()+" current quantity: "+updatedProduct.getQuantity());
        System.out.println("Product Updated");

    }
    
    @Test
    public void testAddProduct() throws ServletException, IOException, SQLException {
        AddNewProduct addNewProduct = new AddNewProduct();
        Part img = mock(Part.class);

        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("db")).thenReturn(db);
        when(db.getProductDao()).thenReturn(pd);
        when(db.getCategoryDao()).thenReturn(cd);

        //add product info
        when(req.getParameter("productName")).thenReturn("Touch sensor");
        when(req.getParameter("price")).thenReturn("18.9");
        when(req.getParameter("quantity")).thenReturn("1");
        when(req.getParameter("description")).thenReturn("Touch sensor_description");
        when(req.getParameter("categoryId")).thenReturn("1");
        when(cd.lenCategory()).thenReturn(10);
        when(cd.getCategoryById(1)).thenReturn(new Category(1,"Smart device"));
        //for image
        when(req.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRealPath("/")).thenReturn(System.getProperty("java.io.tmpdir"));
        when(req.getPart("image")).thenReturn(img);
        when(img.getSubmittedFileName()).thenReturn("");
        when(req.getContextPath()).thenReturn("");

        addNewProduct.doPost(req, resp);
        verify(pd).createProduct(any(Product.class));
        verify(resp).sendRedirect("/ProductManagementServlet");
        System.out.println("Product Added");
    }
}
