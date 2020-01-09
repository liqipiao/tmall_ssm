package com.how2java.tmall.controller;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.util.UploadedImageFile;
import com.sun.imageio.plugins.common.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

/**
 * @ClassName ProductImageController
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/1/8 0008 17:07
 **/
@Controller
@RequestMapping("")
public class ProductImageController {
    @Autowired
    ProductService productService;
    @Autowired
    ProductImageService productImageService;

    /**
     * 增加功能
     * @param productImage productImage对象
     * @param session 用于保存图片
     * @param uploadedImageFile uploadedImageFile对象
     * @return 客户端条跳转到admin_productImage_list?pid=，并带上pid
     */
    @RequestMapping("admin_productImage_add")
    public String add(ProductImage productImage, HttpSession session, UploadedImageFile uploadedImageFile){
        //使用productImageService.add方法实现添加图片
        productImageService.add(productImage);
        //设置文件名
        String fileName=productImage.getId()+".jpg";
        String imageFolder;
        String imageFolder_small=null;
        String imageFolder_middle=null;
        //如果为单个图片
        if (ProductImageService.type_single.equals(productImage.getType())){
            //根据session().getServletContext().getRealPath( "img/productSingle")，定位到存放单个产品图片的目录
            imageFolder=session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small=session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle= session.getServletContext().getRealPath("img/productSingle_middle");
        }else {
            imageFolder=session.getServletContext().getRealPath("img/productDetail");
        }
        File file=new File(imageFolder,fileName);
        file.getParentFile().mkdirs();
        try {
            //通过uploadedImageFile保存文件
            uploadedImageFile.getImage().transferTo(file);
            //借助ImageUtil.change2jpg()方法把格式真正转化为jpg，而不仅仅是后缀名为.jpg
            BufferedImage image= com.how2java.tmall.util.ImageUtil.change2jpg(file);
            //借助ImageUtil.resizeImage把正常大小的图片，改变大小之后，分别复制到productSingle_middle和productSingle_small目录下
            ImageIO.write(image,"jpg",file);
            if (ProductImageService.type_single.equals(productImage.getType())){
                File f_small=new File(imageFolder_small,fileName);
                File f_middle=new File(imageFolder_middle,fileName);
                com.how2java.tmall.util.ImageUtil.resizeImage(file,56,56,f_small);
                com.how2java.tmall.util.ImageUtil.resizeImage(file,217,190,f_middle);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:admin_productImage_list?pid="+productImage.getPid();
    }

    /**
     * 删除功能
     * @param id 删除的产品id
     * @param session 用于读取图片
     * @return 客户端跳转到admin_productImage_list地址
     */
    @RequestMapping("admin_productImage_delete")
    public String delete(int id,HttpSession session){
        //根据id获取productImage对象
        ProductImage productImage=productImageService.get(id);
        //获取文件名
        String fileName=productImage.getId()+".jpg";
        String imageFolder;
        String imageFolder_small=null;
        String imageFolder_middle=null;
        // 如果是单个图片，那么删除3张正常，中等，小号图片
        if (ProductImageService.type_single.equals(productImage.getType())){
            imageFolder= session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small= session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle= session.getServletContext().getRealPath("img/productSingle_middle");
            File imageFile=new File(imageFolder,fileName);
            File f_small=new File(imageFolder_small,fileName);
            File f_middle=new File(imageFolder_middle,fileName);
            imageFile.delete();
            f_middle.delete();
            f_small.delete();
        }else {
            //如果是详情图片，那么删除一张图片
            imageFolder=session.getServletContext().getRealPath("img/productDetail");
            File imageFile=new File(imageFolder,fileName);
            imageFile.delete();
        }
        //借助productImageService，删除数据
        productImageService.delete(id);
        return "redirect:admin_productImage_list?pid="+productImage.getPid();
    }

    /**
     * 查询功能
     * @param pid 获取参数pid
     * @param model
     * @return 服务端跳转到admin/listProductImage.jsp页面
     */
    @RequestMapping("admin_productImage_list")
    public String list(int pid, Model model) {
        //根据pid获取Product对象
        Product p =productService.get(pid);
        //根据pid对象获取单个图片的集合pisSingle
        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.type_single);
        //根据pid对象获取单个图片的集合pisDetail
        List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.type_detail);
        //将product对象放到model上
        model.addAttribute("p", p);
        //将pisSingle放到model上
        model.addAttribute("pisSingle", pisSingle);
        //将pisDetail放到model上
        model.addAttribute("pisDetail", pisDetail);
        return "admin/listProductImage";
    }
}
