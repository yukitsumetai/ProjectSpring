package com.telekom.model.dto;

import java.util.HashSet;
import java.util.Set;

public abstract class SharedDto {
    private Set<OptionDto> options = new HashSet<>();

    public Set<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(Set<OptionDto> options) {
        this.options = options;
    }

    public void addOption(OptionDto o){this.options.add(o);}
}
