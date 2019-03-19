package com.telekom.entityDTO;


import com.telekom.entity.Client;
import com.telekom.entity.Option;
import com.telekom.entity.Tariff;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

@Entity
public class ContractDTO implements Serializable {

   private String phoneNumber;
    private  Double price=(double)0;
    private Double priceOneTime=(double)0;
    private ClientDTO client;
    private TariffDTO tariff;
    private List<OptionDTO> options;




    public Double getPriceOneTime() {
        return priceOneTime;
    }

    public void setPriceOneTime(Double priceOneTime) {
        this.priceOneTime += priceOneTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price += price;
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
        this.tariff=tariff;
    }

    public List<OptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDTO> options) {
        this.options = options;
    }
}
