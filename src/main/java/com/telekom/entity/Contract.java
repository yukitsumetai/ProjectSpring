package com.telekom.entity;

import java.util.ArrayList;

public class Contract {
    private int number;
    private Tarif tarif;
    ArrayList<Option> options;
    private Client client;

    public Contract(int number, Tarif tarif, ArrayList<Option> options, Client client) {
        this.number = number;
        this.tarif = tarif;
        this.options = options;
        this.client = client;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Tarif getTarif() {
        return tarif;
    }

    public void setTarif(Tarif tarif) {
        this.tarif = tarif;
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
