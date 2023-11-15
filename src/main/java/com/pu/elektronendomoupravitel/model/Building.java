package com.pu.elektronendomoupravitel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="buildings")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private long id;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private short floorsNumber;
    @Column(nullable = false)
    private short apartmentsNumber;
    @Column(nullable = false)
    private double area;

    @JsonIgnore
    @OneToMany(mappedBy = "building",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<Apartment> apartments = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public short getFloorsNumber() {
        return floorsNumber;
    }

    public void setFloorsNumber(short floorsNumber) {
        this.floorsNumber = floorsNumber;
    }

    public short getApartmentsNumber() {
        return apartmentsNumber;
    }

    public void setApartmentsNumber(short apartmentsNumber) {
        this.apartmentsNumber = apartmentsNumber;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(List<Apartment> apartments) {
        this.apartments = apartments;
    }
}
