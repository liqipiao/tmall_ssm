package com.how2java.tmall.comparator;

import com.how2java.tmall.pojo.Product;

import java.util.Comparator;

/**
 * @ClassName ProductDateComparator
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/2/4 0004 14:04
 **/

/**
 *  ProductDateComparator 新品比较器
 *  把 创建日期晚的放前面
 */
public class ProductDateComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return p2.getCreateDate().compareTo(p1.getCreateDate());
    }
}
