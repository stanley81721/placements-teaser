package com.interview.repository;


import com.interview.model.LineItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Integer> {

    @Query(value = "select li from LineItem li where li.campaign.campaignId = ?1",
           countQuery = "select count(li) from LineItem li where li.campaign.campaignId = ?1")
    Page<LineItem> findPageinatedLineItemsByCampaignId(int campaignId, Pageable pageable);

    @Query(value = "select li from LineItem li where li.campaign.campaignName like %?1%",
            countQuery = "select count(li) from LineItem li where li.campaign.campaignName like %?1%")
    Page<LineItem> findPageinatedLineItemsByOrderByIdAsc(String campaignName, Pageable pageable);
}
