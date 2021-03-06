package com.telekom.model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity stores contract data
 */
@Entity
@Table(name = "contracts")
public class Contract implements Serializable {

    /**
     * Phone number corresponds to contract id
     */
    @Id
    private BigInteger phoneNumber;

    /**
     * Id of the contract's holder
     */
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "clientId", referencedColumnName = "id")
    private Client client;

    /**
     * Only one tariff per contratc
     */
    @ManyToOne
    @JoinColumn(name = "tariffId")
    private Tariff tariff;

    @ManyToMany
    @JoinColumn(name = "phoneNumber")
    private Set<Option> options = new HashSet<>();

    private boolean blocked;
    private boolean agentBlock;
    private Double price;

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

    public void addOption(Option option) {
        this.options.add(option);
    }

    public BigInteger getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(BigInteger phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
