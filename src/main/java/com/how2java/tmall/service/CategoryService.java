package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.util.Page;

import java.util.List;

public interface CategoryService {
    //分页方法
    /*int total();
    List<Category> list(Page page);*/
    List<Category> list();
    //添加方法
    void add(Category category);
    //删除方法
    void delete(int id);
    //查询单个方法
    Category get(int id);
    //修改方法
    void update(Category category);
}
