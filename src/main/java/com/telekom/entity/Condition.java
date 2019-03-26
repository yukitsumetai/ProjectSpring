package com.telekom.entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "conditions")
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "phoneNumber")
    private Contract contract;

    private Integer parent_id;
    private Integer child_id;

    public Condition() {
    }

    public Condition(Contract contract, Integer parent_id, Integer child_id) {
        this.contract = contract;
        this.parent_id = parent_id;
        this.child_id = child_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public Integer getChild_id() {
        return child_id;
    }

    public void setChild_id(Integer child_id) {
        this.child_id = child_id;
    }
}
