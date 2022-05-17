package com.interview.controller;

import java.util.Date;
import java.util.List;

import com.interview.common.Constants;
import com.interview.model.Campaign;
import com.interview.model.ChangeLog;
import com.interview.model.LineItem;
import com.interview.service.CampaignService;
import com.interview.service.ChangeLogService;
import com.interview.service.LineItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private ChangeLogService changeLogService;

    @GetMapping("/lineItemss")
    public String viewCampaigns(Model model) {
        model.addAttribute("activePage", "lineItems");
        return fingPageinated(1, "campaignName", "asc", model);
    }

    @PostMapping("/lineItems/saveLineItem")
    public String saveLineItem(@ModelAttribute("lineItem") LineItem lineItem, @RequestParam(value = "inputReviewed", required = false) String inputReviewed) {
        // save lineItem to database
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        
        LineItem resultLineItem = lineItemService.getLineItemById(lineItem.getId());
        String changeContent = "";
        if(!resultLineItem.getAdjustments().equals(lineItem.getAdjustments())) {
            resultLineItem.setAdjustments(lineItem.getAdjustments());
            changeContent = "Adjustment change from " + String.valueOf(resultLineItem.getAdjustments().doubleValue()) + " to " + String.valueOf(lineItem.getAdjustments().doubleValue());
        } else if (!resultLineItem.getComment().equals(lineItem.getComment())) {
            resultLineItem.setComment(lineItem.getComment());
            changeContent = changeContent + ", Comment change from " + String.valueOf(resultLineItem.getAdjustments().doubleValue()) + " to " + String.valueOf(lineItem.getAdjustments().doubleValue());
        }
        
        if(null != inputReviewed) {
            resultLineItem.setStatus(Constants.Reviewable.REVIEWDED);
            changeContent = changeContent + ", Status change from Unreviewed to Reviewed";
        } else {
            if(resultLineItem.getStatus() != 0) {
                resultLineItem.setStatus(Constants.Reviewable.UNREVIEWED);
                changeContent = changeContent + ", Status change from Reviewed to Unreviewed";
            }
        }

        if(!changeContent.equals("")) {
            ChangeLog changeLog = new ChangeLog();
            changeLog.setContent(changeContent);
            changeLog.setCreateId(username);
            changeLog.setCreateTimestamp(new java.sql.Timestamp(new Date().getTime()));
            changeLogService.save(changeLog);
        }

        lineItemService.save(resultLineItem);

        return "redirect:/lineItems/page/campaign/1?campaignId=" + String.valueOf(resultLineItem.getCampaign().getCampaignId()) + "&sortField=id&sortDirection=asc";
    }

    @GetMapping("/showLineItemForUpdate/{id}")
	public String showLineItemForUpdate(@PathVariable ( value = "id") int id, Model model) {
		
		// get lineItem from the service
		LineItem lineItem = lineItemService.getLineItemById(id);
		
		// set lineItem as a model attribute to pre-populate the form
		model.addAttribute("lineItem", lineItem);
        model.addAttribute("activePage", "lineItems");
        
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
        model.addAttribute("activePage", "lineItems");
        
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
        model.addAttribute("activePage", "lineItems");

        return "lineItems/indexCampaign";
    }
}
