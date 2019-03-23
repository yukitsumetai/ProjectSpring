package com.telekom.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tariffs")
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 30, message = "TariffDTO name should be from 1 to 30 symbols")
    private String name;


    @NotBlank(message = "Price is required")
    private double price;
    private String description;
    private boolean isValid;

    @ManyToMany
    @JoinTable(name = "tariffs_options",
            joinColumns = @JoinColumn(name = "tariff_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    private Set<Option> options=new HashSet<>();

    public Tariff() {
    }

    public boolean isIsValid() {
        return isValid;
    }

    public void setIsValid(boolean valid) {
        this.isValid = valid;
    }

    public void addOption(Option o) {
        this.options.add(o);
    }
    public void removeOption(Option o) {
        this.options.remove(o);
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
