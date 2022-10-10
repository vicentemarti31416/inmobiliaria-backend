package com.vicente.inmobiliaria.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name = "viviendas")
public class Vivienda implements Serializable {

    @Serial
    private static final long serialVersionUID = 6262996493166995309L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer price;

    private Integer meters;

    private String city;

    private String street;

    private Integer bedrooms;

    private Integer restrooms;

    private String picture;

    @Lob
    @JsonIgnore
    private byte[] photo;

    public Integer getphotoHashCode() {
        return (this.photo != null && this.photo.length > 0)? Arrays.hashCode(this.photo) : null;
    }

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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
