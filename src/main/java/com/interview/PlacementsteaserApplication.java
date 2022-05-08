package com.interview;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.model.Campaign;
import com.interview.model.LineItem;
import com.interview.service.CampaignService;
import com.interview.service.LineItemService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PlacementsteaserApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlacementsteaserApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(CampaignService campaignService, LineItemService lineItemService) {
		return args -> {
			// read json and write to db
			ObjectMapper camPaignMapper = new ObjectMapper();
			ObjectMapper lineItemMapper = new ObjectMapper();
			// if there is no records, read the data file
			if(campaignService.getAllCampaigns().size() == 0 || lineItemService.getAllLineItems().size() == 0) {
				TypeReference<List<LineItem>> lineItemtypeReference = new TypeReference<List<LineItem>>(){};
				InputStream inputStream2 = TypeReference.class.getResourceAsStream("/placements_teaser_data.json");
				try {
					List<LineItem> lineItems = lineItemMapper.readValue(inputStream2, lineItemtypeReference);
					lineItemService.saveAllLineItems(lineItems);
					System.out.println("LineItems Saved!");
				} catch (IOException e) {
					System.out.println("Unable to save lineItems: " + e.getMessage());
				}

				TypeReference<List<Campaign>> campaignTypeReference = new TypeReference<List<Campaign>>(){};
				InputStream inputStream1 = TypeReference.class.getResourceAsStream("/placements_teaser_data.json");
				try {
					List<Campaign> campaigns = camPaignMapper.readValue(inputStream1, campaignTypeReference);
					campaignService.saveAllCampaigns(campaigns);
					System.out.println("Campaigns Saved!");
				} catch (IOException e) {
					System.out.println("Unable to save campaigns: " + e.getMessage());
				}
			}
		};
	}
}
