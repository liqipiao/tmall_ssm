package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * @ClassName ProductController
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/1/8 0008 15:54
 **/
@Controller
@RequestMapping("")
public class ProductController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

    /**
     * 添加方法
     * 在listProduct.jsp提交数据的时候，除了提交产品名称，小标题，原价格，优惠价格，库存外还会提交cid
     * @param model
     * @param p 获取Product对象
     * @return 客户端跳转到admin_product_list,并带上参数cid
     */
    @RequestMapping("admin_product_add")
    public String add(Model model, Product p) {
        //获取当前时间
        p.setCreateDate(new Date());
        //在ProductController中获取Product对象，并插入到数据库
        productService.add(p);
        return "redirect:admin_product_list?cid="+p.getCid();
    }

    /**
     * 根据id删除
     * @param id 删除的id
     * @return 客户端跳转到admin_product_list，并带上参数cid
     */
    @RequestMapping("admin_product_delete")
    public String delete(int id) {
        //根据id获取Product对象
        Product p = productService.get(id);
        //借助productService删除这个对象对应的数据
        productService.delete(id);
        return "redirect:admin_product_list?cid="+p.getCid();
    }

    /**
     *编辑功能
     * @param model
     * @param id 编辑的产品
     * @return 服务端跳转到admin/editProduct.jsp
     */
    @RequestMapping("admin_product_edit")
    public String edit(Model model, int id) {
        //根据product对象的cid产品获取Category对象，并把其设置在product对象的category产品上
        Product p = productService.get(id);
        Category c = categoryService.get(p.getCid());
        p.setCategory(c);
        //把product对象放在request的 "p" 产品中
        model.addAttribute("p", p);
        return "admin/editProduct";
    }

    /**
     * 修改功能
     * @param p
     * @return 客户端跳转到admin_product_list，并带上参数cid
     */
    @RequestMapping("admin_product_update")
    public String update(Product p) {
        //借助productService更新这个对象到数据库
        productService.update(p);
        return "redirect:admin_product_list?cid="+p.getCid();
    }

    /**
     * 查询所有方法
     * @param cid 基于cid，获取当前分类下的产品集合
     * @param model
     * @param page 分页参数
     * @return 服务端跳转到admin/listProduct.jsp页面
     */
    @RequestMapping("admin_product_list")
    public String list(int cid, Model model, Page page) {
        //基于cid，获取当前分类下的产品集合
        Category c = categoryService.get(cid);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Product> ps = productService.list(cid);
        //通过PageInfo获取产品总数
        int total = (int) new PageInfo<>(ps).getTotal();
        //把总数设置给分页page对象
        page.setTotal(total);
        //拼接字符串"&cid="+c.getId()，设置给page对象的Param值。 因为产品分页都是基于当前分类下的分页，所以分页的时候需要传递这个cid
        page.setParam("&cid="+c.getId());
        //把产品集合设置到 request的 "ps" 产品上
        model.addAttribute("ps", ps);
        //把分类对象设置到 request的 "c" 产品上
        model.addAttribute("c", c);
        //把分页对象设置到 request的 "page" 对象上
        model.addAttribute("page", page);
        //服务端跳转到admin/listProduct.jsp页面
        return "admin/listProduct";
    }

}
