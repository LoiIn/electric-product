package com.loiIn.service_impl;

import com.loiIn.dao.CategoryDao;
import com.loiIn.dao_impl.CategoryDao_impl;
import com.loiIn.model.Category;
import com.loiIn.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryService_impl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDao_impl();
    @Override
    public List<Category> findAll() throws SQLException {
        return categoryDao.findAll();
    }

    @Override
    public Category findById(int id) throws SQLException {
        return id > 0 ? categoryDao.findById(id) : null;
    }

    @Override
    public Category insert(String name) throws SQLException {
        Category newCategory = new Category();
        newCategory.setName(name);
        newCategory.setDeleted(false);
        return categoryDao.insert(newCategory);
    }

    @Override
    public boolean update(Category category) throws SQLException {
        return category.getId() > 0 ? categoryDao.update(category) : false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return id > 0 ? categoryDao.delete(id) : false;
    }
}
