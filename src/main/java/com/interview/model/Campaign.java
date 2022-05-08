package com.interview.model;

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
public class Campaign {
    @Id
    @JsonProperty(value="campaign_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer campaignId;
    @JsonProperty(value="campaign_name")
    private String campaignName;
}
