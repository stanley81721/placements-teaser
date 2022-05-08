package com.interview.repository;

import java.util.List;

import com.interview.model.LineItem;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Integer> {
    List<LineItem> findByCampaignId(Integer campaignId);

    @Query(value = "select li from LineItem li where li.campaignId = ?1",
           countQuery = "select count(li) from LineItem li where li.campaignId = ?1")
    Page<LineItem> findLineItemByCampaignId(int campaignId, Pageable pageable);
}
