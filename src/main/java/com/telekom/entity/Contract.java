package com.telekom.entity;

import java.util.ArrayList;

public class Contract {
    private int phoneNumber;
    private Tariff tariff;
    //private double price;
   // ArrayList<OptionDTO> options;


    private Client client;



    public int getNumber() {
        return phoneNumber;
    }

    public void setNumber(int number) {
        this.phoneNumber = number;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
