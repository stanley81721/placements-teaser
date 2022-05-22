package com.interview.controller;

import java.io.ByteArrayOutputStream;

import com.interview.helper.InvoiceExcelExporter;
import com.interview.model.Invoice;
import com.interview.service.InvoiceService;
import com.interview.service.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StorageController {
    
    @Autowired
    private StorageService storageService;

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/invoices/upload/excel/{id}")
    public String uploadFile(@PathVariable ( value = "id") int id, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(id);

        InvoiceExcelExporter invoiceExcelExporter = new InvoiceExcelExporter(invoice);
        ByteArrayOutputStream byteArrayOutputStream = invoiceExcelExporter.exportForAWSS3();

        storageService.uploadFile(byteArrayOutputStream);
        model.addAttribute("invoice", invoice);
        model.addAttribute("status", "success");

        return "invoices/detail";
    }

}
