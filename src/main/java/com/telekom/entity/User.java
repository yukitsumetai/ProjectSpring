package com.telekom.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToMany
    @JoinTable(name = "users_contracts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "phoneNumber"))
    private List<Contract> contract = new ArrayList<>();
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(Contract contract, String password) {
        this.contract.add(contract);
        this.password = password;
        this.role = Role.ROLE_USER;
    }


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
