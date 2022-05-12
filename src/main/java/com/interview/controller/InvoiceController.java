package com.interview.controller;

import java.util.Arrays;
import java.util.List;

import com.interview.helper.InvoiceHelper;
import com.interview.model.Campaign;
import com.interview.model.Invoice;
import com.interview.service.CampaignService;
import com.interview.service.InvoiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InvoiceController {
    
    @Autowired
    private CampaignService campaignService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceHelper invoiceHelper;

    @GetMapping("/invoices")
    public String viewCampaigns(Model model) {
        return "/invoices/index";
    }

    @GetMapping("/invoices/addInvoice")
    public String addInvoice(Model model) {
        //model.addAttribute("invoice", new Invoice());
        model.addAttribute("campaignList", campaignService.getAllCampaigns());

        return "/invoices/addInvoice";
    }

    @PostMapping(value = "/saveInvoice")
	public String saveInvoice(@RequestParam(value = "campaigns" , required = false) Integer[] campaigns) {
        Invoice invoice = new Invoice();
        List<Integer> list = Arrays.asList(campaigns);
        List<Campaign> campaignList = campaignService.getCampaignsByCampaignIds(list);
        invoice.setInvoiceNumber(invoiceHelper.generateInvoiceNumber());
        invoice.setCampaigns(campaignList);
        invoiceService.save(invoice);
        
		return "redirect:/";
	}

}
