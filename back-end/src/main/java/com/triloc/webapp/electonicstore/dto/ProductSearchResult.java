package com.triloc.webapp.electonicstore.dto;

import com.triloc.webapp.electonicstore.model.Product;

public class ProductSearchResult {
    private Product product;
    private String highlight;

    // Constructors, getters, and setters
    public ProductSearchResult(Product product, String highlight) {
        this.product = product;
        this.highlight = highlight;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }
// Getters and setters...
}
