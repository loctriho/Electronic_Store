package com.triloc.webapp.electonicstore.repository;

import com.triloc.webapp.electonicstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c.category_name FROM Category c")
    List<String> findCategoryNames();
    @Query("SELECT new Category(c.category_id, c.category_name) FROM Category c")
    List<Category> findAllWithoutProducts();

}

