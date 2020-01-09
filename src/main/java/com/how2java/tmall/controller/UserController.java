package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.UserService;
import com.how2java.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/1/9 0009 16:24
 **/
@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("admin_user_list")
    public String list(Model model, Page page){
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<User> users=userService.list();
        int total=(int) new PageInfo<>(users).getTotal();
        page.setTotal(total);
        model.addAttribute("us",users);
        model.addAttribute("page",page);
        return "admin/listUser";
    }
}
