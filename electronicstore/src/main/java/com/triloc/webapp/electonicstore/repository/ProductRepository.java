package com.triloc.webapp.electonicstore.repository;


import com.triloc.webapp.electonicstore.dto.ItemsOrder;
import com.triloc.webapp.electonicstore.dto.ProductDTO;
import com.triloc.webapp.electonicstore.model.Manufacturer;
import com.triloc.webapp.electonicstore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByManufacturer(Manufacturer manufacturer);

    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    Page<Product> findAllByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);


    @Query(
            value = "SELECT new com.triloc.webapp.electonicstore.dto.ProductDTO(p.product_id, p.product_name, p.description, p.price, p.image, c.category_name, m.manufacturer_name) FROM Product p JOIN p.category c JOIN p.manufacturer m",
            countQuery = "SELECT COUNT(p) FROM Product p JOIN p.category c JOIN p.manufacturer m"
    )
    Page<ProductDTO> getAllProducts(Pageable pageable);


    @Query(value = "SELECT new com.triloc.webapp.electonicstore.dto.ProductDTO(p.product_id, p.product_name, p.description, p.price, p.image, c.category_name, m.manufacturer_name) FROM Product p JOIN p.category c JOIN p.manufacturer m WHERE c.category_name = ?1",
            countQuery = "SELECT count(p) FROM Product p JOIN p.category c WHERE c.category_name = ?1")
    Page<ProductDTO> getProductsByCategoryName(String productType, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.category.category_name = :categoryName")
    Long countByCategoryName(@Param("categoryName") String categoryName);



}

