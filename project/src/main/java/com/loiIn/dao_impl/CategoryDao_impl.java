package com.loiIn.dao_impl;

import com.loiIn.dao.CategoryDao;
import com.loiIn.model.Category;
import com.loiIn.model.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao_impl implements CategoryDao {

    private MyConnection myConnection = new MyConnection();

    //Dùng để đọc các trường trong resultGet và trả về đối tượng có các thuộc tính tương ứng
    @Override
    public Category getObject(ResultSet resultSet) throws SQLException {
        Category category = null;
        category = new Category(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getBoolean("deleted"));
        return  category;
    }

    @Override
    public List<Category> findAll() throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        String sql = "select * from category where deleted = false";
        PreparedStatement preparedStatement = myConnection.prepare(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.first()){
            do{
                Category category = getObject(resultSet);
                if(category != null) categoryList.add(category);
            }while (resultSet.next());
        }
        return categoryList;
    }

    @Override
    public Category findById(int id) throws SQLException {
        Category category = null;
        String sql = "select * from category where deleted = false and id = ?";
        PreparedStatement preparedStatement = myConnection.prepare(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.first()){
            category = getObject(resultSet);
        }
        return category;
    }

    @Override
    public Category insert(Category category) throws SQLException {
        Category newCategory = null;
        String sql = "insert into category (name, deleted) values(?,?)";
        PreparedStatement preparedStatement = myConnection.prepareUpdate(sql);
        preparedStatement.setString(1, category.getName());
        preparedStatement.setBoolean(2,category.isDeleted());
        int rs = preparedStatement.executeUpdate();
        if(rs >0){
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.first()){
                newCategory = findById((int)resultSet.getLong(1));
            }
        }
        return newCategory;
    }

    @Override
    public boolean update(Category category) throws SQLException {
        boolean result = false;
        String sql = "update category set name = ? where id = ?";
        PreparedStatement preparedStatement = myConnection.prepareUpdate(sql);
        preparedStatement.setString(1,category.getName());
        preparedStatement.setInt(2, category.getId());
        int rs = preparedStatement.executeUpdate();
        if(rs > 0) result = true;
        return result;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        boolean result = false;
        String sql = "update category set deleted = true where id = ?";
        PreparedStatement preparedStatement = myConnection.prepareUpdate(sql);
        preparedStatement.setInt(1,id);
        int rs = preparedStatement.executeUpdate();
        if(rs > 0) result = true;
        return result;
    }

    @Override
    public List<Category> getList(ResultSet resultSet) throws SQLException {
        return null;
    }
}
