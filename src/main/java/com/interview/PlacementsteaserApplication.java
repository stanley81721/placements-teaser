package com.interview;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.interview.model.Campaign;
import com.interview.model.LineItem;
import com.interview.service.CampaignService;
import com.interview.service.LineItemService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

@SpringBootApplication
public class PlacementsteaserApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlacementsteaserApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(CampaignService campaignService, LineItemService lineItemService) {
		return args -> {
			// read json and write to db
			Resource resource = new ClassPathResource("placements_teaser_data.json");
			InputStream in = resource.getInputStream();
			
			JSONTokener tokener = new JSONTokener(in);
			JSONArray dataArray = new JSONArray(tokener);
			List<Campaign> campaignList = new ArrayList<>();
			List<LineItem> lineItemList = new ArrayList<>();
			for(int i = 0; i < dataArray.length(); i++) {
				JSONObject object = dataArray.getJSONObject(i);
				LineItem lineItem = new LineItem();
				Campaign campaign = new Campaign();
				campaign.setCampaignId(object.getInt("campaign_id"));
				campaign.setCampaignName(object.getString("campaign_name"));

				lineItem.setId(object.getInt("id"));
				lineItem.setName(object.getString("line_item_name"));
				lineItem.setBookedAmount(object.getBigDecimal("booked_amount"));
				lineItem.setActualAmount(object.getBigDecimal("actual_amount"));
				lineItem.setAdjustments(object.getBigDecimal("adjustments"));
				lineItem.setCampaign(campaign);

				campaignList.add(campaign);
				lineItemList.add(lineItem);
			}
 
			campaignService.saveAllCampaigns(campaignList);
			lineItemService.saveAllLineItems(lineItemList);
			
			System.out.println("Save data SUCCESS!");
		};
	}
}
