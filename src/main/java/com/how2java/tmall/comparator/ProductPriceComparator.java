package com.how2java.tmall.comparator;

import com.how2java.tmall.pojo.Product;

import java.util.Comparator;

/**
 * @ClassName ProductPriceComparator
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/2/4 0004 14:06
 **/

/**
 * ProductPriceComparator 价格比较器
 把 价格低的放前面
 */
public class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return (int) (p1.getPromotePrice()-p2.getPromotePrice());
    }

}
