package com.interview.controller;

import java.util.List;

import com.interview.model.ChangeLog;
import com.interview.service.ChangeLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChangeLogController {

    @Autowired
    private ChangeLogService changeLogService;

    @GetMapping("/changeLogs")
    public String viewChangeLogs(Model model) {
        return fingPageinated(1, "invoiceNumber", "asc", model);
    }

    @GetMapping("/changeLogs/page/{pageNo}")
    public String fingPageinated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField, @RequestParam("sortDirection") String sortDirection, Model model) {
        int pageSize = 50;

        Page<ChangeLog> page = changeLogService.findPageinated(pageNo, pageSize, sortField, sortDirection);
        List<ChangeLog> changeLogList = page.getContent();
        
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        
        model.addAttribute("sortField", sortField);
		model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        model.addAttribute("changeLogList", changeLogList);
        
        return "changeLogs/index";
    }
    
}
