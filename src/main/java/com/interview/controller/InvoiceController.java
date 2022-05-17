package com.interview.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.interview.helper.InvoiceExcelExporter;
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
    public String viewInvoices(Model model) {
        model.addAttribute("activePage", "invoices");
        return fingPageinated(1, "invoiceNumber", "asc", model);
    }

    @GetMapping("/invoices/addInvoice")
    public String addInvoice(Model model) {
        model.addAttribute("activePage", "invoices");
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
        model.addAttribute("activePage", "invoices");

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
        model.addAttribute("activePage", "invoices");
        
        return "invoices/index";
    }

    @GetMapping("/invoices/export/excel/{id}")
    public void exportToExcel(HttpServletResponse response, @PathVariable ( value = "id") int id) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=invoice_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
         
        Invoice invoice = invoiceService.getInvoiceById(id);
         
        InvoiceExcelExporter invoiceExcelExporter = new InvoiceExcelExporter(invoice);
         
        invoiceExcelExporter.export(response);    
    }

}
