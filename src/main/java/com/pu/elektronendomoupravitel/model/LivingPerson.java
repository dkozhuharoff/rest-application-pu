package com.pu.elektronendomoupravitel.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="living_persons")
public class LivingPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private long id;
    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="person_id")
    private Apartment livingPerson;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Apartment getLivingPerson() {
        return livingPerson;
    }

    public void setLivingPerson(Apartment livingPerson) {
        this.livingPerson = livingPerson;
    }
}
