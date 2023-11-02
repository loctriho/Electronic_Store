package com.triloc.webapp.electonicstore.service;

import com.triloc.webapp.electonicstore.dto.ProductDTO;
import com.triloc.webapp.electonicstore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<ProductDTO> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.getAllProducts(pageable);
    }

    public Page<ProductDTO> getProductsByCategoryName(String productType, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.getProductsByCategoryName(productType, pageable);
    }

    // Handle other methods similarly...
}

