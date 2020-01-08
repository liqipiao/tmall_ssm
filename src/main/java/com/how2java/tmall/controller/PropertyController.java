package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName PropertyController
 * @Description TODO
 * @Author liqipiao
 * @Date 2019/12/29 0029 20:47
 **/
@Controller
@RequestMapping("")
public class PropertyController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    PropertyService propertyService;

    /**
     * 添加方法
     * @param p 通过参数Property接受注入
     * @return 客户端跳转到admin_property_list,并带上参数cid
     */
    @RequestMapping("admin_property_add")
    public String add(Model model, Property p) {
        //通过propertyService保存到数据库
        propertyService.add(p);
        return "redirect:admin_property_list?cid="+p.getCid();
    }

    /**删除方法
     *
     * @param id 删除的id
     * @return 客户端跳转到admin_property_list，并带上参数cid
     */
    @RequestMapping("admin_property_delete")
    public String delete(int id) {
        //根据id获取Property对象
        Property p = propertyService.get(id);
        //借助propertyService删除这个对象对应的数据
        propertyService.delete(id);
        return "redirect:admin_property_list?cid="+p.getCid();
    }

    /**根据id查找
     *
     * @param model
     * @param id
     * @return 服务端跳转到admin/editProperty.jsp
     */
    @RequestMapping("admin_property_edit")
    public String edit(Model model, int id) {
        //根据id获取Property对象
        Property p = propertyService.get(id);
        //根据properoty对象的cid属性获取Category对象，并把其设置在Property对象的category属性上
        Category c = categoryService.get(p.getCid());
        p.setCategory(c);
        //把Property对象放在reques的‘p'属性中
        model.addAttribute("p", p);
        return "admin/editProperty";
    }

    /**修改方法
     *
     * @param p 获取Property对象
     * @return 客户端跳转到admin_property_list，并带上参数cid
     */
    @RequestMapping("admin_property_update")
    public String update(Property p) {
        //借助propertyService更新这个对象到数据库
        propertyService.update(p);
        return "redirect:admin_property_list?cid="+p.getCid();
    }
    //查找所有方法
    @RequestMapping("admin_property_list")
    public String list(int cid, Model model,  Page page) {
        //获取分类cid和分页对象
        Category c = categoryService.get(cid);
        //通过PageHelper设置分页参数
        PageHelper.offsetPage(page.getStart(),page.getCount());
        //基于cid，获取当前分类下的属性集合
        List<Property> ps = propertyService.list(cid);
        //通过pageinfo获取属性总数
        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        //拼接字符串"&cid="+c.getId()，设置给page对象的Param值。 因为属性分页都是基于当前分类下的分页，所以分页的时候需要传递这个cid
        page.setParam("&cid="+c.getId());
        //把属性集合设置到 request的 "ps" 属性上
        model.addAttribute("ps", ps);
        //把分类对象设置到 request的 "c" 属性上
        model.addAttribute("c", c);
        //把分页对象设置到 request的 "page" 对象上
        model.addAttribute("page", page);
        return "admin/listProperty";
    }
}
