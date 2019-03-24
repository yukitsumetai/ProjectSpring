package com.telekom.entityDTO;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class OptionDTO implements Serializable {

    private int id;

    private String name;
    private String description;
    private boolean isValid;
    private double priceMonthly;
    private double priceOneTime;
    private Set<TariffDTO> compatibleTariffs = new HashSet<>();
    private OptionDTO parent;
    private Set<OptionDTO> children = new HashSet<>();


    public void addOption(Integer id, String name) {
        OptionDTO tmp = new OptionDTO();
        tmp.setId(id);
        tmp.setName(name);
        this.children.add(tmp);
    }

    public void addTariff(Integer id, String name) {
        TariffDTO tmp = new TariffDTO();
        tmp.setId(id);
        tmp.setName(name);
        this.compatibleTariffs.add(tmp);
    }

    public OptionDTO getParent() {
        return parent;
    }

    public void setParent(OptionDTO parent) {
        this.parent = parent;
    }

    public Set<OptionDTO> getChildren() {
        return children;
    }

    public void setChildren(Set<OptionDTO> children) {
        this.children = children;
    }

    public boolean isIsValid() {
        return isValid;
    }

    public void setIsValid(boolean valid) {
        isValid = valid;
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

    public Set<TariffDTO> getCompatibleTariffs() {
        return compatibleTariffs;
    }

    public void setCompatibleTariffs(Set<TariffDTO> compatibleTariffs) {
        this.compatibleTariffs = compatibleTariffs;
    }

}
