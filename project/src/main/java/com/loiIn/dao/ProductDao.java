package com.loiIn.dao;

import com.loiIn.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao extends BaseDao<Product>{

    List<Product> findAll() throws SQLException;

    Product findById(int id) throws  SQLException;

    List<Product> sortBy(String field, boolean isAsc) throws SQLException;

    List<Product> findByCategory(int idCategory) throws SQLException;

    List<Product> search(String name, String startDate, String endDate,
                         Boolean soldOut, int guarantee, int category,
                         int bought, int promotion) throws SQLException;
}
