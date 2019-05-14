package com.telekom.model.dto;

import com.telekom.model.entity.Contract;
import com.telekom.model.entity.Role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserDto implements Serializable {

    private Long id;


    private List<Contract> contract = new ArrayList<>();
    private String password;
    private Role role;


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Contract> getContract() {
        return contract;
    }

    public void addContract(Contract contract) {
        this.contract.add(contract);
    }

    public void setContract(List<Contract> contract) {
        this.contract = contract;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
