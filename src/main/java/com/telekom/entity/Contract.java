package com.telekom.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "contracts")
public class Contract implements Serializable {


    @Id
    private BigInteger phoneNumber;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clientId", referencedColumnName = "id")
    Client client;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "tariffId")
    Tariff tariff;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "phoneNumber")
    List<Option> options;

    private Double price;
    private Double priceTariff;


    public BigInteger getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(BigInteger phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceTariff() {
        return priceTariff;
    }

    public void setPriceTariff(Double priceTariff) {
        this.priceTariff = priceTariff;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
