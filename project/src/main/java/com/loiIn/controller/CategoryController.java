package com.loiIn.controller;

import com.google.gson.Gson;
import com.loiIn.model.Category;
import com.loiIn.model.JsonResult;
import com.loiIn.service.CategoryService;
import com.loiIn.service_impl.CategoryService_impl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryController", value = "/api/v1/category/*")
public class CategoryController extends HttpServlet {

    private CategoryService categoryService = new CategoryService_impl();

    private JsonResult jsonResult = new JsonResult();

    // Thực hiện các chức năng thêm category
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rs = "";
        try {
            Category category = new Gson().fromJson(request.getReader(), Category.class);
            Category newCategory = categoryService.insert(category.getName());
            rs = newCategory != null ? jsonResult.jsonSuccess(newCategory) : jsonResult.jsonFail("Upload category fail");
        }catch (Exception e){
            e.printStackTrace();
            rs = jsonResult.jsonFail("Upload Category fail");
        }
        response.getWriter().write(rs);
    }

    // Thực hiện chức năng tìm kiếm Category
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path_info = request.getPathInfo();
        String rs = "";
        if(path_info.indexOf("/find-all") == 0){
            try {
                List<Category> categoryList = categoryService.findAll();
                rs = jsonResult.jsonSuccess(categoryList);
            }catch (Exception e){
                e.printStackTrace();
                rs = jsonResult.jsonFail("Find All Error");
            }
            response.getWriter().write(rs);
        }else if(path_info.indexOf("/find-by-id") == 0){
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                Category category = categoryService.findById(id);
                rs = category != null ? jsonResult.jsonSuccess(category) : jsonResult.jsonFail("Not Found");
            }catch (Exception e){
                e.printStackTrace();
                rs = jsonResult.jsonFail("Find By Id Error");
            }
            response.getWriter().write(rs);
        }else{
            response.sendError(404, "Url is not supported");
        }
    }

    @Override
    // Thực hiện chức năng xóa
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rs = "";
        String pathInfo = req.getPathInfo();
        if(pathInfo.indexOf("/delete") == 0){
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                rs = jsonResult.jsonSuccess(categoryService.delete(id));
            }catch(Exception e){
                e.printStackTrace();
                rs = jsonResult.jsonFail("Delete Category Fail");
            }
            resp.getWriter().write(rs);
        }else{
            resp.sendError(404, "Url is not supported");
        }

    }

    @Override
    // Thực hiện chức năng sửa
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException{
        String rs = "";
        String pathInfo = req.getPathInfo();
        if(pathInfo.indexOf("/update") == 0){
            try {
                Category category = new Gson().fromJson(req.getReader(), Category.class);
                rs = jsonResult.jsonSuccess(categoryService.update(category));
            }catch (Exception e){
                e.printStackTrace();
                rs = jsonResult.jsonFail("Update Category Fail");
            }
            resp.getWriter().write(rs);
        }else{
            resp.sendError(404, "Url is not supported");
        }
    }
}
