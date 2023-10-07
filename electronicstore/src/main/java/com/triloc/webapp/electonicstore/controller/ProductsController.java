package com.triloc.webapp.electonicstore.controller;

import com.triloc.webapp.electonicstore.dto.ItemsOrder;
import com.triloc.webapp.electonicstore.dto.ProductDTO;
import com.triloc.webapp.electonicstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public @ResponseBody List<ProductDTO> getProducts(@RequestParam(value = "category", required = false) String category,
                                                      @RequestParam(defaultValue = "0") int page
                                                     ) {
        Pageable pageable = PageRequest.of(page, 10);

        if (category != null) {
            return productRepository.getProductsByCategoryName(category, pageable).get().collect(Collectors.toList());
        } else {
            return productRepository.getAllProducts(pageable).get().collect(Collectors.toList());
        }
    }

    @GetMapping("/count")
    public @ResponseBody Map<String, Long> getTotalCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("totalCount", productRepository.count());
        return response;
    }


    @GetMapping("/{categoryType}/count")
    public @ResponseBody Map<String, Long> getTotalCountByCategory(@PathVariable String categoryType) {
        Map<String, Long> response = new HashMap<>();
        Long count = productRepository.countByCategoryName(categoryType);
        response.put("totalCount", count);
        return response;
    }
}
