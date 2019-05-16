package com.telekom.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "options")
public class Option implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private double priceMonthly;
    private double priceOneTime;
    /**
     * Delete flag
     */
    private boolean isValid;

    @ManyToMany(mappedBy = "options")
    private Set<Tariff> compatibleTariffs = new HashSet<>();

    /**
     * Condition for an option. Can be null
     */
    @ManyToOne
    @JoinTable(name = "options_options",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id"))
    private Option parent;

    /**
     * Options for which this option is a condition
     */
    @OneToMany(mappedBy = "parent")
    private Set<Option> children = new HashSet<>();

    @ManyToOne
    @JoinTable(name = "options_optionGroups",
            joinColumns = @JoinColumn(name = "option_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private OptionGroup group;


    public OptionGroup getGroup() {
        return group;
    }

    public void setGroup(OptionGroup group) {
        this.group = group;
    }

    public Option getParent() {
        return parent;
    }

    public void setParent(Option parent) {
        this.parent = parent;
    }

    public Set<Option> getChildren() {
        return children;
    }

    public void setChildren(Set<Option> children) {
        this.children = children;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
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

    public Set<Tariff> getCompatibleTariffs() {
        return compatibleTariffs;
    }

    public void setCompatibleTariffs(Set<Tariff> compatibleTariffs) {
        this.compatibleTariffs = compatibleTariffs;
    }

}
