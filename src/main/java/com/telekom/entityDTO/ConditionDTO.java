package com.telekom.entityDTO;

import com.telekom.entity.Contract;
import com.telekom.entity.Option;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
public class ConditionDTO {

    private Integer id;
    private Contract contract;
    private Option parent_id;
    private Option child_id;


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

    public Option getParent_id() {
        return parent_id;
    }

    public void setParent_id(Option parent_id) {
        this.parent_id = parent_id;
    }

    public Option getChild_id() {
        return child_id;
    }

    public void setChild_id(Option child_id) {
        this.child_id = child_id;
    }
}
