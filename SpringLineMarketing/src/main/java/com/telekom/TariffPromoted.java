package com.telekom;

import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
public class TariffPromoted implements Serializable {

    private String name;
    private double price;
    private String description;
    private String options;


    public TariffPromoted(String name, double price, String description, String options) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.options = options;
    }

    public TariffPromoted() {
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

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}
