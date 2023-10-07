//package com.triloc.webapp.electonicstore.controller;
//
//
//import com.triloc.webapp.electonicstore.model.Category;
//import com.triloc.webapp.electonicstore.model.Product;
//import com.triloc.webapp.electonicstore.model.ProductItem;
//
//import com.triloc.webapp.electonicstore.repository.CategoryRepository;
//import com.triloc.webapp.electonicstore.repository.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpSession;
//import java.util.ArrayList;
//
//import java.util.List;
//
//
//@Controller
//@RequestMapping("/home")
//public class HomeController {
//
//
//    @Autowired
//    CategoryRepository categoryRepository;
//    @Autowired
//    ProductRepository productRepository;
//
//    @GetMapping
//    public String getProductByCategory(@RequestParam(value="category_name", required=false) String category_name,@RequestParam(value="category_id", required=false) Long categoryId, Model model
//    , HttpSession session) {
//        List<Category> categories = categoryRepository.findAll();
//        ArrayList<ProductItem> productItemArrayList = (ArrayList<ProductItem>) session.getAttribute("cartList");
//        if (productItemArrayList == null) {
//            productItemArrayList = new ArrayList<>();
//        }
//        model.addAttribute("cartList", productItemArrayList);
//
//        // Pass the categories to the view using the model
//        model.addAttribute("categories", categories);
//
//        if(categoryId!=null){
//            List<Product> products = productRepository.findAllByCategoryId(categoryId);
//            model.addAttribute("products", products);
//            model.addAttribute("category_name", category_name);
//            return "menu";
//        }
//
//        return "menu";
//    }
//
//    @GetMapping("/testAPI")
//    public  @ResponseBody String testAPI(Model model){
//        return "Test Oauth2 Succesfully";
//
//    }
//
//}
