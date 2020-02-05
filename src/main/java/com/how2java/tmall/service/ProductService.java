package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;

import java.util.List;

/**
 * @ClassName ProductService
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/1/8 0008 15:46
 **/
public interface ProductService {
    void add(Product product);
    void delete(int id);
    void update(Product product);
    Product get(int id);
    List list(int cid);

    void setFirstProductImage(Product product);

    void fill(List<Category> cs);

    void fill(Category c);

    void fillByRow(List<Category> cs);

    //增加为产品设置销量和评价数量的方法：
    void setSaleAndReviewNumber(Product p);
    void setSaleAndReviewNumber(List<Product> ps);
    //模糊查询方法
    List<Product> search(String keyword);
}
