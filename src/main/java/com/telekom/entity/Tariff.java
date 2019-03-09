package com.telekom.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tariffs")

public class Tariff {

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 30, message = "Tariff name should be from 1 to 30 symbols")
    private String name;


    @NotBlank(message = "Price is required")
    private double price;
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    public Tariff() {
    }

    public Tariff(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
