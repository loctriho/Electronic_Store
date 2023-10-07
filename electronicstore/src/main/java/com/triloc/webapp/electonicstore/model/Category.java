package com.triloc.webapp.electonicstore.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;

    private String category_name;


    public Category() {}

    public Category(Long category_id, String category_name) {
        this.category_id = category_id;
        this.category_name = category_name;
    }
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    // getters and setters


    public Long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
