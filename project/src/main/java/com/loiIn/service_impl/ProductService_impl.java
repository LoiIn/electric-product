package com.loiIn.service_impl;

import com.loiIn.dao.ProductDao;
import com.loiIn.dao_impl.ProductDao_impl;
import com.loiIn.model.Product;
import com.loiIn.service.CategoryService;
import com.loiIn.service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductService_impl implements ProductService {

    private ProductDao productDao = new ProductDao_impl();
    private CategoryService categoryService = new CategoryService_impl();

    @Override
    public Product insert(Product product) throws SQLException {
        if(categoryService.findById(product.getCategoryId()) != null){
            return productDao.insert(product);
        }
        return null;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        if(categoryService.findById(product.getCategoryId()) != null){
            return productDao.update(product);
        }
        return false;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        return id > 0 ? productDao.delete(id) : false;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        return productDao.findAll();
    }

    @Override
    public Product findById(int id) throws SQLException {
        return id > 0 ?  productDao.findById(id) : null;
    }

    @Override
    public List<Product> search(String name, String startDate, String endDate, Boolean soldOut, int guarantee, int category, int bought, int promotion) throws SQLException {
        return productDao.search(name, startDate, endDate, soldOut, guarantee, category, bought, promotion);
    }

    @Override
    public List<Product> sortBy(String field, boolean isAsc) throws SQLException {
        return productDao.sortBy(field, isAsc);
    }

    @Override
    public List<Product> findByCategory(int idCategory) throws SQLException {
        return productDao.findByCategory(idCategory);
    }
}
