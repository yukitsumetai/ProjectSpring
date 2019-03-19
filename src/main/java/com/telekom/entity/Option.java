package com.telekom.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 30, message = "OptionDTO name should be from 1 to 30 symbols")
    private String name;
    private String description;
    @NotBlank(message = "Price is required")
    private double priceMonthly;
    @NotBlank(message = "Price is required")
    private double priceOneTime;

    @ManyToMany(mappedBy = "options", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Tariff> compatibleTariffs = new ArrayList<>();

    public void addTariff(Tariff tariff) {
        this.compatibleTariffs.add(tariff);
    }

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

    public List<Tariff> getCompatibleTariffs() {
        return compatibleTariffs;
    }

    public void setCompatibleTariffs(List<Tariff> compatibleTariffs) {
        this.compatibleTariffs = compatibleTariffs;
    }

    public void setCompatibleTariffs(Tariff compatibleTariff) {
        this.compatibleTariffs.add(compatibleTariff);
    }
}
