package com.interview.service;

import java.util.List;

import com.interview.model.Campaign;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface CampaignService {
    List<Campaign> getAllCampaigns();
    void saveCampaign(Campaign employee);
    void saveAllCampaigns(List<Campaign> campaigns);
	Campaign getCampaignById(int id);
	void deleteCampaignById(int id);
    Page<Campaign> findPageinated(int pageNo, int pageSize, String sortField, String sortDirection);
}
