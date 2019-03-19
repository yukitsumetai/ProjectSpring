package com.telekom.entity;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigInteger;

@Entity
@Table(name = "clients")
public class Client {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 10, message = "Surname should be from 1 to 10 symbols")
    private String surname;

    @Id
    @Column(name="clientID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Email
    private String email;
    private String birthday;
    private String password;
    private BigInteger passport;


    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "addressId")
    private Address address;

   private Contract contract;

    public Client() {

    }

    public BigInteger getPassport() {
        return passport;
    }

    public void setPassport(BigInteger passport) {
        this.passport = passport;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
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
