package com.service1.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String cityName;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "city")
    private List<Apartment> apartmentList = new ArrayList<>();

    public City(){

    }

    public int getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<Apartment> getApartmentList() {
        return apartmentList;
    }

    public void setApartmentList(List<Apartment> apartmentList) {
        this.apartmentList = apartmentList;
    }

    public void setId(int id) {
        this.id = id;
    }
}
