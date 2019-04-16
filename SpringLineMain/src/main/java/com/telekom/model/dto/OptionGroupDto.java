package com.telekom.model.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class OptionGroupDto implements Serializable {

    private int id;
    private String name;
    private String description;
    private boolean isValid;
    private Set<OptionDto> options=new HashSet<>();

    public void addOption(OptionDto o){this.options.add(o);}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsValid() {
        return isValid;
    }

    public void setIsValid(boolean valid) {
        isValid = valid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(Set<OptionDto> options) {
        this.options = options;
    }
}
