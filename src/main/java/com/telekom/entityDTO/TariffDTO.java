package com.telekom.entityDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class TariffDTO implements Serializable {

    private int id;
    private String name;
    private double price;
    private String description;
    private List<OptionDTO> options = new ArrayList<>();


    public List<OptionDTO> getOptions() {
        return options;
    }
    public void setOptions(List<OptionDTO> options) {
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
