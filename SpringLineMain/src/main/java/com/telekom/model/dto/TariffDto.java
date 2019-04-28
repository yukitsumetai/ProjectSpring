package com.telekom.model.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class TariffDto implements Serializable {

    private int id;

    @Size(max = 60)
    @NotBlank
    private String name;

    @Min(0)
    private double price;

    @Size(max = 200)
    private String description;
    private Set<OptionDto> options = new HashSet<>();
    private boolean isValid;
    private boolean existing;
    private boolean promoted;


    public boolean isExisting() {
        return existing;
    }

    public void setExisting(boolean existing) {
        this.existing = existing;
    }


    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }

    public boolean isIsValid() {
        return isValid;
    }

    public void setIsValid(boolean valid) {
        isValid = valid;
    }

    public Set<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(Set<OptionDto> options) {
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
