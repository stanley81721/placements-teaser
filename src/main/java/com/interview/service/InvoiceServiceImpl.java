package com.interview.service;

import java.util.List;
import java.util.Optional;

import com.interview.model.Invoice;
import com.interview.repository.InvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public void save(Invoice invoice) {
        this.invoiceRepository.save(invoice);
    }

    @Override
    public Invoice getInvoiceById(int id) {
        Optional<Invoice> optional = invoiceRepository.findById(id);
		Invoice lineItem = null;
		if (optional.isPresent()) {
			lineItem = optional.get();
		} else {
			throw new RuntimeException("LineItem not found for id : " + id);
		}
		return lineItem;
    }

    @Override
    public void deleteInvoiceById(int id) {
        this.invoiceRepository.deleteById(id);
    }

}
