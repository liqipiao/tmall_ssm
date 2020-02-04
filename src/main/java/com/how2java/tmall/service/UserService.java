package com.how2java.tmall.service;

import com.how2java.tmall.pojo.User;

import java.util.List;

/**
 * @ClassName UserService
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/1/9 0009 16:20
 **/
public interface UserService {
    void add(User user);
    void update(User user);
    void delete(int id);
    User get(int id);
    List list();
    boolean isExist(String name);
    //用于登录
    User get(String name,String password);
}
