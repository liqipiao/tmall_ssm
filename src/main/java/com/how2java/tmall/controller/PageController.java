package com.how2java.tmall.controller;

import com.how2java.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName PageController
 * @Description 专门做服务端跳转
 * @Author liqipiao
 * @Date 2020/2/4 0004 12:20
 **/

@Controller
@RequestMapping("")
public class PageController {

    @RequestMapping("registerPage")
    public String registerPage(){
        return "fore/register";
    }

    @RequestMapping("registerSuccessPage")
    public String registerSuccessPage(){
        return "fore/registerSuccess";
    }

    @RequestMapping("loginPage")
    public String loginPage(){
        return "fore/login";
    }

    @RequestMapping("forealipay")
    public String alipay(){
        return "fore/alipay";
    }
}
