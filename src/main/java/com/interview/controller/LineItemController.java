package com.interview.controller;

import java.util.List;

import com.interview.model.LineItem;
import com.interview.service.LineItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LineItemController {

    @Autowired
    private LineItemService lineItemService;
    
    @GetMapping("/lineItems/page/{pageNo}")
    public String fingPageinated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("campaignId") int campaignId, @RequestParam("sortField") String sortField, @RequestParam("sortDirection") String sortDirection, Model model) {
        int pageSize = 50;

        Page<LineItem> page = lineItemService.findPageinated(campaignId, pageNo, pageSize, sortField, sortDirection);
        List<LineItem> lineItemList = page.getContent();
        
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("campaignId", campaignId);
        model.addAttribute("sortField", sortField);
		model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        model.addAttribute("lineItemList", lineItemList);
        return "lineItems/index";
    }
}
