package com.triloc.webapp.electonicstore.model;

import com.triloc.webapp.electonicstore.interface2.CloneableUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "User")
public class User implements UserDetails, Serializable, CloneableUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customer_id;



    private String first_name;

    private String last_name;

    private String email;

    private String phoneNumber;
    private String password;

    @Column(nullable = false, unique = true)
    private String username;


    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private boolean accountNonExpired;



    @Column(nullable = false)
    private boolean accountNonLocked;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();



    private String provider;


    @Embedded
    private Address address;

    public User(String username, String password) {
    }

    public User() {

    }

    // getters and setters

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }



    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }



    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public User cloneUser() {
        User clonedUser = new User();

        clonedUser.setCustomer_id(this.customer_id);
        clonedUser.setFirst_name(this.first_name);
        clonedUser.setLast_name(this.last_name);
        clonedUser.setEmail(this.email);
        clonedUser.setPhoneNumber(this.phoneNumber);
        clonedUser.setPassword(this.password);
        clonedUser.setUsername(this.username);
        clonedUser.setEnabled(this.enabled);
        clonedUser.setAccountNonExpired(this.accountNonExpired);
        clonedUser.setAccountNonLocked(this.accountNonLocked);
        clonedUser.setRoles(new HashSet<>(this.roles));
        clonedUser.setOrders(new ArrayList<>(this.orders));
        clonedUser.setProvider(this.provider);

        if (this.address != null) {
            Address clonedAddress = new Address();
//            clonedAddress.setAddress(this.address.getAddress());
            clonedAddress.setCity(this.address.getCity());
            clonedAddress.setState(this.address.getState());
            clonedAddress.setZipCode(this.address.getZipCode());
            clonedAddress.setState(this.address.getState());
            clonedUser.setAddress(clonedAddress);
        }

        return clonedUser;
    }


    public void cloneUserFrom(User anotherUser) {
        this.setFirst_name(anotherUser.getFirst_name());
        this.setLast_name(anotherUser.getLast_name());
        this.setEmail(anotherUser.getEmail());
        this.setPhoneNumber(anotherUser.getPhoneNumber());
        this.setUsername(anotherUser.getUsername());
        this.setAddress(anotherUser.getAddress());
        // add more fields as needed
    }
}