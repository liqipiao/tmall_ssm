package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName OrderService
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/1/9 0009 16:38
 **/

public interface OrderService {

    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    void add(Order c);

    void delete(int id);
    void update(Order c);
    Order get(int id);
    List list();
    //结算方法
    float add(Order c,List<OrderItem> ois);
    //查看订单方法
    List list(int uid, String excludedStatus);
}
