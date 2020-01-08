package com.how2java.tmall.service;

import com.how2java.tmall.pojo.ProductImage;

import java.util.List;

/**
 * @ClassName ProductImageService
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/1/8 0008 16:57
 **/
public interface ProductImageService {
    //还提供了两个常量，分别表示单个图片和详情图片：
    String type_single="type_single";
    String type_detail="type_detail";

    void add(ProductImage productImage);
    void delete(int id);
    void update(ProductImage productImage);
    ProductImage get(int id);
    List list(int pid,String type);
}
