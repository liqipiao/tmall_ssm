package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;

import java.util.List;

/**
 * @ClassName OrderItemService
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/1/9 0009 16:37
 **/
public interface OrderItemService {
    void add(OrderItem orderItem);
    void delete(int id);
    void update(OrderItem orderItem);
    OrderItem get(int id);
    List list();

    void file(List<Order> orders);
    void file(Order order);

    void fill(List<Order> os);
    void fill(Order o);
    //根据产品获取销售量的方法
    int getSaleCount(int  pid);
    //立即购买方法
    List<OrderItem> listByUser(int uid);
}
