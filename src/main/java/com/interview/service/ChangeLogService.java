package com.interview.service;

import java.util.List;

import com.interview.model.ChangeLog;

import org.springframework.data.domain.Page;

public interface ChangeLogService {

    List<ChangeLog> getAllChangeLogs();
    void save(ChangeLog changeLog);
    ChangeLog getChangeLogById(int id);
	void deleteChangeLogById(int id);
    Page<ChangeLog> findPageinated(int pageNo, int pageSize, String sortField, String sortDirection);
    
}
