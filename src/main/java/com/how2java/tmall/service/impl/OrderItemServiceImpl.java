package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.OrderItemMapper;
import com.how2java.tmall.pojo.*;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.ProductService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName OrderItemServiceImpl
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/1/9 0009 16:38
 **/
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ProductService productService;
    @Override
    public void add(OrderItem orderItem) {
        orderItemMapper.insert(orderItem);
    }

    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OrderItem orderItem) {
        orderItemMapper.updateByPrimaryKeySelective(orderItem);
    }

    @Override
    public OrderItem get(int id) {
        OrderItem orderItem=orderItemMapper.selectByPrimaryKey(id);
        setProduct(orderItem);
        return orderItem;
    }

    private void setProduct(OrderItem orderItem) {
        Product product=productService.get(orderItem.getPid());
        orderItem.setProduct(product);
    }

    @Override
    public List<OrderItem> list() {
        OrderItemExample example=new OrderItemExample();
        example.setOrderByClause("id desc");
        return orderItemMapper.selectByExample(example);
    }

    @Override
    public void file(List<Order> orders) {
        for (Order order : orders){
            file(order);
        }
    }

    @Override
    public void file(Order order) {
        OrderItemExample example=new OrderItemExample();
        example.createCriteria().andOidEqualTo(order.getId());
        example.setOrderByClause("id desc");
        List<OrderItem> items=orderItemMapper.selectByExample(example);
        setProduct(items);
        float total=0;
        int totalNumber=0;
        for (OrderItem orderItem : items){
            total+=orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
            totalNumber+=orderItem.getNumber();
        }
        order.setTotal(total);
        order.setTotalNumber(totalNumber);
        order.setOrderItems(items);
    }

    @Override
    public int getSaleCount(int pid) {
        OrderItemExample example=new OrderItemExample();
        example.createCriteria().andPidEqualTo(pid);
        List<OrderItem> ois =orderItemMapper.selectByExample(example);
        int result=0;
        for (OrderItem oi : ois){
            result+=oi.getNumber();
        }
        return result;
    }

    private void setProduct(List<OrderItem> items) {
        for (OrderItem orderItem : items){
            setProduct(orderItem);
        }
    }
}
