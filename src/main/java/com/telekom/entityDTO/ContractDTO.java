package com.telekom.entityDTO;

import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.*;

@Entity
public class ContractDTO implements Serializable {

    private String phoneNumber;
    private Double price = 0.00;
    private Double priceOneTime = 0.00;
    private ClientDTO client;
    private TariffDTO tariff;
    private Set<OptionDTO> options = new HashSet<>();
    private boolean agentBlock;
    private boolean blocked;


    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isAgentBlock() {
        return agentBlock;
    }

    public void setAgentBlock(boolean agentBlock) {
        this.agentBlock = agentBlock;
    }

    public void addOption(OptionDTO option) {
        this.options.add(option);
    }

    public Double getPriceOneTime() {
        return Math.round(priceOneTime*100.0)/100.0;
    }

    public void addPriceOneTime(Double price) {
        this.priceOneTime += Math.round(price*100.0)/100.0;
    }

    public void setPriceOneTime(Double priceOneTime) {
        this.priceOneTime += priceOneTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public BigInteger getPhoneNumberInt() {
        return new BigInteger(this.getPhoneNumber());
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getPrice() {
        return Math.round(price*100.0)/100.0;
    }

    public void addPrice(Double price) { this.price += Math.round(price*100.0)/100.0; }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public TariffDTO getTariff() {
        return tariff;
    }

    public void setTariff(TariffDTO tariff) {
        this.tariff = tariff;
    }

    public Set<OptionDTO> getOptions() {
        return options;
    }

    public void setOptions(Set<OptionDTO> options) {
        this.options = options;
    }
}
