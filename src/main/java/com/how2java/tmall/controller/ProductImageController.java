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

    @RequestMapping("admin_productImage_add")
    public String add(ProductImage productImage, HttpSession session, UploadedImageFile uploadedImageFile){
        productImageService.add(productImage);
        String fileName=productImage.getId()+".jpg";
        String imageFolder;
        String imageFolder_small=null;
        String imageFolder_middle=null;
        if (ProductImageService.type_single.equals(productImage.getType())){
            imageFolder=session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small=session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle= session.getServletContext().getRealPath("img/productSingle_middle");
        }else {
            imageFolder=session.getServletContext().getRealPath("img/productDetail");
        }
        File file=new File(imageFolder,fileName);
        file.getParentFile().mkdirs();
        try {
            uploadedImageFile.getImage().transferTo(file);
            BufferedImage image= com.how2java.tmall.util.ImageUtil.change2jpg(file);
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

    @RequestMapping("admin_productImage_delete")
    public String delete(int id,HttpSession session){
        ProductImage productImage=productImageService.get(id);
        String fileName=productImage.getId()+".jpg";
        String imageFolder;
        String imageFolder_small=null;
        String imageFolder_middle=null;
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
            imageFolder=session.getServletContext().getRealPath("img/productDetail");
            File imageFile=new File(imageFolder,fileName);
            imageFile.delete();
        }
        productImageService.delete(id);
        return "redirect:admin_productImage_list?pid="+productImage.getPid();
    }
    @RequestMapping("admin_productImage_list")
    public String list(int pid, Model model) {
        Product p =productService.get(pid);
        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.type_single);
        List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.type_detail);
        model.addAttribute("p", p);
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);
        return "admin/listProductImage";
    }
}
