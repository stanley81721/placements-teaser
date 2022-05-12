package com.interview.controller;

import java.util.Arrays;
import java.util.List;

import com.interview.helper.InvoiceHelper;
import com.interview.model.Campaign;
import com.interview.model.Invoice;
import com.interview.service.CampaignService;
import com.interview.service.InvoiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return fingPageinated(1, "invoiceNumber", "asc", model);
    }

    @GetMapping("/invoices/addInvoice")
    public String addInvoice(Model model) {
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
        
		return "redirect:/invoices";
	}

    @GetMapping("/invoices/showInvoiceDetail/{id}")
    public String showInvoiceDetail(@PathVariable ( value = "id") int id, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(id);
        model.addAttribute("invoice", invoice);

        return "/invoices/detail";
    }

    @GetMapping("/invoices/page/{pageNo}")
    public String fingPageinated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField, @RequestParam("sortDirection") String sortDirection, Model model) {
        int pageSize = 50;

        Page<Invoice> page = invoiceService.findPageinated(pageNo, pageSize, sortField, sortDirection);
        List<Invoice> invoiceList = page.getContent();
        
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        
        model.addAttribute("sortField", sortField);
		model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        model.addAttribute("invoiceList", invoiceList);
        
        return "invoices/index";
    }

}
