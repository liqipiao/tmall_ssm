package com.how2java.tmall.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    //查询所有商品，并分页
    @RequestMapping("admin_category_list")
    public String list(Model model, Page page){
        /*List<Category> cs=categoryService.list(page);
        int total=categoryService.total();
        page.setTotal(total);*/
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Category> cs=categoryService.list();
        int total=(int) new PageInfo<>(cs).getTotal();
        page.setTotal(total);
        model.addAttribute("cs",cs);
        model.addAttribute("page",page);
        return "admin/listCategory";
    }

    /**
     *
     * @param c 接受页面提交的分类名称
     * @param session 用于在后续获取当前的应用的路径
     * @param uploadedImageFile 用于接收上传的图片
     * @return redirect:/admin_category_list
     * @throws Exception
     */
    @RequestMapping("admin_category_add")
    public String add(Category c, HttpSession session, UploadedImageFile uploadedImageFile) throws Exception{
        //通过categoryService保存c对象
        categoryService.add(c);
        //通过session获取ControllerContext，再通过etRealPath定位存放分类图片的路径。
        File imageFolder=new File(session.getServletContext().getRealPath("img/category"));
        //根据分类id创建文件名
        File file=new File(imageFolder,c.getId()+".jpg");
        //如果/img/category目录不存在，则创建该目录，否则后续保存浏览器传过来图片，会提示无法保存
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        System.out.println(uploadedImageFile);
        System.out.println(uploadedImageFile.getImage());
        System.out.println(file);
        //通过UploadedImageFile 把浏览器传递过来的图片保存在上述指定的位置
        uploadedImageFile.getImage().transferTo(file);
        //通过ImageUtil.change2jpg(file); 确保图片格式一定是jpg，而不仅仅是后缀名是jpg.
        BufferedImage img= ImageUtil.change2jpg(file);
        ImageIO.write(img,"jpg",file);
        //客户端跳转到admin_category_list
        return "redirect:/admin_category_list";
    }

    /**
     *
     * @param id 提供参数接受id注入
     * @param session 用于定位文件位置
     * @return redirect:/admin_category_list
     * @throws Exception
     */
    @RequestMapping("admin_category_delete")
    public String delete(int id,HttpSession session) throws Exception{
        //通过categoryService删除指定数据
        categoryService.delete(id);
        //通过session获取ControllerContext然后获取分类图片位置，接着删除分类图片
        File imageFolder=new File(session.getServletContext().getRealPath("img/category"));
        File file=new File(imageFolder,id+".jpg");
        file.delete();
        //客户端跳转到 admin_category_list
        return "redirect:/admin_category_list";
    }

    /**
     *
     * @param model 用于保存数据
     * @param id 用来接受注入
     * @return admin/editCategory
     * @throws Exception
     */
    @RequestMapping("admin_category_edit")
    public String edit(Model model,int id) throws Exception{
        //通过categoryService.get()获取Category对象
        Category c=categoryService.get(id);
        //把对象放在c上
        model.addAttribute("c",c);
        //返回editCategory.jsp
        return "admin/editCategory";
    }

    /**
     *
     * @param c 接受页面提交的分类名称
     * @param session 用于在后续获取当前应用的路径
     * @param uploadedImageFile 用于接收上传的图片
     * @return redirect:/admin_category_list
     * @throws Exception
     */
    @RequestMapping("admin_category_update")
    public String update(Category c,HttpSession session,UploadedImageFile uploadedImageFile) throws Exception{
        //通过categoryService.update更新c对象
        categoryService.update(c);
        //首先判断是否有上传图片，如果有上传，那么通过session获取ControllerContext,再通过getRealPath定位存放分类图片的路径。
        MultipartFile image = uploadedImageFile.getImage();
        if(null!=image &&!image.isEmpty()){
            //通过UploadedImageFile 把浏览器传递过来的图片保存在上述指定的位置
            File  imageFolder= new File(session.getServletContext().getRealPath("img/category"));
            //分类id创建文件名
            File file = new File(imageFolder,c.getId()+".jpg");
            image.transferTo(file);
            //通过ImageUtil.change2jpg(file); 确保图片格式一定是jpg，而不仅仅是后缀名是jpg.
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }
        return "redirect:/admin_category_list";
    }
}
