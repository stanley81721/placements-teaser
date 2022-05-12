package com.interview.service;

import java.util.List;

import com.interview.model.Invoice;

import org.springframework.data.domain.Page;

public interface InvoiceService {

    List<Invoice> getAllInvoices();
    void save(Invoice invoice);
    Invoice getInvoiceById(int id);
	void deleteInvoiceById(int id);
    Page<Invoice> findPageinated(int pageNo, int pageSize, String sortField, String sortDirection);
    
}
