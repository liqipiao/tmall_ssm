package com.how2java.tmall.controller;

import com.how2java.tmall.comparator.*;
import com.how2java.tmall.pojo.*;
import com.how2java.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName ForeController
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/2/4 0004 12:34
 **/
@Controller
@RequestMapping("")
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;

    @RequestMapping("forehome")
    public String home(Model model){
        List<Category> cs=categoryService.list();
        productService.fill(cs);
        productService.fillByRow(cs);
        model.addAttribute("cs", cs);
        return "fore/home";
    }

    /**
     * 前台注册
     * 1.通过参数User获取浏览器提交的账号密码
     * 2.通过HtmlUtils.htmlEscape(name);把账号里的特殊符号进行转义
     * 3.判断用户名是否存在
     *      3.1 如果已经存在，就服务端跳转到reigster.jsp，并且带上错误提示信息
     *       3.2 如果不存在，则加入到数据库中，并服务端跳转到registerSuccess.jsp页面
     * @param model 用于保存数据
     * @param user 用户信息
     * @return 服务端跳转到redirect:registerSuccessPage
     */
    @RequestMapping("foreregister")
    public String register(Model model,User user) {
        String name=user.getName();
        name= HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist=userService.isExist(name);
        if (exist){
            String m="用户名已经被使用,不能使用";
            model.addAttribute("msg",m);
            model.addAttribute("user",null);
            return "fore/register";
        }
        userService.add(user);
        return "redirect:registerSuccessPage";
    }

    /**
     * 前台登录
     * 1. 获取账号密码
     * 2.把账号通过HtmlUtils.htmlEscape进行转义
     * 3.根据账号和密码获取User对象
     *      3.1如果对象为空，则服务端跳转回login.jsp，也带上错误信息，并且使用 loginPage.jsp 中的办法显示错误信息
     *      3.2如果对象存在，则把对象保存在session中，并客户端跳转到首页"forehome"
     * @param name 用户名
     * @param password 密码
     * @param model 存放提示信息
     * @param session 把对象保存在session中
     * @return 客户端跳转到redirect:forehome
     */
    @RequestMapping("forelogin")
    public String forelogin(@RequestParam("name") String name, @RequestParam("password") String password, Model model, HttpSession session){
        name=HtmlUtils.htmlEscape(name);
        User user=userService.get(name,password);
        if (null==user){
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        }
        session.setAttribute("user", user);
        return "redirect:forehome";
    }

    /**
     * 退出登录
     * 1.在session中去掉"user"
     * 2.客户端跳转到首页:
     * @param session 登录保存的用户session
     * @return forehome
     */
    @RequestMapping("forelogout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:forehome";
    }

    /**
     * 产品页
     * 1.获取pid
     * 2.根据pid获取Product 对象product
     * 3. 根据对象product，获取这个产品对应的单个图片集合
     * 4. 根据对象product，获取这个产品对应的详情图片集合
     * 5. 获取产品的所有属性值
     * 6. 获取产品对应的所有的评价
     * 7. 设置产品的销量和评价数量
     * 8. 把上述取值放在request属性上
     * 9. 服务端跳转到 "product.jsp" 页面
     * @param pid 产品pid
     * @param model 用于保存数据
     * @return 服务端跳转到 "product.jsp" 页面
     */
    @RequestMapping("foreproduct")
    public String product( int pid, Model model) {
        Product product=productService.get(pid);
        List<ProductImage> productSingleImages =productImageService.list(product.getId(),ProductImageService.type_single);
        List<ProductImage> productDetailImages =productImageService.list(product.getId(),ProductImageService.type_detail);
        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);
        List<PropertyValue> pvs=propertyValueService.list(product.getId());
        List<Review> reviews=reviewService.list(product.getId());
        productService.setSaleAndReviewNumber(product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("p", product);
        model.addAttribute("pvs", pvs);
        return "fore/product";
    }

    /**
     *判断登录
     * 1.获取session中的"user"对象
     *   1.1如果不为空，即表示已经登录，返回字符串"success"
         1.2如果为空，即表示未登录，返回字符串"fail"
     * @param session 保存用户的session对象
     * @return 返回字符串"fail
     */
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin( HttpSession session) {
        User user =(User)  session.getAttribute("user");
        if(null!=user){
            return "success";
        }
        return "fail";
    }

    /**
     * 用于模态框登录
     * 1. 获取账号密码
     * 2. 通过账号密码获取User对象
     * 2.1 如果User对象为空，那么就返回"fail"字符串。
     * 2.2 如果User对象不为空，那么就把User对象放在session中，并返回"success" 字符串
     * @param name 用户名
     * @param password 密码
     * @param session 保存用户信息的seeion
     * @return success
     */
    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam("name") String name, @RequestParam("password") String password,HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name,password);
        if(null==user){
            return "fail";
        }
        session.setAttribute("user", user);
        return "success";
    }

    /**
     * 分类页
     * 1. 获取参数cid
     * 2. 根据cid获取分类Category对象 c
     * 3. 为c填充产品
     * 4. 为产品填充销量和评价数据
     * 5. 获取参数sort
     * 5.1 如果sort==null，即不排序
     * 5.2 如果sort!=null，则根据sort的值，从5个Comparator比较器中选择一个对应的排序器进行排序
     * 6. 把c放在model中
     * 7. 服务端跳转到 category.jsp
     * @param cid
     * @param sort
     * @param model
     * @return
     */
    @RequestMapping("forecategory")
    public String category(int cid,String sort, Model model) {
        Category c = categoryService.get(cid);
        productService.fill(c);
        productService.setSaleAndReviewNumber(c.getProducts());
        if(null!=sort){
            switch(sort){
                case "review":
                    Collections.sort(c.getProducts(),new ProductReviewComparator());
                    break;
                case "date" :
                    Collections.sort(c.getProducts(),new ProductDateComparator());
                    break;

                case "saleCount" :
                    Collections.sort(c.getProducts(),new ProductSaleCountComparator());
                    break;

                case "price":
                    Collections.sort(c.getProducts(),new ProductPriceComparator());
                    break;

                case "all":
                    Collections.sort(c.getProducts(),new ProductAllComparator());
                    break;
            }
        }

        model.addAttribute("c", c);
        return "fore/category";
    }
}
