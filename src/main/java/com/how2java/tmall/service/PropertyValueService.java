package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.PropertyValue;

import java.util.List;

/**
 * @ClassName PropertyValueService
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/1/9 0009 15:17
 **/
public interface PropertyValueService {
    void init(Product product);
    void update(PropertyValue propertyValue);
    PropertyValue get(int ptid,int pid);
    List<PropertyValue> list(int pid);
}
