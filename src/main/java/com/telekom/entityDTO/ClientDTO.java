package com.telekom.entityDTO;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

public class ClientDTO implements Serializable {

    private int id;

    @Size(min = 2, max = 20)
    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 2, max = 20)
    private String surname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String birthday;

    @NotBlank
    private String password;

    @NotNull
    @Digits(integer=8, fraction=0)
    private Integer passport;

    @NotNull
    @Digits(integer=13, fraction=0)
    private String phoneNumber;
    private List<ContractDTO> contracts;
    private AddressDTO address;


    public ClientDTO() {

    }

    public List<ContractDTO> getContracts() {
        return contracts;
    }

    public void setContracts(List<ContractDTO> contracts) {
        this.contracts = contracts;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getPassport() {
        return passport;
    }

    public void setPassport(Integer passport) {
        this.passport = passport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
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
