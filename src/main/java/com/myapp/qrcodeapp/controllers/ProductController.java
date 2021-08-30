package com.myapp.qrcodeapp.controllers;

import com.myapp.qrcodeapp.entities.Product;
import com.myapp.qrcodeapp.helpers.ZXingHelper;
import com.myapp.qrcodeapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@Controller
public class ProductController {
    @Qualifier("productServiceImpl")
    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public String showProduct(Model m){
        List<Product> listProducts = service.listAll();
        m.addAttribute("listProducts", listProducts);

        return "index";
    }

    @RequestMapping(value = "qrcode/{id}", method = RequestMethod.GET)
    public void qrcode(@PathVariable("id") Integer id, HttpServletResponse response, Model model) throws Exception {
        Product product = service.get(id);

        model.addAttribute("product", product);
//        System.out.println(product.getCorrectionLevel() +" "+product.getImg());
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(ZXingHelper.getQRCodeImage("localhost:8080/products/details/"+id, 200, 200, product.getCorrectionLevel(), product.getImg()));

        outputStream.flush();
        outputStream.close();
    }


    @GetMapping("/products/add")
    public String addNewProduct(Model model){
        Product product = new Product();
        product.setCorrectionLevel("M");
        product.setImg("");
        model.addAttribute("product",product);
        return "add";
    }


    @PostMapping("/products/save")
    public String saveProduct(Product p, RedirectAttributes ra){
        service.save(p);
        ra.addFlashAttribute("message", "Thành công!");
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            Product product = service.get(id);
            model.addAttribute("product", product);
            return "edit";
        } catch (Exception e){
            e.printStackTrace();
//            ra.addFlashAttribute("message", "Không thành công!");
            return "redirect:/products";
        }

    }


    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "Xóa thành công!");
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/products";
    }

    @GetMapping("/products/details/{id}")
    public String productDetails(@PathVariable("id") Integer id, Model model){
        try {
            Product p = service.get(id);
            model.addAttribute("product", p);
            return "details";
        } catch (Exception e){
            e.printStackTrace();
            return "index";
        }
    }

}
