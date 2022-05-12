package com.interview.service;

import java.util.List;

import com.interview.model.Invoice;

public interface InvoiceService {

    List<Invoice> getAllInvoices();
    void save(Invoice invoice);
    Invoice getInvoiceById(int id);
	void deleteInvoiceById(int id);
    
}
