package com.telekom;

import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
public class TariffPromoted implements Serializable {

    private String name;
    private double price;
    private String description;
    private List<String> options;


    public TariffPromoted(String name, double price, String description, List<String> options) {
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

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
