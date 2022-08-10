package com.vicente.inmobiliaria.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "viviendas")
public class Vivienda implements Serializable {

    @Serial
    private static final long serialVersionUID = 6262996493166995309L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private Integer price;

    @NotEmpty
    private Integer meters;

    @NotEmpty
    private String city;

    @NotEmpty
    private String street;

    @NotEmpty
    private Integer bedrooms;

    @NotEmpty
    private Integer restrooms;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getMeters() {
        return meters;
    }

    public void setMeters(Integer meters) {
        this.meters = meters;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getRestrooms() {
        return restrooms;
    }

    public void setRestrooms(Integer restrooms) {
        this.restrooms = restrooms;
    }
}
