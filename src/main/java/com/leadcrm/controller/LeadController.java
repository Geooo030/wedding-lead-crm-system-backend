package com.leadcrm.controller;

import com.leadcrm.dto.ApiResponse;
import com.leadcrm.entity.Lead;
import com.leadcrm.service.LeadService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leads")
public class LeadController {
    
    private final LeadService leadService;
    
    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }
    
    @GetMapping
    public ResponseEntity<?> getLeads(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priorityLevel,
            @RequestParam(required = false) String companyType,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("asc") 
                ? Sort.by(sortBy).ascending() 
                : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Lead.LeadStatus statusEnum = status != null ? Lead.LeadStatus.valueOf(status) : null;
        Lead.PriorityLevel priorityEnum = priorityLevel != null ? Lead.PriorityLevel.valueOf(priorityLevel) : null;
        
        Page<Lead> leads = leadService.searchLeads(country, statusEnum, priorityEnum, companyType, region, keyword, pageable);
        
        return ResponseEntity.ok(ApiResponse.success(leads));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getLeadById(@PathVariable String id) {
        Lead lead = leadService.findById(id);
        if (lead == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("客户不存在"));
        }
        return ResponseEntity.ok(ApiResponse.success(lead));
    }
    
    @PostMapping
    public ResponseEntity<?> createLead(@RequestBody Lead lead) {
        Lead saved = leadService.save(lead);
        return ResponseEntity.ok(ApiResponse.success("创建成功", saved));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLead(@PathVariable String id, @RequestBody Lead lead) {
        Lead existing = leadService.findById(id);
        if (existing == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("客户不存在"));
        }
        lead.setId(id);
        Lead saved = leadService.save(lead);
        return ResponseEntity.ok(ApiResponse.success("更新成功", saved));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLead(@PathVariable String id) {
        leadService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("删除成功", null));
    }
    
    @GetMapping("/stats/by-country")
    public ResponseEntity<?> countByCountry() {
        List<Object[]> stats = leadService.countByCountry();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
    
    @GetMapping("/stats/by-status")
    public ResponseEntity<?> countByStatus() {
        List<Object[]> stats = leadService.countByStatus();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
    
    @GetMapping("/stats/by-priority")
    public ResponseEntity<?> countByPriorityLevel() {
        List<Object[]> stats = leadService.countByPriorityLevel();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
    
    @PostMapping("/import")
    public ResponseEntity<?> importLeads(@RequestBody List<Lead> leads) {
        int imported = leadService.importLeads(leads);
        return ResponseEntity.ok(ApiResponse.success("导入成功 " + imported + " 条", imported));
    }
}