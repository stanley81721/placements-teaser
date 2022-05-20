package com.interview.service;

import java.util.List;

import com.interview.model.LineItem;

import org.springframework.data.domain.Page;

public interface LineItemService {

    List<LineItem> getAllLineItems();
    void save(LineItem lineItem);
    void saveAllLineItems(List<LineItem> lineItems);
    LineItem getLineItemById(int id);
	void deleteLineItemById(int id);
    Page<LineItem> findPageinated(int pageNo, int pageSize, String sortField, String sortDirection);
    Page<LineItem> findPageinatedByCampaignId(int campaign, int pageNo, int pageSize, String sortField, String sortDirection);

    Page<LineItem> findPageinatedByCampaignName(String campaignName, int pageNo, int pageSize, String sortField, String sortDirection);

}
