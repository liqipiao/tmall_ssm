package com.how2java.tmall.comparator;

import com.how2java.tmall.pojo.Product;

import java.util.Comparator;

/**
 * @ClassName ProductAllComparator
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/2/4 0004 14:01
 **/

/**
 * ProductAllComparator 综合比较器
 * 把 销量x评价 高的放前面
 */
public class ProductAllComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p2.getReviewCount()*p2.getSaleCount()-p1.getReviewCount()*p1.getSaleCount();
    }
}
