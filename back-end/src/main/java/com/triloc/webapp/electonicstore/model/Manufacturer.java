package com.triloc.webapp.electonicstore.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Manufacturer")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long manufacturer_id;

    private String manufacturer_name;

    @OneToMany(mappedBy = "manufacturer")
    private List<Product> products;

    // getters and setters


    public Long getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(Long manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    public String getManufacturer_name() {
        return manufacturer_name;
    }

    public void setManufacturer_name(String manufacturer_name) {
        this.manufacturer_name = manufacturer_name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


    @Override
    public String toString() {
        return "Manufacturer{" +
                "manufacturer_name='" + manufacturer_name + '\'' +
                '}';
    }
}
