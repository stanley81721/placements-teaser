package com.interview.service;

import java.util.List;
import java.util.Optional;

import com.interview.model.Invoice;
import com.interview.repository.InvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<Invoice> findPageinated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.invoiceRepository.findAll(pageable);
    }

}
