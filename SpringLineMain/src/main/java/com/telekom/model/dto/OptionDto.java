package com.telekom.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class OptionDto implements Serializable {

    private int id;

    @Size(max = 60)
    @NotEmpty
    private String name;

    @Size(max = 180)
    private String description;

    @Min(0)
    private double priceMonthly;

    @Min(0)
    private double priceOneTime;

    private Set<TariffDto> compatibleTariffs = new HashSet<>();
    private OptionDto parent;
    private Set<OptionDto> children = new HashSet<>();
    private OptionGroupDto optionGroup;
    private boolean existing;
    private boolean isValid;

    public OptionDto() {
    }

    public OptionDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public OptionGroupDto getOptionGroup() {
        return optionGroup;
    }

    public void setOptionGroup(OptionGroupDto optionGroupDTO) {
        this.optionGroup = optionGroupDTO;
    }

    public boolean isExisting() {
        return existing;
    }

    public void setExisting(boolean existing) {
        this.existing = existing;
    }

    public void addOption(Integer id, String name) {
        OptionDto tmp = new OptionDto();
        tmp.setId(id);
        tmp.setName(name);
        this.children.add(tmp);
    }

    public void addTariff(Integer id, String name) {
        TariffDto tmp = new TariffDto();
        tmp.setId(id);
        tmp.setName(name);
        this.compatibleTariffs.add(tmp);
    }

    public OptionDto getParent() {
        return parent;
    }

    public void setParent(OptionDto parent) {
        this.parent = parent;
    }

    public Set<OptionDto> getChildren() {
        return children;
    }

    public void setChildren(Set<OptionDto> children) {
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

    public Set<TariffDto> getCompatibleTariffs() {
        return compatibleTariffs;
    }

    public void setCompatibleTariffs(Set<TariffDto> compatibleTariffs) {
        this.compatibleTariffs = compatibleTariffs;
    }

}
