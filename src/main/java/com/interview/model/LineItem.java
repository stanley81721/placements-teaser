package com.interview.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "LINE_ITEM")
public class LineItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private BigDecimal bookedAmount;
    private BigDecimal actualAmount;
    private BigDecimal adjustments;
    @Getter(AccessLevel.NONE)
    @Transient
    private BigDecimal billableAmount;
    private String comment;
    @ManyToOne
    @JoinColumn(name="campaign_id", nullable=false)
    private Campaign campaign;
    private Integer status = 0;
    @Transient
    private String statusStr;
    
    public BigDecimal getBillableAmount() {
        return this.actualAmount.add(this.adjustments);
    }

    public String getStatusStr() {
        if(this.status == 1) {
            return "Reviewed";
        } else {
            return "Unreviewed";
        }
    }
}
