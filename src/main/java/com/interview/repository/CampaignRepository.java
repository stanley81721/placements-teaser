package com.interview.repository;

import java.util.List;

import com.interview.model.Campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    
    List<Campaign> findByCampaignIdIn(List<Integer> camPaignIds);

}
