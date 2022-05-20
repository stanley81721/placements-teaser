package com.interview.service;

import java.util.List;
import java.util.Optional;

import com.interview.model.LineItem;
import com.interview.repository.LineItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class LineItemServiceImpl implements LineItemService {
    
    @Autowired
    private LineItemRepository lineItemRepository;

    @Override
    public List<LineItem> getAllLineItems() {
        return lineItemRepository.findAll();
    }

    @Override
    public void save(LineItem lineItem) {
        this.lineItemRepository.save(lineItem);
    }

    @Override
    public void saveAllLineItems(List<LineItem> lineItems) {
        lineItemRepository.saveAll(lineItems);
    }

    @Override
    public LineItem getLineItemById(int id) {
        Optional<LineItem> optional = lineItemRepository.findById(id);
		LineItem lineItem = null;
		if (optional.isPresent()) {
			lineItem = optional.get();
		} else {
			throw new RuntimeException("LineItem not found for id : " + id);
		}
		return lineItem;
    }

    @Override
    public void deleteLineItemById(int id) {
        this.lineItemRepository.deleteById(id);
    }

    @Override
    public Page<LineItem> findPageinated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.lineItemRepository.findAll(pageable);
    }

    @Override
    public Page<LineItem> findPageinatedByCampaignId(int campaignId, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return lineItemRepository.findPageinatedLineItemsByCampaignId(campaignId, pageable);
    }

    @Override
    public Page<LineItem> findPageinatedByCampaignName(String campaignName, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return lineItemRepository.findPageinatedLineItemsByOrderByIdAsc(campaignName, pageable);
    }

}