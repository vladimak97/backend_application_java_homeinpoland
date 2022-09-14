package com.service1.demo.entities;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String phoneNumber;
    private String email;
    private String officeAddress;

    public Address(){}

    public int getId() {
        return id;
    }

    public void String setId(int id)
        this.id = id;
    }
public void String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }
}
