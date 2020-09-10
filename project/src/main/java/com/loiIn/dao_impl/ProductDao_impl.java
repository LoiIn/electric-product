package com.loiIn.dao_impl;

import com.loiIn.dao.CategoryDao;
import com.loiIn.dao.ProductDao;
import com.loiIn.model.MyConnection;
import com.loiIn.model.Product;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao_impl implements ProductDao {

    private MyConnection myConnection = new MyConnection();

    private CategoryDao categoryDao = new CategoryDao_impl();

    @Override
    public Product getObject(ResultSet resultSet) throws SQLException {
        Product product = null;
        product = new Product(resultSet.getInt("id"), resultSet.getString("name"),
                resultSet.getDouble("price"), resultSet.getDate("create_date"),
                resultSet.getBoolean("deleted"),resultSet.getString("image"),
                resultSet.getString("introduction"),resultSet.getString("specification"),
                resultSet.getBoolean("sold_out"),resultSet.getInt("guarantee"),resultSet.getInt("category_id"),
                resultSet.getInt("bought"),resultSet.getInt("promotion"));
        return product;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        String sql = "select * from product where deleted = false";
        PreparedStatement preparedStatement = myConnection.prepare(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        return getList(resultSet);
    }

    @Override
    public Product findById(int id) throws SQLException {
        Product product = null;
        String sql = "select * from product where deleted = false and id = ?";
        PreparedStatement preparedStatement = myConnection.prepare(sql);
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.first()){
            product = getObject(resultSet);
        }
        return product;
    }

    @Override
    public Product insert(Product product) throws SQLException {
        Product newProduct = null;
        String sql = "insert into product values (null, ?, ?, ?, false, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = myConnection.prepareUpdate(sql);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setDouble(2, product.getPrice());
        preparedStatement.setDate(3, new Date(new java.util.Date().getTime()));
        preparedStatement.setString(4, product.getImage());
        preparedStatement.setString(5, product.getIntroduction());
        preparedStatement.setString(6, product.getSpecification());
        preparedStatement.setBoolean(7, product.isSoldOut());
        preparedStatement.setInt(8, product.getGuarantee());
        preparedStatement.setInt(9, product.getCategoryId());
        preparedStatement.setInt(10, product.getBought());
        preparedStatement.setInt(11, product.getPromotion());
        int rs = preparedStatement.executeUpdate();
        if(rs > 0 ) {
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.first()) {
                newProduct = findById((int) resultSet.getLong(1));
            }
        }
        return newProduct;
    }

    @Override
    public boolean update(Product product) throws SQLException {
       boolean result = false;
        String sql = "update product set name = ?, price = ?, create_date = ?, " +
                "image = ?, introduction = ?, specification = ?, sold_out = ? " +
                "guarantee = ?, category_id = ?, bought = ?, promotion = ? where id = ?";
        PreparedStatement preparedStatement = myConnection.prepareUpdate(sql);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setDouble(2,product.getPrice());
        preparedStatement.setString(3, product.getImage());
        preparedStatement.setString(4, product.getIntroduction());
        preparedStatement.setString(5, product.getSpecification());
        preparedStatement.setBoolean(6, product.isSoldOut());
        preparedStatement.setInt(7, product.getGuarantee());
        preparedStatement.setInt(8, product.getCategoryId());
        preparedStatement.setInt(9, product.getBought());
        preparedStatement.setInt(10, product.getPromotion());
        preparedStatement.setInt(11,product.getId());
        int rs = preparedStatement.executeUpdate();
        if(rs > 0) result = true;
        return  result;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        boolean result = false;
        String sql = "update product set deleted = true where id = ?";
        PreparedStatement preparedStatement =  myConnection.prepareUpdate(sql);
        preparedStatement.setInt(1, id);
        int rs = preparedStatement.executeUpdate();
        if(rs > 0) result = true;
        return  result;
    }

    @Override
    public List<Product> getList(ResultSet resultSet) throws SQLException {
        List<Product> productList = new ArrayList<>();
        if(resultSet.first()){
            do{
                Product product = getObject(resultSet);
                if(product != null) productList.add(product);
            }while (resultSet.next());
        }
        return productList;
    }

    @Override
    public List<Product> sortBy(String field, boolean isAsc) throws SQLException {
        String sql = "select * from product where " +
                "deleted = false order by " + field + (isAsc ? "ASC" : "DESC");
        PreparedStatement preparedStatement = myConnection.prepare(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        return getList(resultSet);
    }

    @Override
    public List<Product> findByCategory(int idCategory) throws SQLException {
        String sql = "select * from product as p, category as c " +
                "where c.deleted =  false  and c.id = ? and p.category_id =  c.id";
        PreparedStatement preparedStatement = myConnection.prepare(sql);
        preparedStatement.setInt(1, idCategory);
        ResultSet resultSet = preparedStatement.executeQuery();
        return  getList(resultSet);
    }

    @Override
    public List<Product> search(String name, String startDate, String endDate, Boolean soldOut, int guarantee, int category, int bought, int promotion) throws SQLException {
        String sql = "select distinct p.* from product as p, category as c " +
                "where deleted = false and p.name like ? and " +
                "(? is null or p.create_date >= ?) and " +
                "(? is null or p.create_date <= ?) and " +
                "(? is null or p.sold_out = ?) and " +
                "(? = -1 or p.guarantee = ?) and " +
                "(? = -1 or (c.deleted = false and c.id = ? and p.category_id = c.id)) and " +
                "(? = -1 or p.bought = ?) and " +
                "(? = -1 or p.promotion = ?";
        PreparedStatement preparedStatement = myConnection.prepare(sql);
        preparedStatement.setString(1, "%" + name + "%");
        preparedStatement.setString(2, startDate);
        preparedStatement.setString(3, startDate == null ? "0000-01-01" : startDate);
        preparedStatement.setString(4, endDate);
        preparedStatement.setString(5, endDate == null ? "9999-12-31" : endDate);
        if(soldOut == null){
            preparedStatement.setString(6, null);
            preparedStatement.setBoolean(7, true);
        }else{
            preparedStatement.setString(6, "");
            preparedStatement.setBoolean(7, soldOut);
        }
        preparedStatement.setInt(8,  guarantee);
        preparedStatement.setInt(9,  guarantee);
        preparedStatement.setInt(10, category);
        preparedStatement.setInt(11, category);
        preparedStatement.setInt(12, bought);
        preparedStatement.setInt(13, bought);
        preparedStatement.setInt(14, promotion);
        preparedStatement.setInt(15, promotion);
        ResultSet resultSet = preparedStatement.executeQuery();
        return getList(resultSet);
    }
}
