package com.service1.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Apartment implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private Address address;
    private int bedCount;
    private int bathCount;
    private String imageUrl;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private City city;
    private String description;
    private int metrage;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @PostPersist
    public void initImageUrl(){
        this.imageUrl = "apartmentImage" + this.id;
    }
    public Apartment() {}

    public int getMetrage() {
        return metrage;
    }

    public void setMetrage(int metrage) {
        this.metrage = metrage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getBedCount() {
        return bedCount;
    }

    public void setBedCount(int bedCount) {
        this.bedCount = bedCount;
    }

    public int getBathCount() {
        return bathCount;
    }

    public void setBathCount(int bathCount) {
        this.bathCount = bathCount;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


}

