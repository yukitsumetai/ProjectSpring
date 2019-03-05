package com.telekom.entity;

import java.util.ArrayList;

public class Contract {
    private int number;
    private Tariff tariff;
    ArrayList<Option> options;
    private Client client;

    public Contract(int number, Tariff tariff, ArrayList<Option> options, Client client) {
        this.number = number;
        this.tariff = tariff;
        this.options = options;
        this.client = client;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
