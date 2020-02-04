package com.how2java.tmall.comparator;

import com.how2java.tmall.pojo.Product;

import java.util.Comparator;

/**
 * @ClassName ProductReviewComparator
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/2/4 0004 14:03
 **/

/**
 * ProductReviewComparator 人气比较器
 * 把 评价数量多的放前面
 */
public class ProductReviewComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getReviewCount()-p1.getReviewCount();
    }
}
