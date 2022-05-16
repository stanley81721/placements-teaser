package com.interview.service;

import java.util.List;
import java.util.Optional;

import com.interview.model.ChangeLog;
import com.interview.repository.ChangeLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ChangeLogServiceImpl implements ChangeLogService {

    @Autowired
    private ChangeLogRepository changeLogRepository;

    @Override
    public List<ChangeLog> getAllChangeLogs() {
        return changeLogRepository.findAll();
    }

    @Override
    public void save(ChangeLog invoice) {
        this.changeLogRepository.save(invoice);
    }

    @Override
    public ChangeLog getChangeLogById(int id) {
        Optional<ChangeLog> optional = changeLogRepository.findById(id);
		ChangeLog lineItem = null;
		if (optional.isPresent()) {
			lineItem = optional.get();
		} else {
			throw new RuntimeException("LineItem not found for id : " + id);
		}
		return lineItem;
    }

    @Override
    public void deleteChangeLogById(int id) {
        this.changeLogRepository.deleteById(id);
    }

    @Override
    public Page<ChangeLog> findPageinated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.changeLogRepository.findAll(pageable);
    }
    
}
