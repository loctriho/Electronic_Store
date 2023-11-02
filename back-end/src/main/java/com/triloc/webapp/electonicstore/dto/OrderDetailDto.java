package com.triloc.webapp.electonicstore.dto;


import com.triloc.webapp.electonicstore.model.OrderDetail;

import java.util.Date;

public class OrderDetailDto {
    private Long id;
    private double price;
    private String name;
    private Long productId;
    private Integer quantity;
    private Date date;
    private String orderId; // order_id from Order

    public OrderDetailDto(OrderDetail orderDetail, String orderId) {
        this.id = orderDetail.getId();
        this.price = orderDetail.getPrice();
        this.name = orderDetail.getName();
        this.productId = orderDetail.getProductId();
        this.quantity = orderDetail.getQuantity();
        this.date = orderDetail.getDate();
        this.orderId = orderId;
    }
    public OrderDetailDto(){

    }
    // getters and setters
    // ...


    public Long getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Date getDate() {
        return date;
    }

    public String getOrderId() {
        return orderId;
    }
}
