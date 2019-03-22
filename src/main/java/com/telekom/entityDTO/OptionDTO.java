package com.telekom.entityDTO;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class OptionDTO implements Serializable {

    private int id;

    private String name;
    private String description;

    private double priceMonthly;

    private double priceOneTime;

    private List<TariffDTO> compatibleTariffs = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPriceMonthly() {
        return priceMonthly;
    }

    public void setPriceMonthly(double priceMonthly) {
        this.priceMonthly = priceMonthly;
    }

    public double getPriceOneTime() {
        return priceOneTime;
    }

    public void setPriceOneTime(double priceOneTime) {
        this.priceOneTime = priceOneTime;
    }

    public List<TariffDTO> getCompatibleTariffs() {
        return compatibleTariffs;
    }

    public void setCompatibleTariffs(List<TariffDTO> compatibleTariffs) {
        this.compatibleTariffs = compatibleTariffs;
    }

}
