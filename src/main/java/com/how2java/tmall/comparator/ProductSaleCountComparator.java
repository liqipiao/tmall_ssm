package com.how2java.tmall.comparator;

import com.how2java.tmall.pojo.Product;

import java.util.Comparator;

/**
 * @ClassName ProductSaleCountComparator
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/2/4 0004 14:05
 **/

/**
 * ProductSaleCountComparator 销量比较器
 把 销量高的放前面
 */
public class ProductSaleCountComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return p2.getSaleCount()-p1.getSaleCount();
    }

}
