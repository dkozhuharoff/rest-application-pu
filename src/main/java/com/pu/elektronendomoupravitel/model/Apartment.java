package com.pu.elektronendomoupravitel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private long id;
    @Column(nullable = false)
    private int number;
    @Column(nullable = false)
    private double area;
    @Column(nullable = false)
    private short floor;
    private boolean sold = false;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="apartment_building_id")
    private Building building;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="apartment_holder_id")
    private ApartmentHolder apartmentHolder;

    @JsonIgnore
    @OneToMany(mappedBy = "livingPerson",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<LivingPerson> livingPeople = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public short getFloor() {
        return floor;
    }

    public void setFloor(short floor) {
        this.floor = floor;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public ApartmentHolder getApartmentHolder() {
        return apartmentHolder;
    }

    public void setApartmentHolder(ApartmentHolder apartmentHolder) {
        this.apartmentHolder = apartmentHolder;
    }

    public List<LivingPerson> getLivingPeople() {
        return livingPeople;
    }

    public void setLivingPeople(List<LivingPerson> livingPeople) {
        this.livingPeople = livingPeople;
    }
}
