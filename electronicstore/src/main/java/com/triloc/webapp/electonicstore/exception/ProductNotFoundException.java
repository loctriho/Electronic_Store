package com.triloc.webapp.electonicstore.exception;

public class ProductNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(Long productId) {
        super("Product not found with ID: " + productId);
    }
}
