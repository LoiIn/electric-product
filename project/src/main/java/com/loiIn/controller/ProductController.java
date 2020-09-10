package com.loiIn.controller;

import com.google.gson.Gson;
import com.loiIn.model.JsonResult;
import com.loiIn.model.Product;
import com.loiIn.service.ProductService;
import com.loiIn.service_impl.ProductService_impl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductController", value = "/api/v1/product/*")
public class ProductController extends HttpServlet {

    private ProductService productService = new ProductService_impl();

    private JsonResult jsonResult = new JsonResult();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rs = "";
        try{
            Product product = new Gson().fromJson(request.getReader(),Product.class);
            rs = product != null ? jsonResult.jsonSuccess(productService.insert(product)) : jsonResult.jsonFail("Upload Product is Fail");
        }catch (Exception e){
            e.printStackTrace();
            rs = jsonResult.jsonFail("Upload Product is Fail");
        }
        response.getWriter().write(rs);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String rs = "";
        switch (pathInfo){
            case "/find-all":
                try{
                    List<Product> productList = productService.findAll();
                    rs = jsonResult.jsonSuccess(productList);
                }catch (Exception e){
                    e.printStackTrace();
                    rs = jsonResult.jsonFail("Find All Error");
                }
                response.getWriter().write(rs);
                break;
            case "/find-by-id":
                int id = Integer.parseInt(request.getParameter("id"));
                try{
                    Product product = productService.findById(id);
                    rs = product != null ? jsonResult.jsonSuccess(product) : jsonResult.jsonFail("Not found");
                }catch (Exception e){
                    e.printStackTrace();
                    rs = jsonResult.jsonFail("Find By Id is Error");
                }
                response.getWriter().write(rs);
                break;
            case "/find-by-category":
                int categoryId = Integer.parseInt(request.getParameter("category_id"));
                try {
                    List<Product> productList = productService.findByCategory(categoryId);
                    rs = jsonResult.jsonSuccess(productList);
                }catch (Exception e){
                    e.printStackTrace();
                    rs = jsonResult.jsonFail("Find By Category is Error");
                }
                response.getWriter().write(rs);
                break;
            case "/sort-by":
                String field = request.getParameter("field");
                Boolean isAsc = Boolean.parseBoolean(request.getParameter("isAsc"));
                try{
                    List<Product> productList = productService.sortBy(field,isAsc);
                    rs = jsonResult.jsonSuccess(productList);
                }catch (Exception e){
                    e.printStackTrace();
                    rs = jsonResult.jsonFail("Sort is Error");
                }
                response.getWriter().write(rs);
                break;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rs = "";
        try{
            Product product = new Gson().fromJson(req.getReader(), Product.class);
            rs = jsonResult.jsonSuccess(productService.update(product));
        }catch (Exception e){
            e.printStackTrace();
            rs = jsonResult.jsonFail("Update product fail");
        }
        resp.getWriter().write(rs);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rs = "";
        try{
            int id = Integer.parseInt(req.getParameter("id"));
            rs = jsonResult.jsonSuccess(productService.delete(id));
        }catch (Exception e){
            e.printStackTrace();
            rs = jsonResult.jsonFail("Delete product is fail");
        }
        resp.getWriter().write(rs);
    }
}
