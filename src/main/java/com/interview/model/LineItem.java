package com.interview.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @JsonProperty(value = "campaign_id")
    private Integer campaignId;
    @JsonProperty(value="line_item_name")
    private String name;
    @JsonProperty(value="booked_amount")
    private BigDecimal bookedAmount;
    @JsonProperty(value="actual_amount")
    private BigDecimal actualAmount;
    private BigDecimal adjustments;
    private BigDecimal billableAmount;
    
    public BigDecimal getBillableAmount() {
        return this.actualAmount.add(this.adjustments);
    }

    public void setBillableAmount(BigDecimal billableAmount) {
        this.billableAmount = billableAmount;
    }
}
