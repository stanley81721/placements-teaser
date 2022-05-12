package com.interview.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "INVOICE")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invoiceId;
    private String invoiceNumber;
    @ManyToMany(fetch = FetchType.LAZY,
    cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(name = "invoice_campaign",
      joinColumns = { @JoinColumn(name = "invoice_id") },
      inverseJoinColumns = { @JoinColumn(name = "campaign_id") })
    private List<Campaign> campaigns;
    @Transient
    private BigDecimal grandTotals;

    public BigDecimal getGrandTotals() {
        BigDecimal grandTotals = new BigDecimal(0);
        for(Campaign campaign : this.campaigns) {
            grandTotals.add(campaign.getSubTotals());
        }
        return grandTotals;
    }
}