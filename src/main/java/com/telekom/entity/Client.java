package com.telekom.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client implements Serializable {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 10, message = "Surname should be from 1 to 10 symbols")
    private String surname;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Email
    private String email;
    private String birthday;
    private String password;
    private BigInteger passport;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Contract> contract=new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;


    public Client() {

    }

    public List<Contract> getContract() {
        return contract;
    }

    public void setContract(List<Contract> contract) {
        this.contract = contract;
    }

    public void setContract(Contract contract) {
        this.contract.add(contract);
    }


    public BigInteger getPassport() {
        return passport;
    }

    public void setPassport(BigInteger passport) {
        this.passport = passport;
    }


    public int getId() {
        return id;
    }

    public void setId(int clientID) {
        this.id = clientID;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
