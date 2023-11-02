package com.triloc.webapp.electonicstore.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Session")
public class Session {
    @Id
    private Long session_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User user;

    @OneToMany(mappedBy = "session")
    private List<ShoppingCart> shoppingCarts;

    // getters and setters


    public Long getSession_id() {
        return session_id;
    }

    public void setSession_id(Long session_id) {
        this.session_id = session_id;
    }

    public User getCustomer() {
        return user;
    }

    public void setCustomer(User user) {
        this.user = user;
    }

    public List<ShoppingCart> getShoppingCarts() {
        return shoppingCarts;
    }

    public void setShoppingCarts(List<ShoppingCart> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
    }
}
