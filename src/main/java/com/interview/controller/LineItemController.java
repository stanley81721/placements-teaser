package com.interview.controller;

import java.util.List;

import com.interview.model.Campaign;
import com.interview.model.LineItem;
import com.interview.service.CampaignService;
import com.interview.service.LineItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LineItemController {

    @Autowired
    private LineItemService lineItemService;

    @Autowired
    private CampaignService campaignService;

    @GetMapping("/lineItemss")
    public String viewCampaigns(Model model) {
        return fingPageinated(1, "campaignName", "asc", model);
    }

    @PostMapping("/lineItems/saveLineItem")
    public String saveLineItem(@ModelAttribute("lineItem") LineItem lineItem) {
        // save lineItem to database
        LineItem resultLineItem = lineItemService.getLineItemById(lineItem.getId());
        resultLineItem.setAdjustments(lineItem.getAdjustments());
        resultLineItem.setComment(lineItem.getComment());

        lineItemService.save(resultLineItem);

        return "redirect:/lineItems/page/1?campaignId=" + String.valueOf(resultLineItem.getCampaign().getCampaignId()) + "&sortField=id&sortDirection=asc";
    }

    @GetMapping("/showLineItemForUpdate/{id}")
	public String showLineItemForUpdate(@PathVariable ( value = "id") int id, Model model) {
		
		// get lineItem from the service
		LineItem lineItem = lineItemService.getLineItemById(id);
		
		// set lineItem as a model attribute to pre-populate the form
		model.addAttribute("lineItem", lineItem);
        System.out.println(model.getAttribute("page")); 

		return "/lineItems/detail";
	}

    @GetMapping("/lineItems/page/{pageNo}")
    public String fingPageinated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField, @RequestParam("sortDirection") String sortDirection, Model model) {
        int pageSize = 50;

        Page<LineItem> page = lineItemService.findPageinated(pageNo, pageSize, sortField, sortDirection);
        List<LineItem> lineItemList = page.getContent();
        
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        
        model.addAttribute("sortField", sortField);
		model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        model.addAttribute("lineItemList", lineItemList);
        
        return "lineItems/index";
    }
    
    @GetMapping("/lineItems/page/campaign/{pageNo}")
    public String fingPageinatedLineItemsByCampaignId(@PathVariable(value = "pageNo") int pageNo, @RequestParam("campaignId") int campaignId, @RequestParam("sortField") String sortField, @RequestParam("sortDirection") String sortDirection, Model model) {
        int pageSize = 50;
        
        Page<LineItem> page = lineItemService.findPageinatedByCampaignId(campaignId, pageNo, pageSize, sortField, sortDirection);
        Campaign campaign = campaignService.getCampaignById(campaignId);
        List<LineItem> lineItemList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        
        model.addAttribute("campaignId", campaignId);
        model.addAttribute("sortField", sortField);
		model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        model.addAttribute("campaign", campaign);
        model.addAttribute("subTotals", campaign.getSubTotals());
        model.addAttribute("lineItemList", lineItemList);

        return "lineItems/indexCampaign";
    }
}
