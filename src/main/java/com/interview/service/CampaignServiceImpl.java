package com.interview.service;

import java.util.List;
import java.util.Optional;

import com.interview.model.Campaign;
import com.interview.repository.CampaignRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CampaignServiceImpl implements CampaignService {
    
    @Autowired
    private CampaignRepository campaignRepository;

    @Override
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    @Override
	public void saveCampaign(Campaign campaign) {
		this.campaignRepository.save(campaign);
	}

	@Override
	public Campaign getCampaignById(int id) {
		Optional<Campaign> optional = campaignRepository.findById(id);
		Campaign campaign = null;
		if (optional.isPresent()) {
			campaign = optional.get();
		} else {
			throw new RuntimeException("Campaign not found for id : " + id);
		}
		return campaign;
	}

	@Override
	public void deleteCampaignById(int id) {
		this.campaignRepository.deleteById(id);
	}

    @Override
    public void saveAllCampaigns(List<Campaign> campaigns) {
        campaignRepository.saveAll(campaigns);
    }

    @Override
    public Page<Campaign> findPageinated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.campaignRepository.findAll(pageable);
    }
}