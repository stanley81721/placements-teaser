package com.interview.repository;

import com.interview.model.ChangeLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeLogRepository extends JpaRepository<ChangeLog, Integer> {
    
}
