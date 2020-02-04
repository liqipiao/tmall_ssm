package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Review;

import java.util.List;

/**
 * @ClassName ReviewService
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/2/4 0004 13:21
 **/
public interface ReviewService {
    void add(Review c);
    void delete(int id);
    void update(Review c);
    Review get(int id);
    List list(int pid);
    int getCount(int pid);
}
