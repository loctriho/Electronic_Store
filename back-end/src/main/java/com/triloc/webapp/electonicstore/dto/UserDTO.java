package com.triloc.webapp.electonicstore.dto;

import com.triloc.webapp.electonicstore.model.Address;

public class UserDTO {

    private String email;
    private String first_name;
    private String last_name;
    private Address address;
    private String csrfToken;

   private String username;


   private String phoneNumber;

    public UserDTO(String username, String first_name, String last_name, String email,String phoneNumber, Address address) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

   public UserDTO(){};
// Constructors, getters, and setters



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(String csrfToken) {
        this.csrfToken = csrfToken;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}


