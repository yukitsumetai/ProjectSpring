package com.telekom.entityDTO;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TariffDTO implements Serializable {

    private int id;
    private String name;
    private double price;
    private String description;
    private Set<OptionDTO> options = new HashSet<>();
    private BigInteger phoneNumber;
   private boolean isValid;
    private Set<OptionGroupDTO> optionGroups = new HashSet<>();

    public void addOptionGroup(OptionGroupDTO optionGroup) {
        this.optionGroups.add(optionGroup);
    }

    public Set<OptionGroupDTO> getOptionGroups() {
        return optionGroups;
    }

    public void setOptionGroups(Set<OptionGroupDTO> optionGroups) {
        this.optionGroups = optionGroups;
    }

    public boolean isIsValid() {
        return isValid;
    }

    public void setIsValid(boolean valid) {
        isValid = valid;
    }

    public BigInteger getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(BigInteger phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<OptionDTO> getOptions() {
        return options;
    }

    public void setOptions(Set<OptionDTO> options) {
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
